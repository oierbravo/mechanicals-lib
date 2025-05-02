package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;

public abstract class BaseRecipeParams implements IBaseRecipeParams {
    public ResourceLocation id;
    public ArrayList<RecipeRequirement> recipeRequirements;

    protected BaseRecipeParams(ResourceLocation id) {
        this.id = id;
        recipeRequirements = new ArrayList<>();
    }
}
