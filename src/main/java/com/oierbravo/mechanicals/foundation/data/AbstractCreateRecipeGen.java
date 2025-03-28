package com.oierbravo.mechanicals.foundation.data;

import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AbstractCreateRecipeGen extends RecipeProvider {
    Supplier<ResourceLocation> resourceLocationSupplier;

    public AbstractCreateRecipeGen(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, Supplier<ResourceLocation> resourceLocationSupplier) {
        super(output, registries);
        this.resourceLocationSupplier = resourceLocationSupplier;
    }

    protected ProcessingRecipeBuilder<MillingRecipe> createMilling(String name){
        return new ProcessingRecipeBuilder<>(MillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<MixingRecipe> createMixing(String name){
        return new ProcessingRecipeBuilder<>(MixingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
}

