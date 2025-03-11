package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeRequirementsUtils {
    public static ArrayList<RecipeRequirementType<?>> checkRequirements(Map<String, IRecipeRequirement> pRecipeRequirements, BlockEntity pBlockEntity){
        ArrayList<RecipeRequirementType<?>> missingRequirements = new ArrayList<>();
        pRecipeRequirements.forEach((recipeRequirementType, recipeRequirement) -> {
            if(!recipeRequirement.test(pBlockEntity.getLevel(),pBlockEntity))
                missingRequirements.add(recipeRequirement.getType());
        });
        return missingRequirements;
    }

}
