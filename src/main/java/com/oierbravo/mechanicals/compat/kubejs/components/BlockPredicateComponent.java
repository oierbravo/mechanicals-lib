package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.foundation.util.BlockPredicateUtils;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.ListRecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.NativeArray;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.createmod.catnip.data.Couple;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public record BlockPredicateComponent() implements RecipeComponent<BlockPredicate> {
    public static final TypeInfo TYPE_INFO = TypeInfo.of(BlockPredicate.class);
    public static final RecipeComponentType<BlockPredicate> BLOCK_PREDICATE = RecipeComponentType.unit(Mechanicals.asResource("block_predicate"), new BlockPredicateComponent());

    @Override
    public RecipeComponentType<?> type() {
        return BLOCK_PREDICATE;
    }

    @Override
    public Codec<BlockPredicate> codec() {
        return BlockPredicate.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TYPE_INFO;
    }

    @Override
    public ListRecipeComponent<BlockPredicate> asList() {
        return RecipeComponent.super.asList();
    }

    @Override
    public BlockPredicate wrap(RecipeScriptContext cx, Object from) {
        if(from instanceof String id)
            return BlockPredicateUtils.Builder.build(id);
        if(from instanceof BlockPredicate blockPredicate)
            return blockPredicate;
        return RecipeComponent.super.wrap(cx, from);
    }
}
