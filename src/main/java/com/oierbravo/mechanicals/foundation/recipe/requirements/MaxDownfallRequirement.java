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

public record MaxDownfallRequirement(Float downfall) implements IRecipeRequirement {
    public static String ID = "max_temperature";
    public static MapCodec<MaxDownfallRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.optionalFieldOf("value", null).forGetter(MaxDownfallRequirement::downfall)).apply(builder, MaxDownfallRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MaxDownfallRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.FLOAT, MaxDownfallRequirement::downfall,
            MaxDownfallRequirement::new
    );
    public static MaxDownfallRequirement of(Float speed){
        return new MaxDownfallRequirement(speed);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        Holder<Biome> blockEntityBiome = pLevel.getBiome(pBlockEntity.getBlockPos());
        Biome biome = blockEntityBiome.value();
        float biomeDownfall = biome.getModifiedClimateSettings().downfall();
        return biomeDownfall <= downfall;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalRecipeRequirementTypes.MAX_DOWNFALL.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return downfall.toString();
    }
}
