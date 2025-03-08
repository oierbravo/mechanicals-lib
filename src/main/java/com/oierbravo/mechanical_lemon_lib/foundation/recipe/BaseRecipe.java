package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BaseRecipe<T extends RecipeInput, P extends BaseRecipeParams> implements Recipe<T>, IRecipeWithRequirements {

    protected ResourceLocation id;

    protected Map<RecipeRequirementType<?>, RecipeRequirement> recipeRequirements = new HashMap<>();

    protected static List<RecipeRequirementType<?>> enabledRecipeRequirements = List.of();


    public BaseRecipe(P params){
        this.id = params.id;

        params.recipeRequirements.forEach(
                recipeRequirement -> recipeRequirements.put(recipeRequirement.getType(), recipeRequirement)
        );

    }
    public List<RecipeRequirementType<?>> getEnabledRequirements() {
        return enabledRecipeRequirements;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

}
