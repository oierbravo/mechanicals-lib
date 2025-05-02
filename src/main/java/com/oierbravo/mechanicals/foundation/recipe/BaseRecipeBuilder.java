package com.oierbravo.mechanicals.foundation.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.data.recipes.FinishedRecipe;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.NotCondition;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public abstract class BaseRecipeBuilder<R extends BaseRecipe<?,P>, P extends BaseRecipeParams> {

    protected P params;
    protected List<RecipeRequirement> recipeRequirements;
    protected List<ICondition> recipeConditions;

    public BaseRecipeBuilder( ResourceLocation id){
        recipeRequirements = new ArrayList<>();
        recipeConditions = new ArrayList<>();
    }

    public abstract R build();

    public void build(Consumer<FinishedRecipe> consumer) {
        consumer.accept(new DataGenResult<>(build(), recipeConditions));
    }

    public BaseRecipeBuilder<R,P> withRequirement(RecipeRequirement requirement){
        params.recipeRequirements.add(requirement);
        return this;
    }
    public BaseRecipeBuilder<R,P> withRequirements(List<RecipeRequirement> recipeRequirements) {
        recipeRequirements.forEach(this::withRequirement);
        return this;
    }

    public BaseRecipeBuilder<R,P> whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    public BaseRecipeBuilder<R,P> whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public BaseRecipeBuilder<R,P> withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return this;
    }

    public static class DataGenResult<S extends BaseRecipe<?,?>> implements FinishedRecipe {

        private List<ICondition> recipeConditions;
        private BaseRecipeSerializer<S,?> serializer;
        private ResourceLocation id;
        private S recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(S recipe, List<ICondition> recipeConditions) {
            this.recipe = recipe;
            this.recipeConditions = recipeConditions;
            ResourceLocation typeId = recipe.getId();

            if (!(recipe.getSerializer() instanceof BaseRecipeSerializer))
                throw new IllegalStateException("Cannot datagen Recipe of type: " + typeId);

            this.id = new ResourceLocation(recipe.getId().getNamespace(),
                    typeId.getPath() + "/" + recipe.getId().getPath());
            this.serializer = (BaseRecipeSerializer<S, ?>) recipe.getSerializer();
        }

        @Override
        public void serializeRecipeData(JsonObject json) {
            serializer.write(json, recipe);
            if (recipeConditions.isEmpty())
                return;

            JsonArray conds = new JsonArray();
            recipeConditions.forEach(c -> conds.add(CraftingHelper.serialize(c)));
            json.add("conditions", conds);
        }

        @Override
        public ResourceLocation getId() {
            return id;
        }

        @Override
        public RecipeSerializer<?> getType() {
            return serializer;
        }

        @Override
        public JsonObject serializeAdvancement() {
            return null;
        }

        @Override
        public ResourceLocation getAdvancementId() {
            return null;
        }

    }
}
