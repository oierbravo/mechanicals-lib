package com.oierbravo.mechanicals.compat.kubejs.components;

import com.mojang.serialization.Codec;
import dev.latvian.mods.kubejs.recipe.KubeRecipe;
import dev.latvian.mods.kubejs.recipe.component.RecipeComponent;
import dev.latvian.mods.rhino.Context;
import dev.latvian.mods.rhino.NativeArray;
import dev.latvian.mods.rhino.type.TypeInfo;
import net.createmod.catnip.data.Couple;

public class CoupleBooleanComponent  implements RecipeComponent<Couple<Boolean>> {
    public static final RecipeComponent<Couple<Boolean>> BOOLEAN = new CoupleBooleanComponent();

    @Override
    public Codec<Couple<Boolean>> codec() {
        return Couple.codec(Codec.BOOL);
    }

    @Override
    public TypeInfo typeInfo() {
        return TypeInfo.of(Couple.class);
    }

    @Override
    public Couple<Boolean> wrap(Context cx, KubeRecipe recipe, Object from) {
        if(from instanceof Boolean bool)
            return Couple.create(bool,bool);
        if(from instanceof NativeArray array){
            if(array.size() == 1){
                return Couple.create((boolean) array.getFirst(), (boolean) array.getFirst());
            }
            return Couple.create((boolean) array.getFirst(), (boolean) array.get(1));
        }

        return Couple.create(false,false);
    }
}
