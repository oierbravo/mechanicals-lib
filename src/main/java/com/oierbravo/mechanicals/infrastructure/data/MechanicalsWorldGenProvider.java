package com.oierbravo.mechanicals.infrastructure.data;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.register.MechanicalsBiomeModifiers;
import com.oierbravo.mechanicals.register.MechanicalsConfiguredFeatures;
import com.oierbravo.mechanicals.register.MechanicalsPlacedFeatures;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.DatapackBuiltinEntriesProvider;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class MechanicalsWorldGenProvider extends DatapackBuiltinEntriesProvider {
    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(Registries.CONFIGURED_FEATURE, MechanicalsConfiguredFeatures::bootstrap)
            .add(Registries.PLACED_FEATURE, MechanicalsPlacedFeatures::bootstrap)
            .add(NeoForgeRegistries.Keys.BIOME_MODIFIERS, MechanicalsBiomeModifiers::bootstrap);

    public MechanicalsWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, BUILDER, Set.of(Mechanicals.MODID));

    }
}
