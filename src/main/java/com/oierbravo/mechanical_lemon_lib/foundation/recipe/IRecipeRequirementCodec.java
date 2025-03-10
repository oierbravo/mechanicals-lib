package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.MapCodec;

public interface IRecipeRequirementCodec<RR extends RecipeRequirement<?>> {
    public MapCodec<RR> codec();
}
