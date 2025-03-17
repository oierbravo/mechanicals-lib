package com.oierbravo.mechanicals.foundation.data;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.conditions.ICondition;

public record FluidExistsCondition(String fluid) implements ICondition {
    public static MapCodec<FluidExistsCondition> CODEC = RecordCodecBuilder.mapCodec((builder) -> builder.group(Codec.STRING.fieldOf("fluid_exists").forGetter(FluidExistsCondition::fluid)).apply(builder, FluidExistsCondition::new));

    public FluidExistsCondition(String fluid) {
        this.fluid = fluid;
    }

    public String toString() {
        return "fluid_exists(\"" + this.fluid + "\")";
    }

    public boolean test(IContext context) {
        ResourceLocation fluidLocation = ResourceLocation.tryParse(fluid);
        if(fluidLocation == null)
            return false;
        if(fluidLocation.getNamespace().startsWith("#"))
            return true; //Tags always true;
        return BuiltInRegistries.FLUID.containsKey(fluidLocation);
    }

    @Override
    public MapCodec<? extends ICondition> codec() {
        return CODEC;
    }


}
