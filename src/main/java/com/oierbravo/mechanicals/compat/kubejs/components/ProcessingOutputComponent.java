package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.item.ItemStackJS;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.script.KubeJSContext;
import dev.latvian.mods.kubejs.util.RegistryAccessContainer;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;

public record ProcessingOutputComponent() implements RecipeComponent<ProcessingOutput> {
    public static final RecipeComponent<ProcessingOutput> OUTPUT = new ProcessingOutputComponent();

    @Override
    public Codec<ProcessingOutput> codec() {
        return ProcessingOutput.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(ProcessingOutput.class).or(ItemStackJS.TYPE_INFO);
    }

    @Override
    public ProcessingOutput wrap(Context cx, KubeRecipe recipe, Object from) {
        if (from instanceof ProcessingOutput o) {
            return o;
        }

        RegistryAccessContainer registryAccess = ((KubeJSContext) cx).getRegistries();

        ItemStack itemStack = ItemStackJS.wrap(registryAccess, from);
        if (itemStack.isEmpty()) {
            throw new IllegalArgumentException("empty processing output: " + from);
        }

        return new ProcessingOutput(itemStack,1);
    }
}