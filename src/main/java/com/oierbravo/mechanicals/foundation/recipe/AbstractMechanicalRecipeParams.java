package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

import java.util.ArrayList;

public abstract class AbstractMechanicalRecipeParams {
    public ResourceLocation id;
    public ArrayList<IRecipeRequirement> recipeRequirements;
    public ArrayList<ICondition> conditions;

    protected AbstractMechanicalRecipeParams(ResourceLocation id) {
        this.id = id;
        this.recipeRequirements = new ArrayList<>();
        this.conditions = new ArrayList<>();
    }
}
