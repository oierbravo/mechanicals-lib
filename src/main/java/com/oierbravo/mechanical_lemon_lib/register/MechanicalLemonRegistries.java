package com.oierbravo.mechanical_lemon_lib.register;

import com.oierbravo.mechanical_lemon_lib.MechanicalLemonLib;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegistryBuilder;

public class MechanicalLemonRegistries {
    public static Registry<RecipeRequirementType<?>> RECIPE_REQUIREMENT_TYPE;// = (new RegistryBuilder(Keys.RECIPE_REQUIREMENT_TYPES)).create();
    public MechanicalLemonRegistries() {
    }

    static {
        RECIPE_REQUIREMENT_TYPE = new RegistryBuilder<>(MechanicalLemonRegistries.Keys.RECIPE_REQUIREMENT).sync(true).create();
    }
    public static final class Keys {
        public static final ResourceKey<Registry<RecipeRequirementType<?>>> RECIPE_REQUIREMENT = ResourceKey.createRegistryKey(MechanicalLemonLib.asResource("requirement"));

    }
    public static void register(NewRegistryEvent event) {
        event.register(RECIPE_REQUIREMENT_TYPE);
    }

}
