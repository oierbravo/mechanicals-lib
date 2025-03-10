package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonObject;
import com.mojang.serialization.MapCodec;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public abstract class BaseRecipeSerializer<R extends BaseRecipe<?,?>, B extends BaseRecipeBuilder<R,?>> implements RecipeSerializer<R> {
    protected final List<RecipeRequirementType<?>> enabledRecipeRequirements;

    public BaseRecipeSerializer(List<RecipeRequirementType<?>> pEnabledRecipeRequirements) {
        this.enabledRecipeRequirements = pEnabledRecipeRequirements;
    }
}
