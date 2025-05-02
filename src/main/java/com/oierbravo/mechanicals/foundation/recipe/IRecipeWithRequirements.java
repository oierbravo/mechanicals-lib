package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;

public interface IRecipeWithRequirements {

    Map<RecipeRequirementType<?>, RecipeRequirement> getRecipeRequirements();
    default Map<RecipeRequirementType<?>, RecipeRequirement> getRecipeRequirementsToCheck(){
        return getRecipeRequirements();
    };
    List<RecipeRequirementType<?>> getEnabledRequirements();

    default <T extends RecipeRequirement> T getRequirement(RecipeRequirementType<T> type) {
        return (T) getRecipeRequirements().get(type);
    }
    boolean checkRequirements(Level pLevel, BlockEntity pBlockEntity);
}
