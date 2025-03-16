package com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRecipeRequirementTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MinSpeedRequirement(Float speed) implements IRecipeRequirement {
    public static String ID = "min_speed";
    public static MapCodec<MinSpeedRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.optionalFieldOf("value", null).forGetter(MinSpeedRequirement::speed)).apply(builder, MinSpeedRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MinSpeedRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, MinSpeedRequirement::speed,
            MinSpeedRequirement::new
    );
    public static MinSpeedRequirement of(Float speed){
        return new MinSpeedRequirement(speed);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(pBlockEntity instanceof KineticBlockEntity){
            return ((KineticBlockEntity) pBlockEntity).getSpeed() >= speed;
        }
        return false;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalLemonRecipeRequirementTypes.MIN_SPEED.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return speed.toString();
    }
}
