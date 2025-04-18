package com.oierbravo.mechanicals.utility;


import com.tterrag.registrate.AbstractRegistrate;

public class RegistrateLangBuilder<R extends AbstractRegistrate<?>> {
    private final String namespace;
    private final R registrate;

    public RegistrateLangBuilder(String namespace, R registrate) {
        this.namespace = namespace;
        this.registrate = registrate;
    }
    public RegistrateLangBuilder<R> add(String literal, String defaultTranslation){
        registrate.addRawLang(namespace + "." + literal,defaultTranslation);
        return this;
    }
    public RegistrateLangBuilder<R> addCreativeTab(String defaultTranslation){
        return add(MechanicalLangIdGenerator.creativeTabId( "main"), defaultTranslation);
    }
    public RegistrateLangBuilder<R> addJade(String defaultTranslation){
        registrate.addRawLang(MechanicalLangIdGenerator.jadeId(namespace),defaultTranslation);
        return this;
    }
    public RegistrateLangBuilder<R> addRaw(String literal, String defaultTranslation){
        registrate.addRawLang(literal,defaultTranslation);
        return this;
    }

    protected RegistrateLangBuilder<R> addRecipeRequirement(String variant, String id, String defaultTranslation){
        registrate.addRawLang("mechanicals." + MechanicalLangIdGenerator.recipeRequirement(id, variant),defaultTranslation);
        return this;
    }
    public RegistrateLangBuilder<R> addRecipeRequirementTitle(String id, String defaultTranslation){
        return addRecipeRequirement("title", id, defaultTranslation);
    }
    public RegistrateLangBuilder<R> addRecipeRequirementValue(String id, String defaultTranslation){
        return addRecipeRequirement("value", id, defaultTranslation);
    }
    public RegistrateLangBuilder<R> addRecipeRequirementMissing(String id, String defaultTranslation){
        return addRecipeRequirement("missing", id, defaultTranslation);
    }

    protected RegistrateLangBuilder<R> addTooltip(String id, String type,  String variant, String defaultTranslation){
        registrate.addRawLang(type + "." + namespace + "." + id + ".tooltip." + variant,defaultTranslation);
        return this;
    }
    /* Item tooltips */
    public RegistrateLangBuilder<R> addItemTooltip(String id, String variant, String defaultTranslation){
        return addTooltip(id, "item", variant, defaultTranslation);
    }
    public RegistrateLangBuilder<R> addItemTooltipSummary(String id, String defaultTranslation){
        return addItemTooltip(id,  "summary", defaultTranslation);

    }
    public RegistrateLangBuilder<R> addItemTooltipCondition(String id, int index, String defaultTranslation){
        return addItemTooltip(id,  "condition" + index, defaultTranslation);

    }
    public RegistrateLangBuilder<R> addItemTooltipBehaviour(String id, int index, String defaultTranslation){
        return addItemTooltip(id,  "behaviour" + index, defaultTranslation);

    }
    /* Block tooltips */
    protected RegistrateLangBuilder<R> addBlockTooltip(String id, String variant, String defaultTranslation){
        return addTooltip(id, "block", variant, defaultTranslation);
    }
    public RegistrateLangBuilder<R> addBlockTooltipSummary(String id, String defaultTranslation){
        return addBlockTooltip(id,  "summary", defaultTranslation);
    }
    public RegistrateLangBuilder<R> addBlockTooltipCondition(String id, int index, String defaultTranslation){
        return addBlockTooltip(id,  "condition" + index, defaultTranslation);
    }
    public RegistrateLangBuilder<R> addBlockTooltipBehaviour(String id, int index, String defaultTranslation){
        return addBlockTooltip(id,  "behaviour" + index, defaultTranslation);

    }

    /* Ponder */
    protected RegistrateLangBuilder<R> addPonder(String id,  String variant, String defaultTranslation){
        registrate.addRawLang(namespace + ".ponder." + id + "." + variant,defaultTranslation);
        return this;
    }
    public RegistrateLangBuilder<R> addPonderHeader(String id, String defaultTranslation){
        return addPonder(id, "header", defaultTranslation);
    }
    public RegistrateLangBuilder<R> addPonderText(int index, String id, String defaultTranslation){
        return addPonder(id, "text_" + index, defaultTranslation);
    }
}
