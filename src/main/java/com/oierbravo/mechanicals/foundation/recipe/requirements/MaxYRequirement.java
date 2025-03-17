package com.oierbravo.mechanicals.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public record MaxYRequirement(Integer maxY) implements IRecipeRequirement {
    public static String ID = "max_y";

    public static MapCodec<MaxYRequirement> CODEC = RecordCodecBuilder
            .mapCodec((builder)
                    -> builder
                    .group(Codec.INT.optionalFieldOf("value", null)
                    .forGetter(MaxYRequirement::maxY)).apply(builder, MaxYRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, MaxYRequirement> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, MaxYRequirement::maxY,
            MaxYRequirement::new
    );
    public static MaxYRequirement of(Integer maxY){
        return new MaxYRequirement(maxY);
    }

    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(maxY == null)
            return true;
        BlockPos pos = pBlockEntity.getBlockPos();

        return pos.getCenter().y <= maxY;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalRecipeRequirementTypes.MAX_Y.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return maxY.toString();
    }
}
