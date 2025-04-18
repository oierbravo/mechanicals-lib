package com.oierbravo.mechanicals.foundation.ingredient;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.oierbravo.mechanicals.register.MechanicalIngredientTypes;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.crafting.ICustomIngredient;
import net.neoforged.neoforge.common.crafting.IngredientType;

import java.util.Arrays;
import java.util.function.Predicate;
import java.util.stream.Stream;

/*from SewingKit Mod

Copyright (c) 2024, David Quintana <gigaherz@gmail.com>
All rights reserved.

Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * Neither the name of the author nor the
      names of the contributors may be used to endorse or promote products
      derived from this software without specific prior written permission.

THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
DISCLAIMED. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
(INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
public record CountableIngredient(Ingredient ingredient, int count) implements Predicate<ItemStack>/*, ICustomIngredient*/ {
    public static final CountableIngredient EMPTY = new CountableIngredient(Ingredient.EMPTY,0);


    public static final Codec<CountableIngredient> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Ingredient.CODEC.fieldOf("ingredient").forGetter(CountableIngredient::ingredient),
            Codec.INT.fieldOf("count").forGetter(CountableIngredient::count)
    ).apply(instance, CountableIngredient::new));
    /*public static Codec<CountableIngredient> CODEC = RecordCodecBuilder
            .mapCodec((builder)
                    -> builder
                    .group(
                            Ingredient.CODEC.fieldOf("ingredient").forGetter(CountableIngredient::ingredient),
                            Codec.INT.fieldOf("count").forGetter(CountableIngredient::count))
                                    .apply(builder, CountableIngredient::new));

*/

    public static final StreamCodec<RegistryFriendlyByteBuf, CountableIngredient> STREAM_CODEC = StreamCodec.composite(
            Ingredient.CONTENTS_STREAM_CODEC, CountableIngredient::ingredient,
            ByteBufCodecs.INT, CountableIngredient::count,
            CountableIngredient::new
    );

    public static CountableIngredient of(Ingredient ingredient, int count)
    {
        return new CountableIngredient(ingredient, count);
    }
    public static CountableIngredient of(ItemStack itemStack)
    {
        return new CountableIngredient(Ingredient.of(itemStack), itemStack.getCount());
    }

    @Override
    public boolean test(ItemStack itemStack)
    {
        return ingredient.test(itemStack) && itemStack.getCount() >= count;
    }

    public ItemStack asItemStack(){
        return new ItemStack(Arrays.stream(ingredient.getItems()).findFirst().get().getItem(), count);
    }

    /*@Override
    public Stream<ItemStack> getItems() {
        return Arrays.stream(ingredient.getItems());
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    @Override
    public IngredientType<?> getType() {
        return MechanicalIngredientTypes.COUNTABLE_INGREDIENT.get();
    }*/
}
