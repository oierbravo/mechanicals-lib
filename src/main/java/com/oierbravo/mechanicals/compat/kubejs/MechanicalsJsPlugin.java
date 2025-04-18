package com.oierbravo.mechanicals.compat.kubejs;

import com.oierbravo.mechanicals.compat.kubejs.bindings.BlockPredicateBuilder;
import com.oierbravo.mechanicals.compat.kubejs.bindings.ProcessingOutputBuilder;
import com.oierbravo.mechanicals.compat.kubejs.components.*;
import com.oierbravo.mechanicals.compat.kubejs.bindings.RecipeRequirementBuilder;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.recipe.schema.RecipeComponentFactoryRegistry;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.neoforged.fml.ModList;

public class MechanicalsJsPlugin implements KubeJSPlugin {

    @Override
    public void registerRecipeComponents(RecipeComponentFactoryRegistry registry) {
        registry.register(ProcessingOutputComponent.OUTPUT);
        registry.register(BlockPredicateComponent.BLOCK_PREDICATE);
        registry.register(RecipeRequirementsComponent.RECIPE_REQUIREMENT);
        registry.register(ResourceLocationComponent.RESOURCE_LOCATION);
        registry.register(CreateFluidIngredientComponent.FLUID_INGREDIENT);
        registry.register(CountableIngredientComponent.COUNTABLE_INGREDIENT);
    }

    @Override
    public void registerBindings(BindingRegistry registry) {
        if (registry.type().isServer()) {
            if(ModList.get().isLoaded("create"))
                registry.add("Output", ProcessingOutputBuilder.class);
            registry.add("BlockPredicate", BlockPredicateBuilder.class);
            registry.add("RecipeRequirement", RecipeRequirementBuilder.class);
        }
    }
}