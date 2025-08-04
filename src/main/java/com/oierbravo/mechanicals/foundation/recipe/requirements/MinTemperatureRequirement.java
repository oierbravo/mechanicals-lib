package com.oierbravo.mechanicals.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MinTemperatureRequirement(Float temperature) implements IRecipeRequirement {
    public static String ID = "min_temperature";
    public static MapCodec<MinTemperatureRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.optionalFieldOf("value", null).forGetter(MinTemperatureRequirement::temperature)).apply(builder, MinTemperatureRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MinTemperatureRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, MinTemperatureRequirement::temperature,
            MinTemperatureRequirement::new
    );
    public static MinTemperatureRequirement of(Float speed){
        return new MinTemperatureRequirement(speed);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        Holder<Biome> blockEntityBiome = pLevel.getBiome(pBlockEntity.getBlockPos());
        Biome biome = blockEntityBiome.value();
        float biomeTemperature = biome.getBaseTemperature();
        return biomeTemperature >= temperature;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalRecipeRequirementTypes.MIN_TEMPERATURE.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return temperature.toString();
    }
}
