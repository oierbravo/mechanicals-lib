package com.oierbravo.mechanical_lemon_lib.register;

import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.MechanicalLemonLib;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirement;
import com.simibubi.create.Create;
import net.minecraft.core.RegistrationInfo;
import net.minecraft.core.Registry;
import net.minecraft.core.WritableRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.conditions.ICondition;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MechanicalLemonRegistries {
    public static final Registry<MapCodec<? extends RecipeRequirement>> RECIPE_REQUIREMENT_SERIALIZERS = simple(key("recipe_requirement_serializer")) ;

    private static <T> Registry<T> simple(ResourceKey<Registry<T>> key) {
        return register(key, false);
    }

    private static <T> Registry<T> withIntrusiveHolders(ResourceKey<Registry<T>> key) {
        return register(key, true);
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    private static <T> Registry<T> register(ResourceKey<Registry<T>> key, boolean hasIntrusiveHolders) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(key)
                .sync(true);

        if (hasIntrusiveHolders)
            builder.withIntrusiveHolders();

        Registry<T> registry = builder.create();
        ((WritableRegistry) BuiltInRegistries.REGISTRY)
                .register(key, registry, RegistrationInfo.BUILT_IN);
        return registry;
    }
    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(MechanicalLemonLib.asResource(name));
    }
    public static void init() {
        // make sure the class is loaded.
        // this method is called at the tail of BuiltInRegistries, injected by BuiltInRegistriesMixin.
    }

}
