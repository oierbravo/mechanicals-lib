package com.oierbravo.mechanicals.compat.kubejs.bindings;

import com.oierbravo.mechanicals.foundation.util.BlockPredicateUtils;
import net.minecraft.advancements.critereon.BlockPredicate;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;

public class BlockPredicateBuilder {
    public static BlockPredicate of(String id){
        return BlockPredicateUtils.Builder.build(id);
    }
    public static BlockPredicate of(String id, StatePropertiesPredicate stateProperties){
        return BlockPredicateUtils.Builder.build(id);
    }
    //StatePropertiesPredicate.Builder StatePropertiesPredicate
    public static BlockPredicate.Builder from(String id){
        return BlockPredicateUtils.Builder.from(id);
    }
}
