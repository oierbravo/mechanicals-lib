package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRegistries;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IRecipeWithRequirements {
    Codec<IRecipeRequirementType> CODEC = MechanicalLemonRegistries.RECIPE_REQUIREMENT_TYPES.byNameCodec().dispatch(t -> RecipeRequirementType.codec(t), Function.identity());
    Codec<? extends List<?>> LIST_CODEC = CODEC.listOf();

    Map<RecipeRequirementType<?>, RecipeRequirement<?>> getRecipeRequirements();
    List<RecipeRequirementType<?>> getEnabledRequirements();


    default <RRT extends RecipeRequirementType<?>> RecipeRequirement<?> getRequirement(RRT type) {
        return getRecipeRequirements().get(type);
    }

    boolean checkRequirements(Level pLevel, BlockEntity pBlockEntity);

}
