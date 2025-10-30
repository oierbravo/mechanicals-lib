package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;

public record RecipeRequirementsComponent() implements RecipeComponent<IRecipeRequirement> {
    public static final RecipeComponentType<IRecipeRequirement> RECIPE_REQUIREMENT = RecipeComponentType.unit(Mechanicals.asResource("recipe_requirement"),new RecipeRequirementsComponent());

    @Override
    public RecipeComponentType<?> type() {
        return RECIPE_REQUIREMENT;
    }

    @Override
    public Codec<IRecipeRequirement> codec() {
        return IRecipeRequirement.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(IRecipeRequirement.class);
    }

    @Override
    public IRecipeRequirement wrap(Context cx, KubeRecipe recipe, Object from) {
        return RecipeComponent.super.wrap(cx, recipe, from);
    }
    @Override
    public String toString() {
        return "mechanical_recipe_requirement";
    }
}
