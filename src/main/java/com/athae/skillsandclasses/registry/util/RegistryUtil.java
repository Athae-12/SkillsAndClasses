package com.athae.skillsandclasses.registry.util;

import java.util.function.Predicate;

public class RegistryUtil {

    // set this on start of mod loading and when anything else is being registered later..
    // later rework into something better
    public static String CURRENT_MODID_BEING_REGISTERED = "";

    public static Predicate<String> MODID_TO_GENERATE_DATA = x -> false;

    public static void setCurrentRegistarMod(String modid) {
        CURRENT_MODID_BEING_REGISTERED = modid;
        MODID_TO_GENERATE_DATA = x -> x.equals(modid);
    }
}