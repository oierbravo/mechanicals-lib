package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.neoforged.neoforge.fluids.FluidStack;

public record CreateFluidIngredientComponent() implements RecipeComponent<FluidIngredient> {
    public static final RecipeComponent<FluidIngredient> FLUID_INGREDIENT = new CreateFluidIngredientComponent();


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
}
