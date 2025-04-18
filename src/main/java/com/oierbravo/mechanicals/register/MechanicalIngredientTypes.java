package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.foundation.ingredient.CountableIngredient;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.crafting.IngredientType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

import static com.oierbravo.mechanicals.Mechanicals.MODID;

public class MechanicalIngredientTypes {
    private static final DeferredRegister<IngredientType<?>> INGREDIENT_TYPES = DeferredRegister.create(NeoForgeRegistries.INGREDIENT_TYPES, MODID);

    /*public static final Supplier<IngredientType<CountableIngredient>> COUNTABLE_INGREDIENT =
            INGREDIENT_TYPES.register("countable_ingredient",
                    () -> new IngredientType<>(CountableIngredient.CODEC, CountableIngredient.STREAM_CODEC));
*/
    public static void register(IEventBus modEventBus) {
        INGREDIENT_TYPES.register(modEventBus);
    }

}
