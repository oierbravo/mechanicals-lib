package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.neoforged.neoforge.fluids.FluidStack;

public record CreateFluidIngredientComponent() implements RecipeComponent<FluidIngredient> {
    public static final RecipeComponentType<FluidIngredient> FLUID_INGREDIENT = RecipeComponentType.unit(Mechanicals.asResource("create_fluid_ingredient"),new CreateFluidIngredientComponent());

    @Override
    public RecipeComponentType<?> type() {
        return FLUID_INGREDIENT;
    }

    @Override
    public Codec<FluidIngredient> codec() {
        return FluidIngredient.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(FluidIngredient.class);
    }

    @Override
    public FluidIngredient wrap(Context cx, KubeRecipe recipe, Object from) {
        if(from instanceof FluidStack fluidStack)
            return FluidIngredient.fromFluidStack(fluidStack);
        return RecipeComponent.super.wrap(cx, recipe, from);
    }

    @Override
    public String toString() {
        return "mechanical_create_fluid_ingredient";
    }
}
