package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.utility.LibLang;
import com.oierbravo.mechanicals.utility.MechanicalLangIdGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MechanicalCreativeModeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mechanicals.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(LibLang.translate(MechanicalLangIdGenerator.creativeTabId("main")).component())
                    .icon(MechanicalsCreateItems.RARE_OIERBRAVO_PACKAGE::asStack)
                    .build());

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.get();
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
