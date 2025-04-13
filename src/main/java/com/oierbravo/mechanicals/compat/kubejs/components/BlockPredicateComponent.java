package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.foundation.util.BlockPredicateUtils;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.NativeArray;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.createmod.catnip.data.Couple;
import net.minecraft.advancements.critereon.BlockPredicate;

import java.util.List;

public record BlockPredicateComponent() implements RecipeComponent<BlockPredicate> {
    public static final RecipeComponent<BlockPredicate> BLOCK_PREDICATE = new BlockPredicateComponent();

    @Override
    public Codec<BlockPredicate> codec() {
        return BlockPredicate.CODEC;
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(BlockPredicate.class);
    }

    @Override
    public RecipeComponent<List<BlockPredicate>> asList() {
        return RecipeComponent.super.asList();
    }

    @Override
    public BlockPredicate wrap(Context cx, KubeRecipe recipe, Object from) {
        if(from instanceof String id)
            return BlockPredicateUtils.Builder.build(id);
        if(from instanceof BlockPredicate blockPredicate)
            return blockPredicate;
        return RecipeComponent.super.wrap(cx, recipe, from);
    }

    public static final RecipeComponent<Couple<BlockPredicate>> BLOCK_PREDICATE_COUPLE = new RecipeComponent<>() {
        private static final TypeInfo WRAP_TYPE = TypeInfo.OBJECT_ARRAY;
        //private static final TypeInfo WRAP_TYPE = TypeInfo.of(Couple.class).withParams(TypeInfo.of(BlockPredicate.class));




        @Override
        public Codec<Couple<BlockPredicate>> codec() {
            return Couple.codec(BlockPredicate.CODEC);
        }

        @Override
        public TypeInfo typeInfo() {
            return WRAP_TYPE;
        }

        @Override
        public Couple<BlockPredicate> wrap(Context cx, KubeRecipe recipe, Object from) {
            if(from instanceof NativeArray nativeArray){
                return Couple.create((BlockPredicate) nativeArray.getFirst(),(BlockPredicate)  nativeArray.get(1));
            }

            return Couple.create(BlockPredicate.Builder.block().build(),BlockPredicate.Builder.block().build());
        }

        @Override
        public String toString() {
            return "couple_block_predicate";
        }
    };

}
