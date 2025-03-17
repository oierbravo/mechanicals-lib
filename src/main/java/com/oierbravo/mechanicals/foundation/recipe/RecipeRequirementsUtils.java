package com.oierbravo.mechanicals.foundation.recipe;

public class RecipeRequirementsUtils {
    /*public static List<String> getMissingRequirements(List<IRecipeRequirement> pRecipeRequirements, BlockEntity pBlockEntity){
        ArrayList<String> missingRequirements = new ArrayList<>();
        for( IRecipeRequirement requirement : pRecipeRequirements ){
            if(!requirement.test(pBlockEntity.getLevel(), pBlockEntity)){
                missingRequirements.add(requirement.getIdString());
            }
        }
        return missingRequirements;
    }*/

    /*public static <BR extends IRecipeWithRequirements> List<Pair<Component,Component>> getRequirementsTooltips(BR recipe){
        if(recipe.getRecipeRequirements().isEmpty())
            return List.of();
        return recipe.getRecipeRequirements().stream().map(IRecipeRequirement::toTooltipComponent).toList();
    }*/

}
