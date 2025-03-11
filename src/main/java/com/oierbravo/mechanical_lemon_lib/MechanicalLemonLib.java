package com.oierbravo.mechanical_lemon_lib;

import com.mojang.logging.LogUtils;
import com.oierbravo.mechanical_lemon_lib.register.LemonCreativeModeTabs;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRecipeRequirementTypes;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

@Mod(MechanicalLemonLib.MODID)
public class MechanicalLemonLib {

    public static final String MODID = "mechanical_lemon_lib";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MechanicalLemonLib(IEventBus modEventBus, ModContainer modContainer) {

        LemonCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::newRegistries);
        MechanicalLemonRecipeRequirementTypes.init(modEventBus);
    }

    private void newRegistries(NewRegistryEvent event) {
        MechanicalLemonRegistries.register(event);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
    public static Logger getLogger(){
        return LOGGER;
    }
}
