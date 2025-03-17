package com.oierbravo.mechanicals.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MaxSpeedRequirement(Float speed) implements IRecipeRequirement {
    public static String ID = "max_speed";
    public static MapCodec<MaxSpeedRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.optionalFieldOf("value", null).forGetter(MaxSpeedRequirement::speed)).apply(builder, MaxSpeedRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MaxSpeedRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, MaxSpeedRequirement::speed,
            MaxSpeedRequirement::new
    );
    public static MaxSpeedRequirement of(Float speed){
        return new MaxSpeedRequirement(speed);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(pBlockEntity instanceof KineticBlockEntity){
            return ((KineticBlockEntity) pBlockEntity).getSpeed() <= speed;
        }
        return false;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalRecipeRequirementTypes.MAX_SPEED.get();
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
