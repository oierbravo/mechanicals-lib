package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public abstract class RecipeRequirementType<RR extends RecipeRequirement<?>> {

    private final String id;
    public RecipeRequirementType(String id) {
        this.id = id;
    }
    public String getId() {
        return id;
    }

    public abstract void toNetwork(FriendlyByteBuf buffer, RecipeRequirement<?> recipeRequirement);
    public abstract RR fromNetwork(FriendlyByteBuf buffer);

    public MapCodec<RR> codec(IRecipeRequirementType t) {
        return RR.typeCodec(this);
    }

    public StreamCodec<RegistryFriendlyByteBuf, RR> streamCodec(){
        return StreamCodec.of(this::toNetwork, this::fromNetwork);
    };

    public String toString() {
        return getId();
    }
}
