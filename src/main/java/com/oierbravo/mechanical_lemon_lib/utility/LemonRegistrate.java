package com.oierbravo.mechanical_lemon_lib.utility;

import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class LemonRegistrate {
    public static <B extends Block> @NotNull NonNullConsumer<B> setImpact(double value) {
        return (block) -> {
            BlockStressValues.IMPACTS.register(block, () -> value);
        };
    }
}
