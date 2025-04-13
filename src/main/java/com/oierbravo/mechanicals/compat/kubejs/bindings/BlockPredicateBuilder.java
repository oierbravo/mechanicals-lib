package com.oierbravo.mechanicals.compat.kubejs.bindings;

import com.oierbravo.mechanicals.foundation.util.BlockPredicateUtils;
import net.minecraft.advancements.critereon.BlockPredicate;

public class BlockPredicateBuilder {
    public static BlockPredicate of(String id){
        return BlockPredicateUtils.Builder.build(id);
    }
}
