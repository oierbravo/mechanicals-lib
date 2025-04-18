package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeInput;
import net.neoforged.neoforge.common.conditions.ICondition;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public abstract class AbstractMechanicalRecipe<T extends RecipeInput, P extends AbstractMechanicalRecipeParams> implements Recipe<T>, IRecipeWithRequirements {

    protected ResourceLocation id;

    protected ArrayList<IRecipeRequirement> recipeRequirements;

    protected ArrayList<ICondition> conditions;

    public AbstractMechanicalRecipe(P params){
        recipeRequirements = params.recipeRequirements;
        conditions = params.conditions;
    }
    public List<ICondition> getConditions(){
        return conditions;
    }
    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return true;
    }

}
