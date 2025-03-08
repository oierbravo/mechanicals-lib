package com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.simibubi.create.content.kinetics.base.KineticBlockEntity;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

public class SpeedRequirement extends RecipeRequirement<Float>{
    public static final RecipeRequirementType<?> TYPE = new SpeedRequirementType();
    public static final SpeedRequirement EMPTY = new SpeedRequirement();

    private Float value;

    public SpeedRequirement() {

    }

    public SpeedRequirement(float pValue) {
        value = pValue;
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

    @Override
    public MapCodec<? extends RecipeRequirement<Float>> codec() {
        return null;
    }

    public Float getValue() {
        return value;
    }

    public static SpeedRequirement of(float pValue) {
        return new SpeedRequirement(pValue);
    }


    @Override
    public RecipeRequirementType<?> getType() {
        return TYPE;
    }



    private static class SpeedRequirementType extends RecipeRequirementType<SpeedRequirement>{
        public static MapCodec<SpeedRequirement> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.FLOAT.fieldOf("min_speed").forGetter(SpeedRequirement::getValue)).apply(builder,SpeedRequirement::new));

        public SpeedRequirementType(String id) {
            super(id);
        }
        public SpeedRequirementType() {
            super("min_speed");
        }

        @Override
        public SpeedRequirement fromJson(JsonObject pJson) {
            if (GsonHelper.isValidNode(pJson, this.getId())) {
                return of(pJson.get(this.getId()).getAsFloat());
            }
            return EMPTY;
        }

        @Override
        public JsonObject toJson(JsonObject pJson, RecipeRequirement pRecipeRequirement) {
            if(!pRecipeRequirement.isPresent())
                return pJson;
            pJson.addProperty(this.getId(), pRecipeRequirement.toString());
            return pJson;
        }

        @Override
        public SpeedRequirement fromNetwork(FriendlyByteBuf buffer) {
            boolean hasRequirement = buffer.readBoolean();
            if(hasRequirement) {
                return of(buffer.readFloat());
            }
            return SpeedRequirement.EMPTY;
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, RecipeRequirement pRecipeRequirement) {
            if(pRecipeRequirement == null)
                pRecipeRequirement = new SpeedRequirement();
            if(pRecipeRequirement instanceof SpeedRequirement){
                buffer.writeBoolean(pRecipeRequirement.isPresent());
                if(pRecipeRequirement.isPresent())
                    buffer.writeFloat(((SpeedRequirement) pRecipeRequirement).getValue());
            }

        }

        @Override
        public MapCodec<SpeedRequirement> codec() {
            return CODEC;
        }

    }
}
