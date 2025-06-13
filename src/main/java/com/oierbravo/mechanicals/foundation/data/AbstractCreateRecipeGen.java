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
import com.simibubi.create.content.processing.recipe.StandardProcessingRecipe;
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

    protected StandardProcessingRecipe.Builder<CrushingRecipe> createCrushing(String name){
        return new StandardProcessingRecipe.Builder<>(CrushingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<CuttingRecipe> createCutting(String name){
        return new StandardProcessingRecipe.Builder<>(CuttingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<MillingRecipe> createMilling(String name){
        return new StandardProcessingRecipe.Builder<>(MillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<MixingRecipe> createMixing(String name){
        return new StandardProcessingRecipe.Builder<>(MixingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<CompactingRecipe> createCompacting(String name){
        return new StandardProcessingRecipe.Builder<>(CompactingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<PressingRecipe> createPressing(String name){
        return new StandardProcessingRecipe.Builder<>(PressingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<SandPaperPolishingRecipe> createSandPaperPolishing(String name){
        return new StandardProcessingRecipe.Builder<>(SandPaperPolishingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<SplashingRecipe> createSplashing(String name){
        return new StandardProcessingRecipe.Builder<>(SplashingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<HauntingRecipe> createHaunting(String name){
        return new StandardProcessingRecipe.Builder<>(HauntingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<FillingRecipe> createFilling(String name){
        return new StandardProcessingRecipe.Builder<>(FillingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected StandardProcessingRecipe.Builder<EmptyingRecipe> createEmptying(String name){
        return new StandardProcessingRecipe.Builder<>(EmptyingRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ItemApplicationRecipe.Builder<DeployerApplicationRecipe> createDeploying(String name){
        return new ItemApplicationRecipe.Builder<>(DeployerApplicationRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected ItemApplicationRecipe.Builder<ManualApplicationRecipe> createItemApplication(String name){
        return new ItemApplicationRecipe.Builder<>(ManualApplicationRecipe::new, resourceLocationSupplier.get().withPath(name));
    }
    protected SequencedAssemblyRecipeBuilder createSequencedAssembly(String name){
        return new SequencedAssemblyRecipeBuilder(resourceLocationSupplier.get().withPath(name));
    }

}

