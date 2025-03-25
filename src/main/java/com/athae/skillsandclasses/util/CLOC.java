package com.athae.skillsandclasses.util;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.FormattedText;
import net.minecraft.network.chat.MutableComponent;


public class CLOC {

    public static String translate(FormattedText s) {
        return I18n.get(s.getString()
                        .replaceAll("%", "PERCENT"))
                .replaceAll("PERCENT", "%");

    }

    public static MutableComponent base(String s) {
        if (s.isEmpty()) {
            return Component.literal("");
        } else {
            return Component.translatable(s);
        }
    }

    public static MutableComponent blank(String string) {
        return base(string);
    }

}
