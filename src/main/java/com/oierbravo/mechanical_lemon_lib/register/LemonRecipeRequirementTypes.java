package com.oierbravo.mechanical_lemon_lib.register;

import com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements.SpeedRequirement;

public class LemonRecipeRequirementTypes {

    public static void register() {
        IRecipeRequirement.REGISTRY.register(SpeedRequirement.TYPE,SpeedRequirement.);

    }
}

