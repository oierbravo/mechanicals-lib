package com.oierbravo.mechanicals.foundation.recipe;

import com.oierbravo.mechanicals.utility.LibLang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public abstract class RecipeRequirement {
    public abstract RecipeRequirementType<?> getType();

    public abstract boolean test(Level pLevel, BlockEntity pBlockEntity);

    public abstract boolean isPresent();
    public abstract String toString();
    public Component toRequirementComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".tooltip", toString()).component();
    };
    public Component toMissingComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".missing", toString()).component();
    }
}