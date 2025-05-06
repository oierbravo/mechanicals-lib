package com.oierbravo.mechanicals.utility;

import com.oierbravo.mechanicals.register.MechanicalsFeatureFlags;
import com.simibubi.create.api.stress.BlockStressValues;
import com.tterrag.registrate.util.nullness.NonNullConsumer;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import org.jetbrains.annotations.NotNull;

public class MechanicalRegistrate {
    public static <B extends Block> @NotNull NonNullConsumer<B> setImpact(double value) {
        return (block) -> {
            BlockStressValues.IMPACTS.register(block, () -> value);
        };
    }

    public static BlockBehaviour.Properties lemonFeature(BlockBehaviour.Properties properties) {
        return properties.requiredFeatures(MechanicalsFeatureFlags.FEATURE_LEMON_STUFF);
    }
}
