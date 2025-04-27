package com.oierbravo.mechanicals;

import net.neoforged.api.distmarker.Dist;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(value = Mechanicals.MODID, dist = Dist.CLIENT)
public class MechanicalsClient {
    public MechanicalsClient(net.neoforged.bus.api.IEventBus modEventBus) {
        onCtorClient(modEventBus);
    }
    public static void onCtorClient(net.neoforged.bus.api.IEventBus modEventBus) {
        modEventBus.addListener(MechanicalsClient::clientInit);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        MechanicalPartials.init();
    }
}
