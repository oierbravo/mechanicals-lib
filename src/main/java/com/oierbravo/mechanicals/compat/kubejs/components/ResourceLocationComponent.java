package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.minecraft.resources.ResourceLocation;



public record ResourceLocationComponent() implements RecipeComponent<ResourceLocation> {
    public static final RecipeComponentType<ResourceLocation> RESOURCE_LOCATION = RecipeComponentType.unit(Mechanicals.asResource("resource_location"),new ResourceLocationComponent());

    @Override
    public RecipeComponentType<?> type() {
        return RESOURCE_LOCATION;
    }

    @Override
    public Codec<ResourceLocation> codec() {
        return ResourceLocation.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(ResourceLocation.class);
    }

    @Override
    public ResourceLocation wrap(Context cx, KubeRecipe recipe, Object from) {
        if(from instanceof String id)
            return ResourceLocation.parse(id);
        if(from instanceof ResourceLocation resourceLocation)
            return resourceLocation;
        return RecipeComponent.super.wrap(cx, recipe, from);
    }

    @Override
    public String toString() {
        return "mechanical_resource_location";
    }
}
