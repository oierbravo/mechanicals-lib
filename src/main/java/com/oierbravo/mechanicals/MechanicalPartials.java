package com.oierbravo.mechanicals;

import com.oierbravo.mechanicals.register.MechanicalsCreateItems;
import com.simibubi.create.AllPartialModels;
import com.simibubi.create.Create;
import dev.engine_room.flywheel.lib.model.baked.PartialModel;
import net.minecraft.resources.ResourceLocation;

public class MechanicalPartials {
    public static final PartialModel SHAFT_QUARTER = block("shaft_quarter");
    private static PartialModel block(String path) {
        return PartialModel.of(Mechanicals.asResource("block/" + path));
    }

    public static void init() {
        registerRarePackageModel(MechanicalsCreateItems.RARE_OIERBRAVO_PACKAGE.getId(), 12, 10);
    }
    /* From DragonPlus library  LGPL3 License*/
    public static void registerRarePackageModel(ResourceLocation id, int width, int height) {
        AllPartialModels.PACKAGES.put(id, PartialModel.of(id.withPrefix("item/")));
        AllPartialModels.PACKAGE_RIGGING.put(id, PartialModel.of(Create.asResource("item/package/rigging_" + width + "x" + height)));
    }
}
