package com.oierbravo.mechanicals.utility;

public class MechanicalLangIdGenerator {

    public static String creativeTabId(String variant){
        return "itemGroup:" + variant;
    }

    public static String jadeId(String namespace) {
        return "config.jade.plugin_" + namespace + ".data";
    }

    public static String recipeRequirement(String id, String variant) {
        return "ui.recipe_requirement." + id + ".tooltip." + variant;
    }

}
