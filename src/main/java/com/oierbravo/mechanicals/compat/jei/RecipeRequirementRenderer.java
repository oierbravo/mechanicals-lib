package com.oierbravo.mechanicals.compat.jei;

import com.oierbravo.mechanicals.foundation.recipe.IRecipeWithRequirements;
import com.oierbravo.mechanicals.utility.LibLang;
import net.createmod.catnip.data.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;

import java.util.List;

public class RecipeRequirementRenderer {
    public static void drawRequirements(IRecipeWithRequirements recipe, GuiGraphics guiGraphics, int x, int y) {
        Minecraft minecraft = Minecraft.getInstance();
        Font fontRenderer = minecraft.font;
        int index = 0;
        int distance = 9;
        int offsetX = 5;
        int offsetY = 14;

        guiGraphics.drawString(fontRenderer, LibLang.translate("ui.recipe.requirements.title").component().withStyle(), x, y, 0xFFFFFFFF, true);

        List<Pair<Component, Component>> jeiRequirementTooltips = recipe.getJeiRequirementsTooltips();

        if(jeiRequirementTooltips.isEmpty()){
            guiGraphics.drawString(fontRenderer, LibLang.translate("ui.recipe_requirement.none.tooltip").component().withStyle(),x + offsetX, y + offsetY, 0xFF808080, false);
            return;
        }

        for (Pair<Component, Component> pair : jeiRequirementTooltips) {
            int oneLinerLenght = pair.getSecond().getString().length() + pair.getSecond().getString().length();
            if (oneLinerLenght < 19) {
                guiGraphics.drawString(fontRenderer, pair.getFirst().plainCopy().append(" ").append(pair.getSecond()), x + offsetX, y + offsetY + distance * index, 0xFF808080, false);
                index++;
                continue;
            }
            guiGraphics.drawString(fontRenderer, pair.getFirst(), x + offsetX, y + offsetY + distance * index, 0xFF808080, false);
            index++;
            guiGraphics.drawString(fontRenderer, pair.getSecond(), x + offsetX * 2, y + offsetY + distance * index, 0xFF808080, false);
            index++;

        }
    }
}
