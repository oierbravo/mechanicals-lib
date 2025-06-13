package com.oierbravo.mechanicals.foundation.gui.widget;

import com.mojang.blaze3d.systems.RenderSystem;
import com.oierbravo.mechanicals.foundation.gui.MechanicalGUITextures;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

public class ToggleIconButton extends AbstractSimiWidget {

    protected ScreenElement[] icons;
    protected MutableComponent[] labels;

    private int currentIndex;

    public ToggleIconButton(int x, int y, ScreenElement[] icons, MutableComponent[] labels) {
        this(x, y, 18, 18, icons,labels,0);
    }
    public ToggleIconButton(int x, int y, ScreenElement[] icons, MutableComponent[] labels,int currentIndex) {
        this(x, y, 18, 18, icons,labels,currentIndex);
    }

    public ToggleIconButton(int x, int y, int w, int h, ScreenElement[] icons, MutableComponent[] labels) {
        this(x, y, w, h,icons,labels,0);
    }
    public ToggleIconButton(int x, int y, int w, int h, ScreenElement[] icons, MutableComponent[] labels, int currentIndex) {
        super(x, y, w, h);
        this.icons = icons;
        this.labels = labels;
        this.currentIndex = currentIndex;
    }

    @Override
    public void doRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        if (visible) {
            isHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + width && mouseY < getY() + height;

            MechanicalGUITextures button = !active ? MechanicalGUITextures.BUTTON_DOWN
                    : isMouseOver(mouseX, mouseY) ? MechanicalGUITextures.BUTTON_HOVER : MechanicalGUITextures.BUTTON;

			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			drawBg(graphics, button);
			icons[currentIndex].render(graphics, getX() + 1, getY() + 1);
			setToolTip(getLabel(currentIndex));
		}
	}

    protected void drawBg(GuiGraphics graphics, MechanicalGUITextures button) {
        graphics.blit(button.location, getX(), getY(), button.startX, button.startY, button.width, button.height);
    }

	public void setToolTip(Component text) {
		toolTip.clear();
		toolTip.add(text);
	}

	public void setCurrentIndex(int pIndex){
		this.currentIndex = pIndex;
	}
	public void setIcons(ScreenElement[] icon) {
		this.icons = icons;
	}
	public void setLabels(MutableComponent[] labels) {
		this.labels = labels;
	}
	public MutableComponent getLabel(int pIndex){
		return this.labels[pIndex];
	}
}
