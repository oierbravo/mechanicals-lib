package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.logistics.box.PackageItem;
import com.simibubi.create.content.logistics.box.PackageStyles;
import com.tterrag.registrate.util.entry.ItemEntry;

public class MechanicalsCreateItems {
    public static final ItemEntry<PackageItem> RARE_OIERBRAVO_PACKAGE = Mechanicals.registrate()
            .item("rare_oierbravo_package", prop -> new PackageItem(prop,
                    new PackageStyles.PackageStyle("rare_oierbravo", 12, 10, 21, true)))
            .properties(prop -> prop.stacksTo(1))
            .tag(AllTags.AllItemTags.PACKAGES.tag)
            .model((ctx, prov) -> prov
                    .withExistingParent(ctx.getName(), Mechanicals.asResource("item/package/custom_12x10"))
                    .texture("2", prov.modLoc("item/package/rare_oierbravo")))
            .register();

    public static void register() {}

}
