package com.oierbravo.mechanicals.utility;

import net.neoforged.fml.ModList;

public enum MechanicalMods {

    CFN("create_from_nothing"),
    SIFTER("createsifter"),
    JET_BOOTS("create_jet_boots"),
    CHICKEN("create_mechanical_chicken"),
    EXTRUDER("create_mechanical_extruder"),
    SPAWNER("create_mechanical_spawner"),
    TELEPORTER("create_mechanical_teleporter"),
    COW("mechanical_cow"),
    MEXD("mechanicalexdeorum"),
    MELTER("melter"),
    WATERCODENSER("watercondenser")
    ;

    private final String id;

    private MechanicalMods(String id) {
        this.id = id;
    }
    public boolean isLoaded(){
        return ModList.get().isLoaded(id);
    }

}
