package com.oierbravo.mechanicals.foundation.recipe;

import net.createmod.catnip.data.Pair;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.entity.BlockEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public interface IRecipeWithRequirements {


    ArrayList<IRecipeRequirement> getRecipeRequirements();

    default List<IRecipeRequirement> getJeiRecipeRequirements(){
        return getRecipeRequirements();
    }

    default Optional<IRecipeRequirement> getRequirement(RecipeRequirementType<?> type) {
        return getRecipeRequirements().stream().filter(iRecipeRequirement -> iRecipeRequirement.getType() == type).findFirst();
    }

    default boolean meetsRequirements(BlockEntity pBlockEntity){
        for( IRecipeRequirement requirement : getRecipeRequirements()){
            if(!requirement.test(pBlockEntity.getLevel(), pBlockEntity)){
                return false;
            }
        }
        return true;
    };
    default List<String> getMissingRequirements(BlockEntity pBlockEntity){
        ArrayList<String> missingRequirements = new ArrayList<>();
        for( IRecipeRequirement requirement : getRecipeRequirements() ){
            if(!requirement.test(pBlockEntity.getLevel(), pBlockEntity)){
                missingRequirements.add(requirement.getIdString());
            }
        }
        return missingRequirements;
    };

    default List<Pair<Component,Component>> getRequirementsTooltips(){
        if(getRecipeRequirements().isEmpty())
            return List.of();
        return getRecipeRequirements().stream().map(IRecipeRequirement::toTooltipComponent).toList();
    }

    default List<Pair<Component,Component>> getJeiRequirementsTooltips(){
        if(getJeiRecipeRequirements().isEmpty())
            return List.of();
        return getJeiRecipeRequirements().stream().map(IRecipeRequirement::toTooltipComponent).toList();
    }

}
