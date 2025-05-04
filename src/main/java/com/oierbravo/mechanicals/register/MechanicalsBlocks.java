package com.oierbravo.mechanicals.register;

import com.oierbravo.mechanicals.Mechanicals;
import com.oierbravo.mechanicals.content.MechanicalsFlammableRotatedPillarBlock;
import com.oierbravo.mechanicals.content.MechanicalsTreeGrower;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.simibubi.create.foundation.data.TagGen.axeOnly;

public class MechanicalsBlocks {
    public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> LEMON_LOG = Mechanicals.registrate().block("lemon_log", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.BIRCH_LOG)
            .transform(axeOnly())
            .simpleItem()
            .register();

    /*public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> LEMON_WOOD = Mechanicals.registrate().block("lemon_wood", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.BIRCH_WOOD)
            .transform(axeOnly())
            .simpleItem()
            .register();*/

    public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> STRIPPED_LEMON_LOG = Mechanicals.registrate().block("stripped_lemon_log", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_LOG)
            .transform(axeOnly())
            .register();

    /*public static final BlockEntry<MechanicalsFlammableRotatedPillarBlock> STRIPPED_LEMON_WOOD = Mechanicals.registrate().block("stripped_lemon_wood", MechanicalsFlammableRotatedPillarBlock::new)
            .initialProperties(() -> Blocks.STRIPPED_BIRCH_WOOD)
            .transform(axeOnly())
            .register();*/

    public static final BlockEntry<Block> LEMON_PLANKS = Mechanicals.registrate().block("lemon_planks", Block::new)
            .initialProperties(() -> Blocks.BIRCH_PLANKS)
            .transform(axeOnly())
            .register();


    public static final BlockEntry<LeavesBlock> LEMON_LEAVES = Mechanicals.registrate().block(Blocks.BIRCH_LEAVES,"lemon_leaves", LeavesBlock::new).simpleItem().register();

    public static final BlockEntry<SaplingBlock> LEMON_SAPLING = Mechanicals.registrate()
            .block(Blocks.BIRCH_SAPLING, "lemon_sapling", (props)-> new SaplingBlock(MechanicalsTreeGrower.LEMON, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SAPLING)))
            .transform(saplingBlockBlockBlockBuilder -> )
            .item()
            .build()
            .register();





    public static void register() {}

}
