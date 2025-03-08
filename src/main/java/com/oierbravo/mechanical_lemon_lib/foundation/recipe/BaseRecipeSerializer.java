package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class BaseRecipeSerializer<R extends BaseRecipe<?,?>, B extends BaseRecipeBuilder<R,?>> implements RecipeSerializer<R> {
    protected final List<RecipeRequirementType<?>> enabledRecipeRequirements;


    public BaseRecipeSerializer(List<RecipeRequirementType<?>> pEnabledRecipeRequirements) {
        this.enabledRecipeRequirements = pEnabledRecipeRequirements;
    }

    protected abstract B readFromJson(ResourceLocation recipeId, JsonObject json);
    protected abstract B readFromBuffer(ResourceLocation recipeId, FriendlyByteBuf buffer);

    protected abstract void writeToJson(JsonObject json, R recipe);
    protected abstract void writeToBuffer(FriendlyByteBuf buffer, R recipe);


    public final void write(JsonObject json, R recipe) {
        writeToJson(json, recipe);

    }

    /*@Override
    public @NotNull R fromJson(@NotNull ResourceLocation pRecipeId, @NotNull JsonObject pSerializedRecipe) {
        return readFromJson(pRecipeId, pSerializedRecipe)
                .withRequirements(RecipeRequirementsUtils.fromJson(pSerializedRecipe, enabledRecipeRequirements))
                .build();
    }


    @Override
    public @Nullable R fromNetwork(@NotNull ResourceLocation pRecipeId, @NotNull FriendlyByteBuf pBuffer) {
        return readFromBuffer(pRecipeId, pBuffer).build();
    }

    @Override
    public void toNetwork(@NotNull FriendlyByteBuf pBuffer, @NotNull R pRecipe) {
        writeToBuffer(pBuffer, pRecipe);
    }*/
}
