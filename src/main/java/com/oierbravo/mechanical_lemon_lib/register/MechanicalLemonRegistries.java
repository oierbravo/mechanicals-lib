package com.oierbravo.mechanical_lemon_lib.register;

import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.MechanicalLemonLib;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirementType;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MechanicalLemonRegistries {
    //public static final Registry<MapCodec<? extends RecipeRequirementType<?>>> RECIPE_REQUIREMENT_TYPES = simple(key("requirements")) ;
    public static final Registry<MapCodec<? extends IRecipeRequirementType>> RECIPE_REQUIREMENT_TYPES = (new RegistryBuilder(Keys.RECIPE_REQUIREMENT_TYPES)).create();

    /*private static <T> Registry<T> simple(ResourceKey<Registry<T>> key) {
        return register(key);
    }*/

    /*private static <T> Registry<T> withIntrusiveHolders(ResourceKey<Registry<T>> key) {
        return register(key, true);
    }*/

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    /*private static <T> Registry<T> register(ResourceKey<Registry<T>> key) {
        RegistryBuilder<T> builder = new RegistryBuilder<>(key)
                .sync(true);


        Registry<T> registry = builder.create();
        ((WritableRegistry) BuiltInRegistries.REGISTRY)
                .register(key, registry, RegistrationInfo.BUILT_IN);
        return registry;
    }
    private static <T> ResourceKey<Registry<T>> key(String name) {
        return ResourceKey.createRegistryKey(MechanicalLemonLib.asResource(name));
    }*/
    public MechanicalLemonRegistries() {
    }

    static {
        RECIPE_REQUIREMENT_TYPES = (new RegistryBuilder(MechanicalLemonRegistries.Keys.RECIPE_REQUIREMENT_TYPES)).create();
    }
    public static final class Keys {
        public static final ResourceKey<Registry<MapCodec<? extends RecipeRequirementType<?>>>> RECIPE_REQUIREMENT_TYPES = key("requirements");

        public Keys() {
        }

        private static <T> ResourceKey<Registry<T>> key(String name) {
            return ResourceKey.createRegistryKey(MechanicalLemonLib.asResource(name));
        }
    }
    public static void init() {
    }

}
