package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BaseRecipe<T extends Container, P extends BaseRecipeParams> implements Recipe<T>, IRecipeWithRequirements {

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

    @Override
    public ResourceLocation getId() {
        return id;
    }

}
