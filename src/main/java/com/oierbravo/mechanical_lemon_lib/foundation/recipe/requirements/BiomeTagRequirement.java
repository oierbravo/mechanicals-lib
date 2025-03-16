package com.oierbravo.mechanical_lemon_lib.foundation.recipe.requirements;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanical_lemon_lib.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanical_lemon_lib.register.MechanicalLemonRecipeRequirementTypes;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.Optional;

public record BiomeTagRequirement(TagKey<Biome> tag) implements IRecipeRequirement {
    public static String ID = "biome_tag";

    public BiomeTagRequirement(ResourceLocation resourceLocation){
        this(TagKey.create(Registries.BIOME,resourceLocation));
    }
    public static MapCodec<BiomeTagRequirement> CODEC = RecordCodecBuilder
            .mapCodec((builder)
                    -> builder
                    //.group(TagKey.codec(Registries.BIOME).optionalFieldOf("value", null)
                    .group(ResourceLocation.CODEC.optionalFieldOf("value", null)
                            .forGetter(BiomeTagRequirement::getResourceLocation)).apply(builder,BiomeTagRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BiomeTagRequirement> STREAM_CODEC = StreamCodec.composite(
            ResourceLocation.STREAM_CODEC, BiomeTagRequirement::getResourceLocation,
            BiomeTagRequirement::new
    );
    public ResourceLocation getResourceLocation(){
        return tag.location();
    }
   /* public static final StreamCodec<RegistryFriendlyByteBuf, BiomeTagRequirement> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.BIOME), BiomeTagRequirement::tag,
            BiomeTagRequirement::new
    );*/

    public static BiomeTagRequirement of(TagKey<Biome> tag) {
        return new BiomeTagRequirement(tag);
    }

    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(tag == null)
            return true;
        Holder<Biome> blockEntityBiome = pLevel.getBiome(pBlockEntity.getBlockPos());

        if(pLevel.isClientSide()){
            return false;
        }

        return pLevel.getServer().registryAccess().registryOrThrow(Registries.BIOME).getTag(tag).map(t ->
            t.contains(blockEntityBiome)
        ).orElse(false);
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalLemonRecipeRequirementTypes.BIOME_TAG.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return tag.location().toString();
    }

}
