package com.oierbravo.mechanicals.utility;

import com.oierbravo.mechanicals.Mechanicals;
import net.createmod.catnip.lang.Lang;
import net.createmod.catnip.lang.LangBuilder;

public class LibLang extends Lang {
    public LibLang() {
        super();
    }
    public static LangBuilder builder() {
        return new LangBuilder(Mechanicals.MODID);
    }
    public static LangBuilder translate(String langKey, Object... args) {
        return builder().translate(langKey, args);
    }
}
