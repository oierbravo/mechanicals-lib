package com.oierbravo.mechanicals.foundation.recipe;

import com.mojang.serialization.Codec;
import com.oierbravo.mechanicals.register.MechanicalRegistries;
import com.oierbravo.mechanicals.utility.LibLang;
import net.createmod.catnip.data.Pair;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;

public interface IRecipeRequirement {
    Codec<IRecipeRequirement> CODEC = MechanicalRegistries.RECIPE_REQUIREMENT_TYPE.byNameCodec().dispatch(IRecipeRequirement::getType, RecipeRequirementType::codec);
    Codec<List<IRecipeRequirement>> LIST_CODEC = CODEC.listOf();

    StreamCodec<RegistryFriendlyByteBuf,IRecipeRequirement> STREAM_CODEC
            = ByteBufCodecs.registry(MechanicalRegistries.Keys.RECIPE_REQUIREMENT)
            .dispatch(IRecipeRequirement::getType, RecipeRequirementType::streamCodec);

    StreamCodec<RegistryFriendlyByteBuf, List<IRecipeRequirement>> LIST_STREAM_CODEC =
        STREAM_CODEC.apply(ByteBufCodecs.list(256));

    boolean test(Level pLevel, BlockEntity pBlockEntity);

    String getIdString();
    String toString();
    RecipeRequirementType<?> getType();

    default Pair<Component,Component> toTooltipComponent(){
        return Pair.of(LibLang.translate("ui.recipe_requirement." + getIdString() + ".tooltip.title").component(),
                        LibLang.translate("ui.recipe_requirement." + getIdString() + ".tooltip.value", toString()).component()
        );
    };
    default Component toMissingComponent(){
        return LibLang.translate("ui.recipe_requirement." + getIdString() + ".missing").component();
    }

}
