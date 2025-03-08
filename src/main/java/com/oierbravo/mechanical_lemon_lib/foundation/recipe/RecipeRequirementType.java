package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRegistries;
import net.minecraft.network.FriendlyByteBuf;

import java.util.function.Function;

public abstract class RecipeRequirementType<RR extends RecipeRequirement> {

    private final String id;

    public RecipeRequirementType(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public abstract RR fromJson(JsonObject pJson);

    public abstract JsonObject toJson(JsonObject pJson, RecipeRequirement pRecipeRequirement);

    public abstract RR fromNetwork(FriendlyByteBuf buffer);

    public abstract void toNetwork(FriendlyByteBuf buffer, RecipeRequirement pRecipeRequirement);
    public boolean isProcessBlocker(){
        return this instanceof IProcessBlockingRequirement;
    }
    public abstract MapCodec<RR> codec();
}
