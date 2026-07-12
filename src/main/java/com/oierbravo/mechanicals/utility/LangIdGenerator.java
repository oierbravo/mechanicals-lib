package com.oierbravo.mechanicals.utility;

public class LangIdGenerator {

    public static String creativeTab(){
        return creativeTab("main");
    }
    public static String creativeTab(String variant){
        return "itemGroup:" + variant;
    }

    public static String jade(String namespace) {
        return "config.jade.plugin_" + namespace + ".data";
    }
    public static String jade(String namespace, String variant) {
        return "config.jade.plugin_" + namespace + "." + variant;
    }

    public static String recipeRequirement(String id, String variant) {
        return "ui.recipe_requirement." + id + ".tooltip." + variant;
    }

}
