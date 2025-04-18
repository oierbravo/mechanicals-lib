package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.foundation.ingredient.CountableIngredient;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record CountableIngredientComponent() implements RecipeComponent<CountableIngredient> {
    public static final RecipeComponent<CountableIngredient> COUNTABLE_INGREDIENT = new CountableIngredientComponent();

    @Override
    public Codec<CountableIngredient> codec() {
        return CountableIngredient.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(CountableIngredient.class);
    }

    @Override
    public CountableIngredient wrap(Context cx, KubeRecipe recipe, Object from) {
        if(from instanceof Ingredient ingredient)
            return CountableIngredient.of(ingredient,1);
        if(from instanceof ItemStack itemStack)
            return CountableIngredient.of(itemStack);
        return RecipeComponent.super.wrap(cx, recipe, from);
    }

}
