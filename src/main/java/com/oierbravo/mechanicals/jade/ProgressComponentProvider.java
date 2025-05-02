package com.oierbravo.mechanicals.jade;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.IServerDataProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;
import snownee.jade.api.ui.BoxStyle;
import snownee.jade.api.ui.IElementHelper;
import snownee.jade.util.Color;

public class ProgressComponentProvider implements IBlockComponentProvider, IServerDataProvider<BlockAccessor> {
    private final ResourceLocation UID;
    private final String translationKey;
    String blockEntityID;

    public ProgressComponentProvider(ResourceLocation UID, String id, String translationKey){
        this.UID = UID;
        this.blockEntityID = id;
        this.translationKey = translationKey;
    }

    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        if (accessor.getServerData().contains("progress")) {
            int progress = accessor.getServerData().getInt("progress");

            if(progress > 0){
                IElementHelper helper = tooltip.getElementHelper();
                tooltip.add(helper.progress((float)progress / 100, Component.translatable("mechanical_lemon_lib.progress",progress),helper.progressStyle().color(Color.hex("#FFFF00").toInt()), BoxStyle.DEFAULT,true));
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
