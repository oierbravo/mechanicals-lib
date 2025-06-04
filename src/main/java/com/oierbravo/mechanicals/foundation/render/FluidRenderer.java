package com.oierbravo.mechanicals.foundation.render;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.world.inventory.InventoryMenu;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.fluids.FluidStack;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class FluidRenderer {
    public static void renderFluidInTank(BlockAndTintGetter world, BlockPos pos, float x, float y,FluidStack fluidStack, PoseStack matrix, MultiBufferSource buffer, float width, float height, int pPackedLight, int pPackedOverlay) {
        matrix.pushPose();
        matrix.translate(0.5d, 0.565d, 0.5d);
        Matrix4f matrix4f = matrix.last().pose();
        Matrix3f matrix3f = matrix.last().normal();

        Fluid fluid = fluidStack.getFluid();
        IClientFluidTypeExtensions clientFluid = IClientFluidTypeExtensions.of(fluid);
        TextureAtlasSprite fluidTexture = Minecraft.getInstance()
                .getTextureAtlas(InventoryMenu.BLOCK_ATLAS)
                .apply(clientFluid.getStillTexture(fluidStack));

        int color = clientFluid.getTintColor(fluidStack);

        VertexConsumer builder = buffer.getBuffer(RenderType.translucent());
        renderTopFluidFace(fluidTexture, matrix4f, matrix3f, builder, color, width, height, x, y, pPackedLight, pPackedOverlay);
        matrix.popPose();

    }

    private static void renderTopFluidFace(TextureAtlasSprite sprite, Matrix4f matrix4f, Matrix3f normalMatrix, VertexConsumer builder, int color, float width, float height, float x, float y,int pPackedLight, int pPackedOverlay) {
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = ((color) & 0xFF) / 255f;
        float a = ((color >> 24) & 0xFF) / 255f;

        float minU = sprite.getU(4F / 16F);
        float maxU = sprite.getU(16F / 16F);
        float minV = sprite.getV(4F / 16F);
        float maxV = sprite.getV(16F / 16F);

        float pY = -height / 2 + y * height;


        builder.addVertex(matrix4f, -width / 2, pY , -width / 2).setColor(r, g, b, a)
                .setUv(minU, minV)
                .setLight(pPackedLight)
                .setOverlay(pPackedOverlay)
                .setNormal(0, 1, 0);

        builder.addVertex(matrix4f, -width / 2, pY, width / 2).setColor(r, g, b, a)
                .setUv(minU, maxV)
                .setLight(pPackedLight)
                .setOverlay(pPackedOverlay)
                .setNormal(0, 1, 0);

        builder.addVertex(matrix4f, width / 2, pY, width / 2).setColor(r, g, b, a)
                .setUv(maxU, maxV)
                .setLight(pPackedLight)
                .setOverlay(pPackedOverlay)
                .setNormal(0, 1, 0);

        builder.addVertex(matrix4f, width / 2, pY, -width / 2).setColor(r, g, b, a)
                .setUv(maxU, minV)
                .setLight(pPackedLight)
                .setOverlay(pPackedOverlay)
                .setNormal(0, 1, 0);
    }
}
