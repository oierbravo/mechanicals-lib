package com.oierbravo.mechanicals.foundation.gui.widget;
/*
 * Credits: Creators of create
 * https://github.com/Creators-of-Create/Create
 * License: MIT
 */

import com.mojang.blaze3d.systems.RenderSystem;
import com.oierbravo.mechanicals.foundation.gui.MechanicalGUITextures;
import net.createmod.catnip.gui.element.ScreenElement;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

public class IconButton extends AbstractSimiWidget {

	protected ScreenElement icon;

	public IconButton(int x, int y, ScreenElement icon) {
		this(x, y, 18, 18, icon);
	}

	public IconButton(int x, int y, int w, int h, ScreenElement icon) {
		super(x, y, w, h);
		this.icon = icon;
	}

	@Override
	public void doRender(GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
		if (visible) {
			isHovered = mouseX >= getX() && mouseY >= getY() && mouseX < getX() + width && mouseY < getY() + height;

			MechanicalGUITextures button = !active ? MechanicalGUITextures.BUTTON_DOWN
				: isMouseOver(mouseX, mouseY) ? MechanicalGUITextures.BUTTON_HOVER : MechanicalGUITextures.BUTTON;

			RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
			drawBg(graphics, button);
			icon.render(graphics, getX() + 1, getY() + 1);
		}
	}

	protected void drawBg(GuiGraphics graphics, MechanicalGUITextures button) {
		graphics.blit(button.location, getX(), getY(), button.startX, button.startY, button.width, button.height);
	}

	public void setToolTip(Component text) {
		toolTip.clear();
		toolTip.add(text);
	}

	public void setIcon(ScreenElement icon) {
		this.icon = icon;
	}
}
