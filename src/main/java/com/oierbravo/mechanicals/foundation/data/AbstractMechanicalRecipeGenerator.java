package com.oierbravo.mechanicals.foundation.data;

import com.oierbravo.mechanicals.foundation.recipe.AbstractMechanicalRecipeBuilder;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

public abstract class AbstractMechanicalRecipeGenerator<MRB extends AbstractMechanicalRecipeBuilder<?,?,MRB>> extends RecipeProvider {
    String namespace;
    String displayName;
    String recipeTypeId;
    Supplier<MRB> builderSupplier;
    boolean compat;

    public AbstractMechanicalRecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String namespace, String recipeTypeId , Supplier<MRB> builderSupplier,String displayName) {
        this(output, registries, namespace, recipeTypeId, builderSupplier, displayName, false);
    }
    public AbstractMechanicalRecipeGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> registries, String namespace, String recipeTypeId , Supplier<MRB> builderSupplier,String displayName, boolean compat) {
        super(output, registries);
        this.namespace = namespace;
        this.recipeTypeId = recipeTypeId;
        this.displayName = displayName;
        this.builderSupplier = builderSupplier;
        this.compat = compat;
    }


    private Block block(String resourceLocationString){
        return block(ResourceLocation.parse(resourceLocationString));
    }
    private Block block(ResourceLocation resourceLocation){
        return BuiltInRegistries.BLOCK.get(resourceLocation);
    }


    protected MRB create(String id){
        return builderSupplier.get().create().withId(ResourceLocation.fromNamespaceAndPath(namespace, recipeTypeId + "/" + id));
    }

    @Override
    public final String getName() {
        return displayName + " recipes.";
    }
}
