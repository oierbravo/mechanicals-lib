package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.AdvancementRequirements;
import net.minecraft.advancements.AdvancementRewards;
import net.minecraft.advancements.Criterion;
import net.minecraft.advancements.critereon.RecipeUnlockedTrigger;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;
import net.neoforged.neoforge.common.conditions.NotCondition;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractMechanicalRecipeBuilder<R extends AbstractMechanicalRecipe<?,P>, P extends AbstractMechanicalRecipeParams, BRB extends AbstractMechanicalRecipeBuilder<R,P,?>> {
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();

    protected P params;
    protected ArrayList<IRecipeRequirement> recipeRequirements;
    protected ArrayList<ICondition> recipeConditions;

    public AbstractMechanicalRecipeBuilder(){
        recipeRequirements = new ArrayList<>();
        recipeConditions = new ArrayList<>();
    }

    public abstract R build();
    public abstract BRB create(ResourceLocation id);

    public BRB withRequirement(IRecipeRequirement requirement){
        params.recipeRequirements.add(requirement);
        return (BRB) this;
    }
    public BRB withRequirements(List<IRecipeRequirement> pRecipeRequirements) {
        params.recipeRequirements.addAll(pRecipeRequirements);
        return (BRB) this;
    }

    public BRB whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }

    public BRB whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public BRB withCondition(ICondition condition) {
        recipeConditions.add(condition);
        return (BRB) this;
    }
    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        recipeOutput.accept(resourceLocation, build(), advancement.build(params.id.withPrefix("recipes/")));
    }

    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, params.id);
    }

    public BRB with(Consumer<BRB> consummer){
        consummer.accept((BRB) this);
        return (BRB) this;
    }
}
