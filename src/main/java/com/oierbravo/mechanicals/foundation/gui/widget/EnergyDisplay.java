package com.oierbravo.mechanicals.foundation.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.oierbravo.mechanicals.foundation.gui.MechanicalGUITextures;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Optional;

public class EnergyDisplay extends AbstractSimiWidget {
    private final IEnergyStorage energy;
    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;

    public EnergyDisplay(int x, int y, IEnergyStorage pEnergy) {
        super(x, y);
        this.xPos = x;
        this.yPos = y;
        this.width = 9;
        this.height = 30;
        this.energy = pEnergy;
    }

    @Override
    protected void doRender(@NotNull GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float partialTicks) {
        if (visible) {
            isHovered = pMouseX >= getX() && pMouseY >= getY() && pMouseX < getX() + width && pMouseY < getY() + height;

            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            drawBg(pGuiGraphics, MechanicalGUITextures.RF_VERTIVAL_BAR);

            int stored = (int)(height * (energy.getEnergyStored() / (float)energy.getMaxEnergyStored()));
            pGuiGraphics.fillGradient(xPos +1,yPos + 1 + (height - stored),xPos +1 + width, yPos - 1  + height,0xffb51500, 0xff600b00);
            if(isHovered()) {
                Font font = Minecraft.getInstance().font;
                pGuiGraphics.renderTooltip(font, getTooltips(),
                        Optional.empty(), pMouseX, pMouseY);
            }
        }

    }
    protected void drawBg(GuiGraphics graphics, MechanicalGUITextures background) {
        graphics.blit(background.location, getX(), getY(), background.startX, background.startY, background.width, background.height);
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(energy.getEnergyStored()+" / "+energy.getMaxEnergyStored()+" FE"));
    }
}
