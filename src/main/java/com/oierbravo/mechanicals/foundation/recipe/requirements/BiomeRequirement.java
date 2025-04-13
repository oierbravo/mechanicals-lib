package com.oierbravo.mechanicals.foundation.recipe.requirements;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.foundation.recipe.IRecipeRequirement;
import com.oierbravo.mechanicals.foundation.recipe.RecipeRequirementType;
import com.oierbravo.mechanicals.register.MechanicalRecipeRequirementTypes;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;

public record BiomeRequirement(ResourceKey<Biome> biomeResourceKey) implements IRecipeRequirement {
    public static String ID = "biome";

    public static MapCodec<BiomeRequirement> CODEC = RecordCodecBuilder
            .mapCodec((builder)
                    -> builder
                    .group(ResourceKey.codec(Registries.BIOME).optionalFieldOf("value", null)
                            .forGetter(BiomeRequirement::biomeResourceKey)).apply(builder,BiomeRequirement::new));

    public static final StreamCodec<RegistryFriendlyByteBuf, BiomeRequirement> STREAM_CODEC = StreamCodec.composite(
            ResourceKey.streamCodec(Registries.BIOME), BiomeRequirement::biomeResourceKey,
            BiomeRequirement::new
    );

    public static BiomeRequirement of(ResourceKey<Biome> key) {
        return new BiomeRequirement( key);
    }
    public static BiomeRequirement of(String id) {
        return of(ResourceLocation.parse(id));
    }
    public static BiomeRequirement of(ResourceLocation resourceLocation) {
        return of(ResourceKey.create(Registries.BIOME, resourceLocation));
    }

    public boolean test(Level pLevel, BlockEntity pBlockEntity) {
        if(biomeResourceKey == null)
            return true;
        Holder<Biome> blockEntityBiome = pLevel.getBiome(pBlockEntity.getBlockPos());

        if(pLevel.isClientSide()){
            return false;
        }

        Optional<Holder.Reference<Biome>> requiredBiomeHolder = pLevel.getServer().registryAccess().registryOrThrow(Registries.BIOME).asLookup().get(biomeResourceKey);

        if(requiredBiomeHolder.isPresent()
           && blockEntityBiome.is(requiredBiomeHolder.get().key()))
            return true;

        return false;
    }

    @Override
    public RecipeRequirementType<?> getType() {
        return MechanicalRecipeRequirementTypes.BIOME.get();
    }

    @Override
    public String getIdString() {
        return ID;
    }

    @Override
    public String toString() {
        return biomeResourceKey.location().toString();
    }

}
