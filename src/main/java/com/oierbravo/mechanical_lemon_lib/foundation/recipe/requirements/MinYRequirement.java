package com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRecipeRequirementTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MinYRequirement(Integer minY) implements IRecipeRequirement{
    public static String ID = "min_y";

    public static MapCodec<MinYRequirement> CODEC = RecordCodecBuilder
            .mapCodec((builder)
                    -> builder
                    .group(Codec.INT.optionalFieldOf("value", null)
                    .forGetter(MinYRequirement::minY)).apply(builder,MinYRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MinYRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MinYRequirement::minY,
            MinYRequirement::new
    );
    public static MinYRequirement of(Integer minY){
        return new MinYRequirement(minY);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        return false;
    }

    @Override
    public boolean isPresent() {
        return false;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalLemonRecipeRequirementTypes.MIN_Y.get();
    }

    @Override
    public String toString() {
        return ID;
    }
}
