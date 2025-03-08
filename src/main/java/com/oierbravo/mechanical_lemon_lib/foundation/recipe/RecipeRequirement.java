package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRegistries;
import com.oierbravo.mechanical_lemon_lib.utility.LibLang;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.function.Function;

public abstract class RecipeRequirement<V>{
   //public static Codec<RecipeRequirement<>> CODEC = MechanicalLemonRegistries.RECIPE_REQUIREMENT_SERIALIZERS.byNameCodec().dispatch(RecipeRequirement::codec, Function.identity());
   // public static Codec<? extends List<?>> LIST_CODEC = CODEC.listOf();

    public abstract RecipeRequirementType<?> getType();

    public abstract boolean test(Level pLevel, BlockEntity pBlockEntity);
    public abstract V getValue();
    public abstract boolean isPresent();
    public abstract String toString();
    public Component toRequirementComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".tooltip", toString()).component();
    };
    public Component toMissingComponent(){
        return LibLang.translate("ui.recipe_requirement." + getType().getId() + ".missing", toString()).component();
    }

    public abstract MapCodec<? extends RecipeRequirement<V>> codec();
}