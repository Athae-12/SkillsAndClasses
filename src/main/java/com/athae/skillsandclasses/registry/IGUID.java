package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;

import java.util.Locale;

public interface IGUID {

    public String GUID();

    default String getFileName() {
        return GUID();
    }

    public default String getFormatedForLangFile(String str) {
        return str.replaceAll(" ", "_")
                .toLowerCase(Locale.ROOT)
                .replaceAll("/", ".")
                .replaceAll(":", ".");
    }

    default boolean isGuidFormattedCorrectly() {
        if (GUID() == null) {
            skillsandclassesLog.get().warn("Null guid detected!!! " + getClass().toString());
        }

        return isGUIDFormattedCorrectly(GUID());
    }

    public static boolean isGUIDFormattedCorrectly(String id) {

        if (id == null) {
            return false;
        }

        for (char c : id.toCharArray()) {
            if (!isValidPathCharacter(c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isValidPathCharacter(char c) {
        return c >= '0' && c <= '9' || c >= 'a' && c <= 'z' || c == '_' || c == ':' || c == '/' || c == '.' || c == '-';
    }

}
