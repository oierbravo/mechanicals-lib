package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IRecipeWithRequirements {


    ArrayList<IRecipeRequirement> getRecipeRequirements();
    //List<String> getEnabledRequirements();


    default Optional<IRecipeRequirement> getRequirement(RecipeRequirementType<?> type) {
        return getRecipeRequirements().stream().filter(iRecipeRequirement -> iRecipeRequirement.getType() == type).findFirst();
    }

    boolean checkRequirements(Level pLevel, BlockEntity pBlockEntity);

}
