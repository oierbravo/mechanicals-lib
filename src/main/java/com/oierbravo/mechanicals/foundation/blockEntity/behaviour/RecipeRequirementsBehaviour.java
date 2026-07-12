package com.oierbravo.mechanicals.foundation.blockEntity.behaviour;

import com.oierbravo.mechanicals.foundation.recipe.IRecipeWithRequirements;
import com.oierbravo.mechanicals.utility.LibLang;
import com.oierbravo.mechanicals.utility.LangIdGenerator;
import com.simibubi.create.foundation.blockEntity.SmartBlockEntity;
import com.simibubi.create.foundation.blockEntity.behaviour.BehaviourType;
import com.simibubi.create.foundation.blockEntity.behaviour.BlockEntityBehaviour;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;

public class RecipeRequirementsBehaviour<R extends IRecipeWithRequirements> extends BlockEntityBehaviour {
    public static final BehaviourType<RecipeRequirementsBehaviour<?>> TYPE = new BehaviourType<>();

    public RecipeRequirementsSpecifics<R> specifics;

    private ArrayList<String> missingRequirements;

    public <T extends SmartBlockEntity & RecipeRequirementsSpecifics<R>> RecipeRequirementsBehaviour(T te) {
        super(te);
        this.specifics = te;
        this.missingRequirements = new ArrayList<>();
    }

    @Override
    public BehaviourType<?> getType() {
        return TYPE;
    }

    public boolean meetsRequirements(){
        return !missingRequirements.isEmpty();
    }

    public boolean checkRequirements(R pRecipe) {
        missingRequirements = new ArrayList<>();

        if(!specifics.matchesIngredients(pRecipe)){
            missingRequirements.add("ingredients");
            blockEntity.sendData();
            return false;
        }

        if(!specifics.hasEnoughOutputSpace(pRecipe)){
            missingRequirements.add("output");
        }

        missingRequirements.addAll(pRecipe.getMissingRequirements((BlockEntity) specifics));

        if(!missingRequirements.isEmpty()){
            blockEntity.sendData();
            return false;
        }

        blockEntity.sendData();
        return true;
    }
    public void cleanRequirements(){
        missingRequirements.clear();
        blockEntity.sendData();
    }

    public boolean addToGoggleTooltip(List<Component> tooltip, boolean isPlayerSneaking, boolean added) {
        if(missingRequirements.isEmpty())
            return false;

        for(String requirementId : missingRequirements){
            LibLang.translate(LangIdGenerator.recipeRequirement(requirementId, "missing")).style(ChatFormatting.RED).forGoggles(tooltip,1);
            added = true;
        }
        return added;
    }

    @Override
    public void write(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        ListTag missingRequirementsTag = new ListTag();
        for(String missingId : missingRequirements){
            CompoundTag tag = new CompoundTag();
            tag.putString("t",missingId);
            missingRequirementsTag.add(tag);
        }
        compound.put("MissingRequirements", missingRequirementsTag);
        super.write(compound, registries, clientPacket);
    }

    @Override
    public void read(CompoundTag compound, HolderLookup.Provider registries, boolean clientPacket) {
        missingRequirements = new ArrayList<>();
        ListTag missingRequirementsTag = compound.getList("MissingRequirements", Tag.TAG_COMPOUND);
        for( Tag tag : missingRequirementsTag ){
            CompoundTag requirementsTag = (CompoundTag) tag;
            missingRequirements.add(requirementsTag.getString("t"));
        }
        super.read(compound, registries, clientPacket);
    }

    public interface RecipeRequirementsSpecifics<R extends IRecipeWithRequirements> {
        boolean hasEnoughOutputSpace(R recipe);
        boolean matchesIngredients(R recipe);
    }
}
