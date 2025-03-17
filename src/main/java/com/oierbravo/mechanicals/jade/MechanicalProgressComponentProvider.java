package com.oierbravo.mechanicals.jade;

import com.oierbravo.mechanicals.utility.LibLang;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.api.ui.ProgressStyle;

public class MechanicalProgressComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private final ResourceLocation UID;

    public MechanicalProgressComponentProvider(ResourceLocation UID){
        this.UID = UID;
    }

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("progress")) {
            int progress = accessor.getServerData().getInt("progress");

            if(progress > 0){
                IElementHelper helper = IElementHelper.get();
                ProgressStyle progressStyle = helper.progressStyle().textColor(1);
                tooltip.add(helper.progress((float)progress / 100, LibLang.translate("progress", progress).component().withColor(java.awt.Color.GRAY.getRGB()), progressStyle.color(java.awt.Color.YELLOW.getRGB()), BoxStyle.getTransparent(), false));

            }
        }

    }

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendServerData(CompoundTag compoundTag, BlockAccessor blockAccessor) {
        if(blockAccessor.getBlockEntity() instanceof IHavePercent){
            compoundTag.putInt("progress",((IHavePercent) blockAccessor.getBlockEntity()).getProgressPercent());
        }
    }

}
