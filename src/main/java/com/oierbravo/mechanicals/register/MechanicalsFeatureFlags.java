package com.oierbravo.mechanicals.register;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.flag.FeatureFlags;

import static com.oierbravo.mechanicals.Mechanicals.MODID;

public class MechanicalsFeatureFlags {
    public static final FeatureFlag FEATURE_LEMON_STUFF = FeatureFlags.REGISTRY.getFlag(ResourceLocation.fromNamespaceAndPath(MODID, "lemon_stuff"));

    public static void init(){}
}
