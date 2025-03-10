package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.utility.LibLang;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public abstract class RecipeRequirement<V>{

    public abstract RecipeRequirementType<?> getType();

    public abstract boolean test(Level pLevel, BlockEntity pBlockEntity);
    public abstract Optional<V> getValue();
    public abstract boolean isPresent();
    public abstract String toString();

    public boolean isProcessBlocker(){
        return this instanceof IProcessBlockingRequirement;
    }

    public Component toTooltipComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".tooltip", toString()).component();
    };
    public Component toMissingComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".missing", toString()).component();
    }


    /*public static MapCodec<? extends RecipeRequirement<?>> typeCodec() {
    }*/

    public static MapCodec<? extends RecipeRequirementType<?>> typeCodec(RecipeRequirementType<?> recipeRequirementType) {

    }
}