package com.oierbravo.mechanical_lemon_lib;

import com.mojang.logging.LogUtils;
import com.oierbravo.mechanical_lemon_lib.register.LemonCreativeModeTabs;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(MechanicalLemonLib.MODID)
public class MechanicalLemonLib {

    public static final String MODID = "mechanical_lemon_lib";
    private static final Logger LOGGER = LogUtils.getLogger();

    public MechanicalLemonLib(IEventBus modEventBus, ModContainer modContainer) {

        LemonCreativeModeTabs.register(modEventBus);

    }


}
