package com.oierbravo.mechanicals.foundation.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Renders a block's texture as an axis-aligned box whose fill along one axis tracks a 0..1 fraction,
 * cropping the side faces instead of squashing the whole model.
 *
 * <p>Configure a reusable instance via {@link #builder()}:
 * <ul>
 *   <li>{@code fill(Direction)} — axis and direction the surface advances toward (default {@link Direction#UP}).
 *   <li>{@code crop(CropMode)} — how the side-face UV tracks the fill (default {@link CropMode#ANCHOR_TRAILING}).
 *   <li>{@code skipTrailingCap(boolean)} — omit the face at the fixed end (default {@code true}; the sifter's
 *       trailing cap points into the mesh and is never seen). Turn off for free-standing / horizontal fills.
 * </ul>
 *
 * <p>{@link #render} takes the <b>full</b> region as an {@link AABB} plus an explicit {@code fillFraction};
 * the renderer places the moving cap and crops the UV itself, so the crop is independent of the region's
 * absolute size. The leading cap is always drawn full-sprite; only the four side faces honour the crop mode.
 *
 * <p>The model's layers (one sprite / render type / tint index per render type) are resolved once per
 * {@link BlockState} and cached, so multi-layer composite models keep every layer; the
 * cache holds baked {@link TextureAtlasSprite} references, so it MUST be cleared on resource reload (see
 * {@code ModClientReloadListeners}) or it will draw stale atlas UVs after an atlas re-stitch.
 *
 * AI Generated code.
 */
public final class PartialBlockRenderer {

    /** How a side face's texture tracks the fill along the fill axis. */
    public enum CropMode {
        /** Texture pinned to the fixed (trailing) end, revealed toward the moving surface. */
        ANCHOR_TRAILING,
        /** Texture pinned to the moving (leading) surface, sliding with it. */
        ANCHOR_LEADING,
        /** No crop; the sprite tile is squashed to the box extent. */
        STRETCH
    }

    private static final RandomSource RANDOM = RandomSource.create(42L);
    private static final Map<BlockState, CachedFace> CACHE = new HashMap<>();

    private final Direction fillDir;
    private final CropMode cropMode;
    private final boolean skipTrailingCap;

    private PartialBlockRenderer(Builder b) {
        this.fillDir = b.fillDir;
        this.cropMode = b.cropMode;
        this.skipTrailingCap = b.skipTrailingCap;
    }

    public static Builder builder() {
        return new Builder();
    }

    /** Clears the per-block-state sprite cache. Call on client resource reload. */
    public static void clearCache() {
        CACHE.clear();
    }

    /**
     * Emit the partial box for {@code state} inside {@code region}, filled to {@code fillFraction} (0..1)
     * along the configured fill direction. Does nothing when the fraction rounds to empty.
     */
    public void render(MultiBufferSource buffers, PoseStack ms, BlockState state,
                       BlockAndTintGetter level, BlockPos pos, AABB region, float fillFraction, int light) {
        float f = Mth.clamp(fillFraction, 0f, 1f);
        if (f <= 1e-4f)
            return;

        CachedFace face = faceFor(state);

        Direction.Axis fa = fillDir.getAxis();
        boolean positive = fillDir.getAxisDirection() == Direction.AxisDirection.POSITIVE;
        double rMin = minOn(region, fa), rMax = maxOn(region, fa);
        double len = rMax - rMin;
        double trailing = positive ? rMin : rMax;
        double leading = positive ? rMin + f * len : rMax - f * len;

        // Shrunk box: the region with its fill-axis extent cut to the filled span.
        double bx0 = region.minX, by0 = region.minY, bz0 = region.minZ;
        double bx1 = region.maxX, by1 = region.maxY, bz1 = region.maxZ;
        double loFill = Math.min(trailing, leading), hiFill = Math.max(trailing, leading);
        switch (fa) {
            case X -> { bx0 = loFill; bx1 = hiFill; }
            case Y -> { by0 = loFill; by1 = hiFill; }
            case Z -> { bz0 = loFill; bz1 = hiFill; }
        }

        PoseStack.Pose pose = ms.last();

        // One box per model layer, back-to-front in resolve order (base then translucent overlay).
        for (Layer layer : face.layers) {
            VertexConsumer vc = buffers.getBuffer(layer.renderType);
            TextureAtlasSprite sprite = layer.sprite;

            int color = 0xFFFFFF;
            if (layer.tintIndex >= 0)
                color = Minecraft.getInstance().getBlockColors().getColor(state, level, pos, layer.tintIndex);
            int r = (color >> 16) & 0xFF, g = (color >> 8) & 0xFF, b = color & 0xFF;

            float u0 = sprite.getU0(), u1 = sprite.getU1(), v0 = sprite.getV0(), v1 = sprite.getV1();

            for (Direction dir : Direction.values()) {
                if (dir == fillDir.getOpposite() && skipTrailingCap)
                    continue;

                boolean cap = dir.getAxis() == fa;
                double[][] c = faceCorners(dir, bx0, by0, bz0, bx1, by1, bz1);
                float[][] uv = new float[4][2];

                for (int i = 0; i < 4; i++) {
                    double cx = c[i][0], cy = c[i][1], cz = c[i][2];
                    if (cap) {
                        // Full sprite: the two cross axes map straight to u and v.
                        Direction.Axis a0 = lowerCrossAxis(fa), a1 = upperCrossAxis(fa);
                        float s0 = norm(coord(cx, cy, cz, a0), minOn(region, a0), maxOn(region, a0));
                        float s1 = norm(coord(cx, cy, cz, a1), minOn(region, a1), maxOn(region, a1));
                        uv[i][0] = lerp(u0, u1, s0);
                        uv[i][1] = lerp(v0, v1, s1);
                    } else {
                        // Side face: cross axis fills u, fill axis drives the cropped v.
                        Direction.Axis cross = thirdAxis(fa, dir.getAxis());
                        float s = norm(coord(cx, cy, cz, cross), minOn(region, cross), maxOn(region, cross));
                        float t = (float) ((coord(cx, cy, cz, fa) - trailing) / (leading - trailing));
                        uv[i][0] = lerp(u0, u1, s);
                        uv[i][1] = sideV(t, f, v0, v1);
                    }
                }
                emitQuad(vc, pose, r, g, b, shadeFor(dir), light, dir, c, uv);
            }
        }
    }

    private float sideV(float t, float f, float v0, float v1) {
        // t: 0 at the trailing (fixed) end, 1 at the leading (moving) surface.
        return switch (cropMode) {
            case ANCHOR_TRAILING -> v1 - t * f * (v1 - v0);
            case ANCHOR_LEADING -> v0 + (1f - t) * f * (v1 - v0);
            case STRETCH -> v1 - t * (v1 - v0);
        };
    }

    private static void emitQuad(VertexConsumer vc, PoseStack.Pose pose, int r, int g, int b,
                                 float shade, int light, Direction dir, double[][] c, float[][] uv) {
        int sr = shade(r, shade), sg = shade(g, shade), sb = shade(b, shade);
        Vector3f n = pose.normal().transform(new Vector3f(dir.getStepX(), dir.getStepY(), dir.getStepZ()));
        for (int i = 0; i < 4; i++) {
            vc.addVertex(pose.pose(), (float) c[i][0], (float) c[i][1], (float) c[i][2])
                    .setColor(sr, sg, sb, 255)
                    .setUv(uv[i][0], uv[i][1])
                    .setOverlay(OverlayTexture.NO_OVERLAY)
                    .setLight(light)
                    .setNormal(pose, n.x, n.y, n.z);
        }
    }

    /** Face corners wound CCW as seen from outside the box (front-facing under backface culling). */
    private static double[][] faceCorners(Direction dir, double x0, double y0, double z0,
                                          double x1, double y1, double z1) {
        return switch (dir) {
            case UP -> new double[][] {{x0, y1, z0}, {x0, y1, z1}, {x1, y1, z1}, {x1, y1, z0}};
            case DOWN -> new double[][] {{x0, y0, z0}, {x1, y0, z0}, {x1, y0, z1}, {x0, y0, z1}};
            case SOUTH -> new double[][] {{x1, y1, z1}, {x0, y1, z1}, {x0, y0, z1}, {x1, y0, z1}};
            case NORTH -> new double[][] {{x0, y1, z0}, {x1, y1, z0}, {x1, y0, z0}, {x0, y0, z0}};
            case EAST -> new double[][] {{x1, y1, z0}, {x1, y1, z1}, {x1, y0, z1}, {x1, y0, z0}};
            case WEST -> new double[][] {{x0, y1, z1}, {x0, y1, z0}, {x0, y0, z0}, {x0, y0, z1}};
        };
    }

    private static float shadeFor(Direction dir) {
        return switch (dir) {
            case UP -> 1.0f;
            case DOWN -> 0.5f;
            case NORTH, SOUTH -> 0.8f;
            case EAST, WEST -> 0.6f;
        };
    }

    private static Direction.Axis lowerCrossAxis(Direction.Axis fill) {
        return fill == Direction.Axis.X ? Direction.Axis.Y : Direction.Axis.X;
    }

    private static Direction.Axis upperCrossAxis(Direction.Axis fill) {
        return fill == Direction.Axis.Z ? Direction.Axis.Y : Direction.Axis.Z;
    }

    private static Direction.Axis thirdAxis(Direction.Axis a, Direction.Axis b) {
        for (Direction.Axis axis : Direction.Axis.values())
            if (axis != a && axis != b)
                return axis;
        throw new IllegalStateException("no third axis for " + a + " and " + b);
    }

    private static double coord(double x, double y, double z, Direction.Axis axis) {
        return axis == Direction.Axis.X ? x : axis == Direction.Axis.Y ? y : z;
    }

    private static double minOn(AABB box, Direction.Axis axis) {
        return axis == Direction.Axis.X ? box.minX : axis == Direction.Axis.Y ? box.minY : box.minZ;
    }

    private static double maxOn(AABB box, Direction.Axis axis) {
        return axis == Direction.Axis.X ? box.maxX : axis == Direction.Axis.Y ? box.maxY : box.maxZ;
    }

    private static float norm(double v, double min, double max) {
        double d = max - min;
        return d < 1e-9 ? 0f : (float) ((v - min) / d);
    }

    private static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    private static int shade(int c, float s) {
        return Mth.clamp((int) (c * s), 0, 255);
    }

    private static CachedFace faceFor(BlockState state) {
        return CACHE.computeIfAbsent(state, PartialBlockRenderer::resolve);
    }

    private static CachedFace resolve(BlockState state) {
        BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(state);

        // One layer per render type so composite / multi-layer models (e.g. Ex Deorum compressed
        // blocks: opaque base + translucent overlay) keep every layer instead of collapsing to one.
        List<Layer> layers = new ArrayList<>();
        for (RenderType renderType : model.getRenderTypes(state, RANDOM, ModelData.EMPTY)) {
            List<BakedQuad> quads = model.getQuads(state, Direction.UP, RANDOM, ModelData.EMPTY, renderType);
            if (quads.isEmpty())
                quads = model.getQuads(state, null, RANDOM, ModelData.EMPTY, renderType);
            if (quads.isEmpty())
                continue;
            BakedQuad quad = quads.get(0);
            layers.add(new Layer(quad.getSprite(), renderType, quad.getTintIndex()));
        }

        // Fallback: no render types / empty quads — draw the particle icon as a single solid layer.
        if (layers.isEmpty())
            layers.add(new Layer(model.getParticleIcon(ModelData.EMPTY), RenderType.solid(), -1));

        return new CachedFace(layers);
    }

    private record Layer(TextureAtlasSprite sprite, RenderType renderType, int tintIndex) {}

    private record CachedFace(List<Layer> layers) {}

    public static final class Builder {
        private Direction fillDir = Direction.UP;
        private CropMode cropMode = CropMode.ANCHOR_TRAILING;
        private boolean skipTrailingCap = true;

        private Builder() {}

        public Builder fill(Direction fillDir) {
            this.fillDir = fillDir;
            return this;
        }

        public Builder crop(CropMode cropMode) {
            this.cropMode = cropMode;
            return this;
        }

        public Builder skipTrailingCap(boolean skipTrailingCap) {
            this.skipTrailingCap = skipTrailingCap;
            return this;
        }

        public PartialBlockRenderer build() {
            return new PartialBlockRenderer(this);
        }
    }
}
