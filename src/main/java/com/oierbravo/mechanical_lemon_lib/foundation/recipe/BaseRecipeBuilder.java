package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.advancements.Criterion;
import net.minecraft.data.recipes.RecipeBuilder;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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

    public void build(Consumer<RecipeBuilder> consumer) {
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

    public static class DataGenResult<S extends BaseRecipe<?,?>> implements RecipeBuilder {

        private List<ICondition> recipeConditions;
        private BaseRecipeSerializer<S,?> serializer;
        private ResourceLocation id;
        protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
        @Nullable
        protected String group;

        private S recipe;

        @SuppressWarnings("unchecked")
        public DataGenResult(S recipe, List<ICondition> recipeConditions) {
            this.recipe = recipe;
            this.recipeConditions = recipeConditions;
            ResourceLocation typeId = recipe.id;

            if (!(recipe.getSerializer() instanceof BaseRecipeSerializer))
                throw new IllegalStateException("Cannot datagen Recipe of type: " + typeId);

            this.id = ResourceLocation.fromNamespaceAndPath(recipe.id.getNamespace(),
                    typeId.getPath() + "/" + recipe.id.getPath());
            this.serializer = (BaseRecipeSerializer<S, ?>) recipe.getSerializer();
        }

        @Override
        public RecipeBuilder unlockedBy(String name, Criterion<?> criterion) {
            this.criteria.put(name, criterion);
            return this;
        }

        @Override
        public RecipeBuilder group(@Nullable String s) {
            this.group = group;
            return this;        }

        @Override
        public Item getResult() {
            return null;
        }

        @Override
        public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {

        }

        /*@Override
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
        }*/

    }
}
