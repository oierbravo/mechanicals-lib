package com.oierbravo.mechanicals.utility;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;

import java.util.Arrays;

public class MechanicalItemStackUtils {
    public static ResourceLocation getResorceLocation(ItemStack itemStack) {
        return getResorceLocation(itemStack.getItem());
    }
    public static ResourceLocation getResorceLocation(Ingredient ingredient) {
        return getResorceLocation(Arrays.stream(ingredient.getItems()).findFirst().get());
    }

    public static ResourceLocation getResorceLocation(Item item) {
        return BuiltInRegistries.ITEM.getKey(item);
    }
}
