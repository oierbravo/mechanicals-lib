package com.oierbravo.mechanicals.foundation.data;

import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
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
    protected ProcessingRecipeBuilder<CrushingRecipe> createCrushing(String name){
        return new ProcessingRecipeBuilder<>(CrushingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<MillingRecipe> createMilling(String name){
        return new ProcessingRecipeBuilder<>(MillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<MixingRecipe> createMixing(String name){
        return new ProcessingRecipeBuilder<>(MixingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<ItemApplicationRecipe> createItemApplication(String name){
        return new ProcessingRecipeBuilder<>(ManualApplicationRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
}

