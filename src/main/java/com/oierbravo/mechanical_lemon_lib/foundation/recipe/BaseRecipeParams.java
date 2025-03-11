package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseRecipeParams implements IBaseRecipeParams {
    public ResourceLocation id;
    public ArrayList<IRecipeRequirement> recipeRequirements;
    protected ArrayList<ICondition> conditions;

    protected BaseRecipeParams(ResourceLocation id) {
        this.id = id;
        recipeRequirements = new ArrayList<>();
        conditions = new ArrayList<>();
    }
}
