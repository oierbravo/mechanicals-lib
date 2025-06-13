package com.oierbravo.mechanicals.foundation.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.oierbravo.mechanicals.Mechanicals;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.theme.Color;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

public class MechanicalIcons implements ScreenElement {
    public static final ResourceLocation ICON_ATLAS = Mechanicals.asResource("textures/gui/icons.png");
    public static final int ICON_ATLAS_SIZE = 256;
    private static int x = 0;
    private static int y = -1;
    private int iconX;
    private int iconY;
    public static final MechanicalIcons ARROW_LEFT = newRow();
    public static final MechanicalIcons TRASH = next();
    public static final MechanicalIcons REDSTONE_HIGH = next();
    public static final MechanicalIcons REDSTONE_LOW = next();
    public static final MechanicalIcons REDSTONE_IGNORE = next();
    public static final MechanicalIcons SEARCH = next();
    public static final MechanicalIcons CHECK = next();
    public static final MechanicalIcons LOCK_OPEN = next();
    public static final MechanicalIcons LOCK_CLOSE = next();
    public static final MechanicalIcons TRIANGLE_EXCLAMATION_ORANGE = next();
    public static final MechanicalIcons TRIANGLE_EXCLAMATION_MONO = next();

    public MechanicalIcons(int x, int y) {
        this.iconX = x * 16;
        this.iconY = y * 16;
    }

    private static MechanicalIcons next() {
        return new MechanicalIcons(++x, y);
    }

    private static MechanicalIcons newRow() {
        x = 0;
        return new MechanicalIcons(0, ++y);
    }

    @OnlyIn(Dist.CLIENT)
    public void bind() {
        RenderSystem.setShaderTexture(0, ICON_ATLAS);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(GuiGraphics graphics, int x, int y) {
        graphics.blit(ICON_ATLAS, x, y, 0, (float)this.iconX, (float)this.iconY, 16, 16, 256, 256);
    }

    @OnlyIn(Dist.CLIENT)
    public void render(PoseStack ms, MultiBufferSource buffer, int color) {
        VertexConsumer builder = buffer.getBuffer(RenderType.text(ICON_ATLAS));
        Matrix4f matrix = ms.last().pose();
        Color rgb = new Color(color);
        int light = 15728880;
        Vec3 vec1 = new Vec3(0.0, 0.0, 0.0);
        Vec3 vec2 = new Vec3(0.0, 1.0, 0.0);
        Vec3 vec3 = new Vec3(1.0, 1.0, 0.0);
        Vec3 vec4 = new Vec3(1.0, 0.0, 0.0);
        float u1 = (float)this.iconX * 1.0F / 256.0F;
        float u2 = (float)(this.iconX + 16) * 1.0F / 256.0F;
        float v1 = (float)this.iconY * 1.0F / 256.0F;
        float v2 = (float)(this.iconY + 16) * 1.0F / 256.0F;
        this.vertex(builder, matrix, vec1, rgb, u1, v1, light);
        this.vertex(builder, matrix, vec2, rgb, u1, v2, light);
        this.vertex(builder, matrix, vec3, rgb, u2, v2, light);
        this.vertex(builder, matrix, vec4, rgb, u2, v1, light);
    }

    @OnlyIn(Dist.CLIENT)
    private void vertex(VertexConsumer builder, Matrix4f matrix, Vec3 vec, Color rgb, float u, float v, int light) {
        builder.addVertex(matrix, (float) vec.x, (float) vec.y, (float) vec.z)
                .setColor(rgb.getRed(), rgb.getGreen(), rgb.getBlue(), 255)
                .setUv(u, v)
                .setLight(light);
    }
}