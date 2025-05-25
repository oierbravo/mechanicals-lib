package com.oierbravo.mechanicals.foundation.data;

import com.simibubi.create.content.equipment.sandPaper.SandPaperPolishingRecipe;
import com.simibubi.create.content.fluids.transfer.EmptyingRecipe;
import com.simibubi.create.content.fluids.transfer.FillingRecipe;
import com.simibubi.create.content.kinetics.crusher.CrushingRecipe;
import com.simibubi.create.content.kinetics.deployer.DeployerApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ItemApplicationRecipe;
import com.simibubi.create.content.kinetics.deployer.ManualApplicationRecipe;
import com.simibubi.create.content.kinetics.fan.processing.HauntingRecipe;
import com.simibubi.create.content.kinetics.fan.processing.SplashingRecipe;
import com.simibubi.create.content.kinetics.millstone.MillingRecipe;
import com.simibubi.create.content.kinetics.mixer.CompactingRecipe;
import com.simibubi.create.content.kinetics.mixer.MixingRecipe;
import com.simibubi.create.content.kinetics.press.PressingRecipe;
import com.simibubi.create.content.kinetics.saw.CuttingRecipe;
import com.simibubi.create.content.processing.recipe.ProcessingRecipeBuilder;
import com.simibubi.create.content.processing.sequenced.SequencedAssemblyRecipeBuilder;
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
    protected ProcessingRecipeBuilder<CuttingRecipe> createCutting(String name){
        return new ProcessingRecipeBuilder<>(CuttingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<MillingRecipe> createMilling(String name){
        return new ProcessingRecipeBuilder<>(MillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<MixingRecipe> createMixing(String name){
        return new ProcessingRecipeBuilder<>(MixingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<CompactingRecipe> createCompacting(String name){
        return new ProcessingRecipeBuilder<>(CompactingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<PressingRecipe> createPressing(String name){
        return new ProcessingRecipeBuilder<>(PressingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<SandPaperPolishingRecipe> createSandPaperPolishing(String name){
        return new ProcessingRecipeBuilder<>(SandPaperPolishingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<SplashingRecipe> createSplashing(String name){
        return new ProcessingRecipeBuilder<>(SplashingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<HauntingRecipe> createHaunting(String name){
        return new ProcessingRecipeBuilder<>(HauntingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<FillingRecipe> createFilling(String name){
        return new ProcessingRecipeBuilder<>(FillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<EmptyingRecipe> createEmptying(String name){
        return new ProcessingRecipeBuilder<>(EmptyingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<DeployerApplicationRecipe> createDeploying(String name){
        return new ProcessingRecipeBuilder<>(DeployerApplicationRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ProcessingRecipeBuilder<ItemApplicationRecipe> createItemApplication(String name){
        return new ProcessingRecipeBuilder<>(ManualApplicationRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected SequencedAssemblyRecipeBuilder createSequencedAssembly(String name){
        return new SequencedAssemblyRecipeBuilder(resourceLocationSupplier.get().withPath(name));
    }

}

