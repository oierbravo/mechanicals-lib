package com.oierbravo.mechanicals.compat.kubejs.bindings;

import com.simibubi.create.content.processing.recipe.ProcessingOutput;
import net.minecraft.world.item.ItemStack;

public class ProcessingOutputBuilder {
    public static ProcessingOutput of(ItemStack itemStack){
        return of(itemStack, 1);
    }

    public static ProcessingOutput of(ItemStack itemStack, float chance){
        return new ProcessingOutput(itemStack, chance);
    }
}
