package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RecipeRequirementsUtils {
    /*public static ArrayList<RecipeRequirement<?>>  fromJson(JsonObject json, List<RecipeRequirementType<?>> enabledRecipeRequirements){
        ArrayList<RecipeRequirement> recipeRequirements = new ArrayList<>();

        enabledRecipeRequirements.forEach(recipeRequirementType -> {
            if (GsonHelper.isValidNode(json, recipeRequirementType.getId())) {
                recipeRequirements.add(recipeRequirementType.fromJson(json));
            }
        });
        return recipeRequirements;
    }*/
    public static ArrayList<RecipeRequirement<?>> fromBuffer(FriendlyByteBuf buffer,  List<RecipeRequirementType<?>> enabledRecipeRequirements){
        ArrayList<RecipeRequirement<?>> recipeRequirements = new ArrayList<>();
        enabledRecipeRequirements.forEach(recipeRequirementType -> {
            recipeRequirements.add(recipeRequirementType.fromNetwork(buffer));
        });

        return recipeRequirements;
    }

    /*public static JsonObject toJson(JsonObject pJson, Map<RecipeRequirementType<?>, RecipeRequirement> pRecipeRequirements){
        for (Map.Entry<RecipeRequirementType<?>, RecipeRequirement> entry : pRecipeRequirements.entrySet()) {
            pJson = entry.getKey().toJson(pJson, entry.getValue());
        }
        return pJson;
    }
*/

    public static <RRT extends RecipeRequirementType<?>> void toBuffer(FriendlyByteBuf buffer, IRecipeWithRequirements pRecipe){
        pRecipe.getEnabledRequirements().forEach(recipeRequirementType -> {
            recipeRequirementType.toNetwork(buffer,pRecipe.getRequirement(recipeRequirementType));
        });
    }

    public static ArrayList<String> checkRequirements(Map<RecipeRequirementType<?>, RecipeRequirement<?>> pRecipeRequirements, BlockEntity pBlockEntity){
        ArrayList<String> missingRequirements = new ArrayList<>();
        pRecipeRequirements.forEach((recipeRequirementType, recipeRequirement) -> {
            if(!recipeRequirement.test(pBlockEntity.getLevel(),pBlockEntity))
                missingRequirements.add(recipeRequirement.getType().getId());
        });
        return missingRequirements;
    }

}
