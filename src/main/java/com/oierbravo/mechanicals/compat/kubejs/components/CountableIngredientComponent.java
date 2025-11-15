package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.foundation.ingredient.CountableIngredient;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

public record CountableIngredientComponent() implements RecipeComponent<CountableIngredient> {
    public static final TypeInfo TYPE_INFO = TypeInfo.of(CountableIngredient.class);
    public static final RecipeComponentType<CountableIngredient> COUNTABLE_INGREDIENT = RecipeComponentType.unit(Mechanicals.asResource("countable_ingredient"), new CountableIngredientComponent());

    @Override
    public RecipeComponentType<?> type() {
        return COUNTABLE_INGREDIENT;
    }

    @Override
    public Codec<CountableIngredient> codec() {
        return CountableIngredient.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public CountableIngredient wrap(RecipeScriptContext cx, Object from) {
        if(from instanceof Ingredient ingredient)
            return CountableIngredient.of(ingredient,1);
        if(from instanceof ItemStack itemStack)
            return CountableIngredient.of(itemStack);
        return RecipeComponent.super.wrap(cx, from);
    }

}
