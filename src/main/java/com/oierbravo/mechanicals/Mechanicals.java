package com.oierbravo.mechanicals;

import com.mojang.logging.LogUtils;
import com.oierbravo.mechanicals.register.*;
import com.oierbravo.mechanicals.utility.RegistrateLangBuilder;
import com.tterrag.registrate.Registrate;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@Mod(Mechanicals.MODID)
public class Mechanicals {

    public static final String MODID = "mechanicals";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final NonNullSupplier<Registrate> REGISTRATE = NonNullSupplier.lazy(
            () -> Registrate.create(MODID)
                    .defaultCreativeTab(MechanicalCreativeModeTabs.MAIN_TAB.getKey())
    );


    public Mechanicals(IEventBus modEventBus, ModContainer modContainer) {
        MechanicalsFeatureFlags.init();

        MechanicalCreativeModeTabs.register(modEventBus);

        //MechanicalsBlocks.register();
        if(ModList.get().isLoaded("create"))
            MechanicalsCreateItems.register();

        modEventBus.addListener(this::newRegistries);
        MechanicalRecipeRequirementTypes.register(modEventBus);
        MechanicalIngredientTypes.register(modEventBus);
        modEventBus.addListener(Mechanicals::gatherData);
        //modEventBus.addListener(Mechanicals::addFeaturePacks);

        generateLangEntries();

    }
    private void generateLangEntries(){
        new RegistrateLangBuilder<>(MODID, registrate())
                .add("ui.progress", "Progress: %d%%")
                .addCreativeTab("Mechanicals")
                .add("mechanicals.feature.lemon_stuff.description","Enabled Lemon stuff feature for Mechanicals")
                .add("ui.recipe.requirements.title", "Requirements:")
                .add("ui.recipe_requirement.none.tooltip", "None")
                .addRecipeRequirementTitle("biome", "Biome:")
                .addRecipeRequirementValue("biome", "%s")
                .addRecipeRequirementMissing("biome", "Incorrect biome")
                .addRecipeRequirementTitle("biome_tag", "Biome Tag:")
                .addRecipeRequirementValue("biome_tag", "#%s")
                .addRecipeRequirementTitle("min_y", "Min Y:")
                .addRecipeRequirementValue("min_y", "%s")
                .addRecipeRequirementMissing("min_y", "Y position Too low")
                .addRecipeRequirementTitle("max_y", "Max Y:")
                .addRecipeRequirementValue("max_y", "%s")
                .addRecipeRequirementMissing("max_y", "Y position Too high")
                .addRecipeRequirementTitle("min_speed", "Min Speed:")
                .addRecipeRequirementValue("min_speed", "%s RPM")
                .addRecipeRequirementMissing("min_speed", "Not enough speed")
                .addRecipeRequirementTitle("max_speed", "Max Speed:")
                .addRecipeRequirementMissing("max_speed", "Too fast")
                .addRecipeRequirementValue("max_speed", "%s RPM")
                .addRecipeRequirementMissing("output", "Output full or incompatible")
                .addRecipeRequirementMissing("ingredients", "Missing ingredients");
    }

    public static void gatherData(GatherDataEvent event) {

        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        CompletableFuture<HolderLookup.Provider> lookupProvider = event.getLookupProvider();

        /*DataGenerator.PackGenerator featurePack = generator.getBuiltinDatapack(true, "mechanicals", "lemon_stuff");
        featurePack.addProvider(packOutput -> PackMetadataGenerator.forFeaturePack(
                packOutput,
                LibLang.translate("mechanicals.feature.lemon_stuff.description").component(),
                FeatureFlagSet.of(MechanicalsFeatureFlags.FEATURE_LEMON_STUFF)
        ));*/

        if (event.includeServer()) {

        }
        //generator.addProvider(event.includeServer(), new MechanicalsWorldGenProvider(output, lookupProvider));
    }

    public static void addFeaturePacks(final AddPackFindersEvent event) {
        event.addPackFinders(
                ResourceLocation.fromNamespaceAndPath("mechanicals", "data/mechanicals/datapacks/lemon_stuff"),
                PackType.SERVER_DATA,
                Component.literal("Mechanicals: Lemon stuff"),
                PackSource.FEATURE,
                false,
                Pack.Position.TOP
        );
    }
    public static Registrate registrate() {
        return REGISTRATE.get();
    }


    private void newRegistries(NewRegistryEvent event) {
        MechanicalRegistries.register(event);
    }

    public static ResourceLocation asResource(String path) {
        return ResourceLocation.fromNamespaceAndPath(MODID, path);
    }
    public static Logger getLogger(){
        return LOGGER;
    }
}
