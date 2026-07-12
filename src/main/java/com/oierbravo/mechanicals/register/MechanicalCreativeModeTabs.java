package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.utility.LibLang;
import com.oierbravo.mechanicals.utility.LangIdGenerator;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MechanicalCreativeModeTabs {

    private static final DeferredRegister<CreativeModeTab> TAB_REGISTER =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Mechanicals.MODID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> MAIN_TAB = TAB_REGISTER.register("main",
            () -> CreativeModeTab.builder()
                    .title(LibLang.translate(LangIdGenerator.creativeTab("main")).component())
                    .icon(MechanicalCreativeModeTabs::tabIcon)
                    .build());

    private static ItemStack tabIcon() {
        if (ModList.get().isLoaded("create"))
            return MechanicalsCreateItems.RARE_OIERBRAVO_PACKAGE.asStack();
        return new ItemStack(Items.APPLE);
    }

    public static CreativeModeTab getBaseTab() {
        return MAIN_TAB.get();
    }

    public static void register(IEventBus modEventBus) {
        TAB_REGISTER.register(modEventBus);
    }
}
