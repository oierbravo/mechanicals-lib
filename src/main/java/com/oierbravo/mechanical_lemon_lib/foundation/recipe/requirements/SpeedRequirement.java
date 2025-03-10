package com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public class SpeedRequirement extends RecipeRequirement<Float> {
    public static final RecipeRequirementType TYPE = new SpeedRequirementType();
    public static final SpeedRequirement EMPTY = new SpeedRequirement();


    private Float value;

    public SpeedRequirement() {

    }
    public SpeedRequirement(Float value) {
        this.value = value;
    }

    public SpeedRequirement(Optional<Float> value) {
        this(value.get());
    }


    @Override
    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(pBlockEntity instanceof KineticBlockEntity){
            return Math.abs(((KineticBlockEntity) pBlockEntity).getSpeed()) >= value;
        }
        return true;
    }

    @Override
    public boolean isPresent() {
        return value != null;
    }

    @Override
    public String toString() {
        if(value == null)
            return null;
        return value.toString();
    }


    public Optional<Float> getValue() {
        if(value == null)
            return Optional.empty();
        return Optional.of(value);
    }

    public static SpeedRequirement of(float pValue) {
        return new SpeedRequirement(pValue);
    }


    @Override
    public RecipeRequirementType<?> getType() {
        return TYPE;
    }

    private static class SpeedRequirementType extends RecipeRequirementType<SpeedRequirement> {
        public static MapCodec<SpeedRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.optionalFieldOf("min_speed").forGetter(SpeedRequirement::getValue)).apply(builder,SpeedRequirement::new));


        public SpeedRequirementType(String id) {
            super(id);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RecipeRequirement<?> recipeRequirement) {

        }

        @Override
        public SpeedRequirement fromNetwork(FriendlyByteBuf buffer) {
            return null;
        }

        public SpeedRequirementType() {
            super("min_speed");
        }

        @Override
        public MapCodec<SpeedRequirement> codec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SpeedRequirement> streamCodec() {
            return null;
        }



    }
}
