package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;

public record RecipeRequirementsComponent() implements RecipeComponent<IRecipeRequirement> {
    public static final TypeInfo TYPE_INFO = TypeInfo.of(IRecipeRequirement.class);
    public static final RecipeComponentType<IRecipeRequirement> REQUIREMENT = RecipeComponentType.unit(Mechanicals.asResource("requirement"), new RecipeRequirementsComponent());

    @Override
    public RecipeComponentType<?> type() {
        return REQUIREMENT;
    }

    @Override
    public Codec<IRecipeRequirement> codec() {
        return IRecipeRequirement.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public IRecipeRequirement wrap(RecipeScriptContext cx, Object from) {
        return RecipeComponent.super.wrap(cx, from);
    }
}
