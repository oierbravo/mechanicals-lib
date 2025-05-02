package com.oierbravo.mechanicals.foundation.recipe;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IProcessBlockingRequirement {
    boolean test(Level pLevel, BlockEntity pBlockEntity);
}
