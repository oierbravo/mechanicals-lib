package com.oierbravo.mechanical_lemon_lib.foundation.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRegistries;
import com.oierbravo.mechanical_lemon_lib.utility.LibLang;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

public interface IRecipeRequirement {
    Codec<IRecipeRequirement> CODEC = MechanicalLemonRegistries.RECIPE_REQUIREMENT_TYPE.byNameCodec().dispatch(IRecipeRequirement::getType, RecipeRequirementType::codec);
    Codec<List<IRecipeRequirement>> LIST_CODEC = CODEC.listOf();

    StreamCodec<RegistryFriendlyByteBuf,IRecipeRequirement> STREAM_CODEC
            = ByteBufCodecs.registry(MechanicalLemonRegistries.Keys.RECIPE_REQUIREMENT)
            .dispatch(IRecipeRequirement::getType, RecipeRequirementType::streamCodec);

    StreamCodec<RegistryFriendlyByteBuf, List<IRecipeRequirement>> LIST_STREAM_CODEC =
        STREAM_CODEC.apply(ByteBufCodecs.list(256));

    boolean test(Level pLevel, BlockEntity pBlockEntity);
    /*boolean isPresent();*/
    String getIdString();
    String toString();
    RecipeRequirementType<?> getType();

    default Component toTooltipComponent(){
        return LibLang.translate("ui.recipe_requirement." + getIdString() + ".tooltip", toString()).component();
    };
    default Component toMissingComponent(){
        return LibLang.translate("ui.recipe_requirement." + getIdString() + ".missing").component();
    }

}
