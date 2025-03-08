package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.ParametersAreNonnullByDefault;
import javax.swing.text.html.Option;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class BaseRecipe<T extends RecipeInput, P extends BaseRecipeParams> implements Recipe<T>, IRecipeWithRequirements {

    protected ResourceLocation id;

    protected HashMap<RecipeRequirementType<?>, RecipeRequirement> recipeRequirements = new HashMap<>();

    protected static List<RecipeRequirementType<?>> enabledRecipeRequirements = List.of();

    protected List<ICondition> conditions;


    public BaseRecipe(P params){
        this.id = params.id;

        params.recipeRequirements.forEach(
                recipeRequirement -> recipeRequirements.put(recipeRequirement.getType(), recipeRequirement)
        );
        this.conditions = params.conditions;
    }
    public List<RecipeRequirementType<?>> getEnabledRequirements() {
        return enabledRecipeRequirements;
    }

    public Map<RecipeRequirementType<?>, RecipeRequirement> getRecipeRequirements(){
        return recipeRequirements;
    }
    public List<ICondition> getConditions(){
        return conditions;
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

}
