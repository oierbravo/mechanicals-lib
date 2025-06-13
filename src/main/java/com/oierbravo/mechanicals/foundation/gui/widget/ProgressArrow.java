package com.oierbravo.mechanicals.foundation.gui.widget;

import com.oierbravo.mechanicals.foundation.gui.MechanicalGUITextures;
import net.createmod.catnip.gui.widget.AbstractSimiWidget;
import net.minecraft.client.gui.GuiGraphics;
import org.jetbrains.annotations.NotNull;

public class ProgressArrow extends AbstractSimiWidget {
    MechanicalGUITextures TEXTURE = MechanicalGUITextures.PROGRESS_ARROW_EMPTY;
    int currentProgress;
    private int progress;

    public ProgressArrow(int x, int y) {
        super(x, y);
    }

    @Override
    protected void doRender(@NotNull GuiGraphics graphics, int mouseX, int mouseY, float partialTicks) {
        graphics.blit(TEXTURE.location, getX(), getY(), TEXTURE.startX, TEXTURE.startY, currentProgress, TEXTURE.height);
    }

    public void setProgress(int progress, int maxProgress) {
        int progressArrowSize = TEXTURE.width;
        this.currentProgress = maxProgress != 0 && progress != 0 ? progress * progressArrowSize / maxProgress : 0;
    }
}
