package com.oierbravo.mechanicals.compat.kubejs.bindings;

import com.oierbravo.mechanicals.foundation.recipe.requirements.*;
import net.minecraft.resources.ResourceLocation;

public class RecipeRequirementBuilder {
    public static MinYRequirement minY(int value){
        return MinYRequirement.of(value);
    }

    public static MaxYRequirement maxY(int value){
        return MaxYRequirement.of(value);
    }
    public static MinSpeedRequirement minSpeed(float value){
        return MinSpeedRequirement.of(value);
    }

    public static MaxSpeedRequirement maxSpeed(float value){
        return MaxSpeedRequirement.of(value);
    }

    public static BiomeRequirement biome(String id){
        return BiomeRequirement.of(ResourceLocation.parse(id));
    }

    public static BiomeTagRequirement biomeTag(String id){
        return BiomeTagRequirement.of(ResourceLocation.parse(id));
    }
}
