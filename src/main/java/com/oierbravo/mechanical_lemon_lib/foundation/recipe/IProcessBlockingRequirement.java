package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public interface IProcessBlockingRequirement {
    boolean test(Level pLevel, BlockEntity pBlockEntity);
}
