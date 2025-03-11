package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public class RecipeRequirementType<RR extends IRecipeRequirement> {
    private final MapCodec<RR> codec;
    private final StreamCodec<RegistryFriendlyByteBuf,RR> streamCodec;

    public RecipeRequirementType(MapCodec<RR> codec, StreamCodec<RegistryFriendlyByteBuf, RR> streamCodec) {
        this.codec = codec;
        this.streamCodec = streamCodec;
    }

    public MapCodec<RR> codec() {
        return codec;
    }

    public StreamCodec<RegistryFriendlyByteBuf, RR> streamCodec() {
        return streamCodec;
    }
}
