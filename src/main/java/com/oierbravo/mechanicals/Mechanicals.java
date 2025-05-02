package com.oierbravo.mechanicals;

import com.mojang.logging.LogUtils;
import com.oierbravo.mechanicals.register.LemonCreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.slf4j.Logger;

@Mod(Mechanicals.MODID)
public class Mechanicals {

    public static final String MODID = "mechanical_lemon_lib";
    private static final Logger LOGGER = LogUtils.getLogger();

    public Mechanicals() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        MinecraftForge.EVENT_BUS.register(this);
        LemonCreativeModeTabs.register(modEventBus);

    }

}
