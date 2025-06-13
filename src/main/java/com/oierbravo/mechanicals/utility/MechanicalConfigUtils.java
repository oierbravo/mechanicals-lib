package com.oierbravo.mechanicals.utility;

import com.oierbravo.mechanicals.Mechanicals;
import com.simibubi.create.foundation.fluid.FluidIngredient;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.FluidTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.fluids.FluidStack;

public class MechanicalConfigUtils {
    public static Ingredient readIngredient(String configValue, TagKey<Item> defaultValueTag){
        Ingredient requiredItemIngredient;
        if(configValue.startsWith("#")){
            ResourceLocation itemTag = ResourceLocation.tryParse(configValue.replace("#",""));
            assert itemTag != null;
            return Ingredient.of(ItemTags.create(itemTag));
        }
        final ResourceLocation desiredIngredient = ResourceLocation.parse(configValue);

        if (BuiltInRegistries.ITEM.containsKey(desiredIngredient)) {
            requiredItemIngredient = Ingredient.of(BuiltInRegistries.ITEM.get(desiredIngredient));
        } else {
            Mechanicals.LOGGER.error("Unknown item '{}' in config, using default '{}' instead", configValue, defaultValueTag);
            requiredItemIngredient = Ingredient.of(defaultValueTag);
        }
        return requiredItemIngredient;
    }
    public static FluidStack readFluidStack(String configValue, int configAmount, String defaultValue){
        FluidStack outputFluid;

        final ResourceLocation desiredFluid = ResourceLocation.parse(configValue);

        if (BuiltInRegistries.FLUID.containsKey(desiredFluid)) {
            outputFluid = new FluidStack(BuiltInRegistries.FLUID.get(desiredFluid),configAmount);
        } else {
            Mechanicals.LOGGER.error("Unknown fluid '{}' in config, using default '{}' instead", configValue, defaultValue);
            outputFluid = new FluidStack(BuiltInRegistries.FLUID.get(ResourceLocation.parse(defaultValue)),configAmount);
        }
        return outputFluid;
    }
    public static FluidIngredient readFluidIngredient(String configValue,int configAmount,FluidIngredient defaultValue){
        FluidIngredient fluidIngredient = FluidIngredient.EMPTY;
        if(configValue.startsWith("#")){
            ResourceLocation fluidTag = ResourceLocation.tryParse(configValue.replace("#",""));
            assert fluidTag != null;
            return FluidIngredient.fromTag(FluidTags.create(fluidTag),configAmount);
        }
        final ResourceLocation desiredFluid = ResourceLocation.parse(configValue);

        if (BuiltInRegistries.FLUID.containsKey(desiredFluid)) {
            return FluidIngredient.fromFluid(BuiltInRegistries.FLUID.get(desiredFluid), configAmount);
        }
        Mechanicals.LOGGER.error("Unknown fluid '{}' in config, using default '{}' instead", configValue, defaultValue.toString());
        return defaultValue;
    }
}
