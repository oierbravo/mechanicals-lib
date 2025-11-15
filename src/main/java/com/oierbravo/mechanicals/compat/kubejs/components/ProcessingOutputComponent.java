package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import dev.latvian.mods.kubejs.plugin.builtin.wrapper.ItemWrapper;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.world.item.ItemStack;

public record ProcessingOutputComponent() implements RecipeComponent<ProcessingOutput> {
    public static final TypeInfo TYPE_INFO = TypeInfo.of(ProcessingOutput.class).or(ItemWrapper.TYPE_INFO);
    public static final RecipeComponentType<ProcessingOutput> PROCESSING_OUTPUT = RecipeComponentType.unit(Mechanicals.asResource("processing_output"), new ProcessingOutputComponent());

    @Override
    public RecipeComponentType<?> type() {
        return PROCESSING_OUTPUT;
    }

    @Override
    public Codec<ProcessingOutput> codec() {
        return ProcessingOutput.CODEC_NEW;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public ProcessingOutput wrap(RecipeScriptContext cx, Object from) {
        if (from instanceof ProcessingOutput o) {
            return o;
        }

        ItemStack itemStack = ItemWrapper.wrap(cx.cx(), from);
        if (itemStack.isEmpty()) {
            throw new IllegalArgumentException("empty processing output: " + from);
        }

        return new ProcessingOutput(itemStack, 1);
    }
}