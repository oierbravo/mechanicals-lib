package com.oierbravo.mechanicals.utility;

import com.simibubi.create.foundation.data.CreateRegistrate;

public class RegistrateLangBuilder {
    private final String namespace;
    private final CreateRegistrate registrate;

    public RegistrateLangBuilder(String namespace, CreateRegistrate registrate) {
        this.namespace = namespace;
        this.registrate = registrate;
    }
    public RegistrateLangBuilder add(String literal, String defaultTranslation){
        registrate.addRawLang(namespace + "." + literal,defaultTranslation);
        return this;
    }
    public RegistrateLangBuilder addRaw(String literal, String defaultTranslation){
        registrate.addRawLang(literal,defaultTranslation);
        return this;
    }
}
