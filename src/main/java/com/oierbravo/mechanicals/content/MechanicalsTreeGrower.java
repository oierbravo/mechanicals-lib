package com.oierbravo.mechanicals.content;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.register.MechanicalsConfiguredFeatures;
import net.minecraft.world.level.block.grower.TreeGrower;

import java.util.Optional;

public class MechanicalsTreeGrower {
    public static final TreeGrower LEMON = new TreeGrower(Mechanicals.MODID + ":lemon",
            Optional.empty(), Optional.of(MechanicalsConfiguredFeatures.LEMON_KEY), Optional.empty());
}
