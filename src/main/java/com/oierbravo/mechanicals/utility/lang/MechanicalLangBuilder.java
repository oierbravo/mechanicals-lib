package com.oierbravo.mechanicals.utility.lang;

import net.createmod.catnip.lang.LangBuilder;

public class MechanicalLangBuilder {
    private String langKey;
    private String namespace;
    public LangBuilder builder() {
        return new LangBuilder(namespace);
    }

    public MechanicalLangBuilder(String namespace) {
        this.namespace = namespace;
    }
    public MechanicalLangBuilder key(String key){
        this.langKey = key;
        return this;
    }
    public LangBuilder t(Object... args){
        LangBuilder builder = builder();
        builder.translate(this.langKey,args);
        return builder;
    }

}
