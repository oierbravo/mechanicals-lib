package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.content.MechanicalsFlammableRotatedPillarBlock;
import com.oierbravo.mechanicals.content.MechanicalsTreeGrower;
import com.oierbravo.mechanicals.utility.MechanicalRegistrate;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.foundation.data.TagGen.axeOnly;

@SuppressWarnings("removal")
public class MechanicalsBlocks {
    public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> LEMON_LOG = Mechanicals.registrate().block("lemon_log", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.BIRCH_LOG)
            .properties(MechanicalRegistrate::lemonFeature)
            .blockstate((blockMechanicalsFlammableRotatedPillarBlockDataGenContext, registrateBlockstateProvider) -> registrateBlockstateProvider.logBlock(blockMechanicalsFlammableRotatedPillarBlockDataGenContext.get()) )
            .transform(axeOnly())
            .tag(BlockTags.LOGS)
            .tag(BlockTags.LOGS_THAT_BURN)
            .simpleItem()
            .register();

    public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> STRIPPED_LEMON_LOG = Mechanicals.registrate().block("stripped_lemon_log", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_LOG)
            .properties(MechanicalRegistrate::lemonFeature)
            .blockstate((blockMechanicalsFlammableRotatedPillarBlockDataGenContext, registrateBlockstateProvider) -> registrateBlockstateProvider.logBlock(blockMechanicalsFlammableRotatedPillarBlockDataGenContext.get()) )
            .transform(axeOnly())
            .tag(BlockTags.LOGS)
            .tag(BlockTags.LOGS_THAT_BURN)
            .transform(axeOnly())
            .register();

    public static final BlockEntry<Block> LEMON_PLANKS = Mechanicals.registrate().block("lemon_planks", Block::new)
            .initialProperties(() -> Blocks.BIRCH_PLANKS)
            .properties(MechanicalRegistrate::lemonFeature)
            .tag(BlockTags.PLANKS)
            .transform(axeOnly())
            //.recipe((c, p) ->
            //        p.planks(DataIngredient.items(MechanicalsBlocks.LEMON_LOG.asItem()),RecipeCategory.DECORATIONS,c.lazy()))
                   /* ShapelessRecipeBuilder.shapeless(
                            RecipeCategory.DECORATIONS,
                            c.get(),
                            3
                    ).requires(MechanicalsBlocks.STRIPPED_LEMON_LOG.asItem()).unlockedBy("has_lemon_log", RegistrateRecipeProvider.has(MechanicalsBlocks.STRIPPED_LEMON_LOG)).save(p, Mechanicals.asResource("crafting/" + c.getName())))*/
            //.recipe((c, p) -> ShapelessRecipeBuilder.shapeless(RecipeCategory.DECORATIONS, c.get(),3).requires(MechanicalsBlocks.LEMON_LOG.asItem()).unlockedBy("has_lemon_log", RegistrateRecipeProvider.has(MechanicalsBlocks.LEMON_LOG)).save(p, Mechanicals.asResource("crafting/" + c.getName())))
            .register();



    public static final BlockEntry<LeavesBlock> LEMON_LEAVES = Mechanicals.registrate()
            .block(Blocks.BIRCH_LEAVES,"lemon_leaves", LeavesBlock::new)
            .properties(MechanicalRegistrate::lemonFeature)
            .blockstate((c, p) -> p.simpleBlockWithItem(c.get(),p.models().withExistingParent("lemon_leaves", ResourceLocation.fromNamespaceAndPath("minecraft","block/leaves")).renderType(ResourceLocation.fromNamespaceAndPath("minecraft","cutout")).texture("all", Mechanicals.asResource("block/lemon_leaves"))))
            .addLayer(() -> RenderType::cutout)
            .tag(BlockTags.LEAVES)
            .simpleItem()
            .register();

    public static final BlockEntry<SaplingBlock> LEMON_SAPLING = Mechanicals.registrate()
            .block(Blocks.BIRCH_SAPLING, "lemon_sapling", (props)-> new SaplingBlock(MechanicalsTreeGrower.LEMON, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING).requiredFeatures(MechanicalsFeatureFlags.FEATURE_LEMON_STUFF)))
            .blockstate((c, p) -> p.simpleBlockWithItem(c.get(),p.models().withExistingParent("lemon_sapling", ResourceLocation.fromNamespaceAndPath("minecraft","block/cross")).renderType(ResourceLocation.fromNamespaceAndPath("minecraft","cutout")).texture("cross", Mechanicals.asResource("block/lemon_sapling"))))
            .addLayer(() -> RenderType::cutout)
            .tag(BlockTags.SAPLINGS)
            .simpleItem()
            .register();





    public static void register() {}

}
