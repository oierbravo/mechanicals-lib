package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecipeParams implements IBaseRecipeParams {
    public ResourceLocation id;
    public ArrayList<RecipeRequirement<?>> recipeRequirements;
    protected List<ICondition> conditions;

    protected BaseRecipeParams(ResourceLocation id) {
        this.id = id;
        recipeRequirements = new ArrayList<>();
        conditions = List.of();
    }
}
