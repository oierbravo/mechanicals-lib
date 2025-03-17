package com.oierbravo.mechanicals;

import dev.engine_room.flywheel.lib.model.baked.PartialModel;

public class MechanicalPartials {
    public static final PartialModel SHAFT_QUARTER = block("shaft_quarter");
    private static PartialModel block(String path) {
        return PartialModel.of(Mechanicals.asResource("block/" + path));
    }
    public static void init() {
        // init static fields
    }
}
