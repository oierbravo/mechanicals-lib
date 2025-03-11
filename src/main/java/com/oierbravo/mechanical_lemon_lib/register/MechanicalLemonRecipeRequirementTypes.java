package com.oierbravo.mechanical_lemon_lib.register;

import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.MechanicalLemonLib;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements.MinYRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements.SpeedRequirement;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.minecraft.network.RegistryFriendlyByteBuf;


import java.util.function.Supplier;

public class MechanicalLemonRecipeRequirementTypes {
    public static final DeferredRegister<RecipeRequirementType<?>> RECIPE_REQUIREMENT_TYPES =
            DeferredRegister.create(MechanicalLemonRegistries.Keys.RECIPE_REQUIREMENT, MechanicalLemonLib.MODID);

    public static final Supplier<RecipeRequirementType<SpeedRequirement>> SPEED =
            register(SpeedRequirement.ID, SpeedRequirement.CODEC, SpeedRequirement.STREAM_CODEC);

    public static final Supplier<RecipeRequirementType<MinYRequirement>> MIN_Y =
            register(MinYRequirement.ID, MinYRequirement.CODEC, MinYRequirement.STREAM_CODEC);

    public static void init(IEventBus modEventBus) {
        RECIPE_REQUIREMENT_TYPES.register(modEventBus);
    }

    private static <RR extends IRecipeRequirement, RRT extends RecipeRequirementType<RR>> Supplier<RRT> register(String name, MapCodec<RR> codec, StreamCodec<RegistryFriendlyByteBuf, RR> streamCodec) {
        //noinspection unchecked
        return RECIPE_REQUIREMENT_TYPES.register(name, () -> (RRT) new RecipeRequirementType<>(codec, streamCodec));
    }
}

