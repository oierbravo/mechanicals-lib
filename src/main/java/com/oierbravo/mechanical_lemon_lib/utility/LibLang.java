package com.oierbravo.mechanical_lemon_lib.utility;

import com.oierbravo.mechanical_lemon_lib.MechanicalLemonLib;

public class LibLang extends Lang {
    public LibLang() {
        super();
    }
    public static LangBuilder builder() {
        return new LangBuilder(MechanicalLemonLib.MODID);
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}
