package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MechanicalRegistries {
    public static Registry<RecipeRequirementType<?>> RECIPE_REQUIREMENT_TYPE;// = (new RegistryBuilder(Keys.RECIPE_REQUIREMENT_TYPES)).create();

    public MechanicalRegistries() {
    }

    static {
        RECIPE_REQUIREMENT_TYPE = new RegistryBuilder<>(MechanicalRegistries.Keys.RECIPE_REQUIREMENT).sync(true).create();
    }
    public static final class Keys {
        public static final ResourceKey<Registry<RecipeRequirementType<?>>> RECIPE_REQUIREMENT = ResourceKey.createRegistryKey(Mechanicals.asResource("requirement"));

    }
    public static void register(NewRegistryEvent event) {
        event.register(RECIPE_REQUIREMENT_TYPE);
    }

}
