package com.oierbravo.mechanicals;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

public class MechanicalsClient {
    public MechanicalsClient(IEventBus modEventBus) {
        onCtorClient(modEventBus);
    }
    public static void onCtorClient(IEventBus modEventBus) {
        modEventBus.addListener(MechanicalsClient::clientInit);
    }
    public static void clientInit(final FMLClientSetupEvent event) {
        MechanicalPartials.init();
    }
}
