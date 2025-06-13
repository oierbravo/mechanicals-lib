package com.oierbravo.mechanicals.foundation.recipe;

import com.oierbravo.mechanicals.foundation.data.CompatMods;
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

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public abstract class AbstractMechanicalRecipeBuilder<R extends AbstractMechanicalRecipe<?,P>, P extends AbstractMechanicalRecipeParams, BRB extends AbstractMechanicalRecipeBuilder<R,P,?>> {
    protected final Map<String, Criterion<?>> criteria = new LinkedHashMap<>();
    protected P params;
    protected ResourceLocation id;

    public AbstractMechanicalRecipeBuilder(){
    }

    public abstract R build();
    public abstract BRB create();

    public BRB withRequirement(IRecipeRequirement requirement){
        params.recipeRequirements.add(requirement);
        return (BRB) this;
    }
    public BRB withRequirements(List<IRecipeRequirement> pRecipeRequirements) {
        pRecipeRequirements.forEach(iRecipeRequirement -> {
            if(!params.recipeRequirements.contains(iRecipeRequirement))
                params.recipeRequirements.add(iRecipeRequirement);
        });
        return (BRB) this;
    }

    public BRB whenModLoaded(String modid) {
        return withCondition(new ModLoadedCondition(modid));
    }
    public BRB whenModMissing(String modid) {
        return withCondition(new NotCondition(new ModLoadedCondition(modid)));
    }

    public BRB forCompat(CompatMods mod) {
        return withCondition(new ModLoadedCondition(mod.getId()));
    }
    public BRB withCondition(ICondition condition) {
        params.conditions.add(condition);
        return (BRB) this;
    }
    public BRB withConditions(List<ICondition> conditions) {
        params.conditions.addAll(conditions);
        return (BRB) this;
    }
    public BRB withoutCreateFromNothing(){
        params.conditions.add(new NotCondition(new ModLoadedCondition("create_from_nothing")));
        return (BRB) this;
    }
    public BRB withId(ResourceLocation id){
        this.id = id;
        return (BRB) this;
    }

    public void save(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);

        recipeOutput.accept(resourceLocation, build(), advancement.build(resourceLocation.withPrefix("recipes/")));
    }

    public void saveCompat(RecipeOutput recipeOutput, ResourceLocation resourceLocation) {
        Advancement.Builder advancement = recipeOutput.advancement()
                .addCriterion("has_the_recipe", RecipeUnlockedTrigger.unlocked(resourceLocation))
                .rewards(AdvancementRewards.Builder.recipe(resourceLocation))
                .requirements(AdvancementRequirements.Strategy.OR);
        this.criteria.forEach(advancement::addCriterion);
        String compatId = resourceLocation.getPath().split("/")[0] + "/compat/" + resourceLocation.getPath().split("/")[1];
        ResourceLocation compatResourceLocation = ResourceLocation.fromNamespaceAndPath(resourceLocation.getNamespace(), compatId);
        recipeOutput.accept(
                compatResourceLocation,
                build(), advancement.build(resourceLocation.withPrefix("recipes/")));
    }


    public void save(RecipeOutput recipeOutput) {
        save(recipeOutput, this.id);
    }

    public void saveCompat(RecipeOutput recipeOutput) {
        saveCompat(recipeOutput, this.id);
    }

    public BRB with(Consumer<BRB> consummer){
        consummer.accept((BRB) this);
        return (BRB) this;
    }
}
