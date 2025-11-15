package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.Mechanicals;
import dev.latvian.mods.kubejs.recipe.RecipeScriptContext;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponentType;
import dev.latvian.mods.rhino.NativeArray;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.createmod.catnip.data.Couple;
import net.minecraft.advancements.critereon.BlockPredicate;


public record CoupleBlockPredicateComponent() implements RecipeComponent<Couple<BlockPredicate>>  {
    public static final RecipeComponentType<Couple<BlockPredicate>> COUPLE_BLOCK_PREDICATE = RecipeComponentType.unit(Mechanicals.asResource("couple_block_predicate"),new CoupleBlockPredicateComponent());

    @Override
    public RecipeComponentType<?> type() {
        return COUPLE_BLOCK_PREDICATE;
    }

    @Override
    public Codec<Couple<BlockPredicate>> codec() {
        return Couple.codec(BlockPredicate.CODEC);
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(Couple.class);
    }


    @Override
    public Couple<BlockPredicate> wrap(RecipeScriptContext cx, Object from) {
        if(from instanceof NativeArray nativeArray){
            return Couple.create(
                    (BlockPredicate) nativeArray.getFirst(),
                    (BlockPredicate) nativeArray.get(1)
            );
        }

        return Couple.create(BlockPredicate.Builder.block().build(),BlockPredicate.Builder.block().build());
    }

    @Override
    public String toString() {
        return "mechanical_couple_block_predicate";
    }

}
