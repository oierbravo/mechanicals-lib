package com.oierbravo.mechanicals;

import com.mojang.logging.LogUtils;
import com.oierbravo.mechanicals.register.MechanicalCreativeModeTabs;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import com.oierbravo.mechanicals.register.MechanicalRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.registries.*;
import org.slf4j.Logger;

@Mod(Mechanicals.MODID)
public class Mechanicals {

    public static final String MODID = "mechanicals";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Mechanicals(IEventBus modEventBus, ModContainer modContainer) {

        MechanicalCreativeModeTabs.register(modEventBus);

        modEventBus.addListener(this::newRegistries);
        MechanicalRecipeRequirementTypes.init(modEventBus);
        modEventBus.addListener(this::doClientStuff);

    }
    private void doClientStuff(final FMLClientSetupEvent event) {
        MechanicalPartials.init();
    }

    private void newRegistries(NewRegistryEvent event) {
        MechanicalRegistries.register(event);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
    public static Logger getLogger(){
        return LOGGER;
    }
}
