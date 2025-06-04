package com.oierbravo.mechanicals.utility.lang;


import com.oierbravo.mechanicals.utility.MechanicalLangIdGenerator;
import com.tterrag.registrate.AbstractRegistrate;

import java.util.function.Supplier;

public class MechanicalRegistrateLangBuilder<R extends AbstractRegistrate<?>> {
    private final String namespace;
    private final R registrate;
    protected final Supplier<MechanicalLangBuilder> langBuilderSupplier;


    public MechanicalRegistrateLangBuilder(String namespace, R registrate, Supplier<MechanicalLangBuilder> langBuilder) {
        this.namespace = namespace;
        this.registrate = registrate;
        this.langBuilderSupplier = langBuilder;
    }
    public MechanicalLangBuilder add(String literal, String defaultTranslation){
        return addRawLang(literal,defaultTranslation);
    }
    public MechanicalLangBuilder addCreativeTab(String defaultTranslation){
        return add(MechanicalLangIdGenerator.creativeTabId( "main"), defaultTranslation);
    }
    public MechanicalLangBuilder addJade(String defaultTranslation){
        String key = MechanicalLangIdGenerator.jadeId(namespace);
        registrate.addRawLang(key,defaultTranslation);
        return langBuilderSupplier.get().key(key);
    }
    public MechanicalLangBuilder addRaw(String literal, String defaultTranslation){
        registrate.addRawLang(literal,defaultTranslation);
        return langBuilderSupplier.get().key(literal);
    }

    protected MechanicalLangBuilder addRecipeRequirement(String variant, String id, String defaultTranslation){
        String key = "mechanicals." + MechanicalLangIdGenerator.recipeRequirement(id, variant);
        registrate.addRawLang(key,defaultTranslation);
        return langBuilderSupplier.get().key(key);
    }
    public MechanicalLangBuilder addRecipeRequirementTitle(String id, String defaultTranslation){
        return addRecipeRequirement("title", id, defaultTranslation);
    }
    public MechanicalLangBuilder addRecipeRequirementValue(String id, String defaultTranslation){
        return addRecipeRequirement("value", id, defaultTranslation);
    }
    public MechanicalLangBuilder addRecipeRequirementMissing(String id, String defaultTranslation){
        return addRecipeRequirement("missing", id, defaultTranslation);
    }

    protected MechanicalLangBuilder addTooltip(String id, String type, String variant, String defaultTranslation){
        String key = type + "." + namespace + "." + id + ".tooltip." + variant;
        registrate.addRawLang(key,defaultTranslation);
        return langBuilderSupplier.get().key(key);
    }

    /* Item tooltips */
    public MechanicalLangBuilder addItemTooltip(String id, String variant, String defaultTranslation){
        return addTooltip(id, "item", variant, defaultTranslation);
    }
    public MechanicalLangBuilder addItemTooltipSummary(String id, String defaultTranslation){
        return addItemTooltip(id,  "summary", defaultTranslation);

    }
    public MechanicalLangBuilder addItemTooltipCondition(String id, int index, String defaultTranslation){
        return addItemTooltip(id,  "condition" + index, defaultTranslation);

    }
    public MechanicalLangBuilder addItemTooltipBehaviour(String id, int index, String defaultTranslation){
        return addItemTooltip(id,  "behaviour" + index, defaultTranslation);

    }
    /* Block tooltips */
    protected MechanicalLangBuilder addBlockTooltip(String id, String variant, String defaultTranslation){
        return addTooltip(id, "block", variant, defaultTranslation);
    }
    public MechanicalLangBuilder addBlockTooltipSummary(String id, String defaultTranslation){
        return addBlockTooltip(id,  "summary", defaultTranslation);
    }
    public MechanicalLangBuilder addBlockTooltipCondition(String id, int index, String defaultTranslation){
        return addBlockTooltip(id,  "condition" + index, defaultTranslation);
    }
    public MechanicalLangBuilder addBlockTooltipBehaviour(String id, int index, String defaultTranslation){
        return addBlockTooltip(id,  "behaviour" + index, defaultTranslation);

    }

    /* Ponder */
    protected MechanicalLangBuilder addPonder(String id, String variant, String defaultTranslation){
        String key = namespace + ".ponder." + id + "." + variant;
        registrate.addRawLang(key,defaultTranslation);
        return langBuilderSupplier.get().key(key);
    }
    public MechanicalLangBuilder addPonderHeader(String id, String defaultTranslation){
        return addPonder(id, "header", defaultTranslation);
    }
    public MechanicalLangBuilder addPonderText(int index, String id, String defaultTranslation){
        return addPonder(id, "text_" + index, defaultTranslation);
    }

    protected MechanicalLangBuilder addRawLang(String key, String defaultTranslation){
        return addRawLang(key, defaultTranslation, false);
    }
    protected MechanicalLangBuilder addRawLang(String key, String defaultTranslation, boolean customNamespace){
        registrate.addRawLang((customNamespace) ? key : namespace + "." + key, defaultTranslation);
        return langBuilderSupplier.get().key(key);
    }

}
