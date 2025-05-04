package com.oierbravo.mechanicals.content;

import com.oierbravo.mechanicals.register.MechanicalsBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.common.ItemAbility;

import javax.annotation.Nullable;

public class MechanicalsFlammableRotatedPillarBlock extends RotatedPillarBlock {
    public MechanicalsFlammableRotatedPillarBlock(Properties properties) {
        super(properties);
    }
    @Override
    public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return true;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
        return 5;
    }

    @Override
    public @Nullable BlockState getToolModifiedState(BlockState state, UseOnContext context, ItemAbility itemAbility, boolean simulate) {
        if(context.getItemInHand().getItem() instanceof AxeItem) {
            if(state.is(MechanicalsBlocks.LEMON_LOG.get())) {
                return MechanicalsBlocks.STRIPPED_LEMON_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }

            /*if(state.is(MechanicalsBlocks.LEMON_WOOD.get())) {
                return MechanicalsBlocks.STRIPPED_LEMON_LOG.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
            }*/
        }

        return super.getToolModifiedState(state, context, itemAbility, simulate);
    }
}
