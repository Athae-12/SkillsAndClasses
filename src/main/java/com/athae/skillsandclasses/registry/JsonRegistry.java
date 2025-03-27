package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.interfaces.ISerializable;
import com.athae.skillsandclasses.registry.RegistrationInfo;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public interface JsonRegistry<T> extends skillsandclassesRegistry<T> {

    default void addToSerializables(RegistrationInfo info) {
        Database.getRegistry(getskillsandclassesRegistryType()).addSerializable(this, info);
    }


    public static HashMap<skillsandclassesRegistryType, Set<String>> INVALID_JSONS_MAP = new HashMap<skillsandclassesRegistryType, Set<String>>();

    // this is never called because mc already errors for invalid json syntax
    public static HashMap<skillsandclassesRegistryType, Set<ResourceLocation>> NOT_LOADED_JSONS_MAP = new HashMap<skillsandclassesRegistryType, Set<ResourceLocation>>();

    public static void addToInvalidJsons(skillsandclassesRegistryType type, String id) {

        if (!INVALID_JSONS_MAP.containsKey(type)) {
            INVALID_JSONS_MAP.put(type, new HashSet<>());
        }
        INVALID_JSONS_MAP.get(type).add(id);
    }

    public static void addToErroredJsons(skillsandclassesRegistryType type, ResourceLocation id) {

        if (!NOT_LOADED_JSONS_MAP.containsKey(type)) {
            NOT_LOADED_JSONS_MAP.put(type, new HashSet<>());
        }
        NOT_LOADED_JSONS_MAP.get(type).add(id);
    }

    skillsandclassesLog LOGGER = skillsandclassesLog.get();

    @Override
    default void compareLoadedJsonAndFinalClass(JsonObject json, Boolean editmode) {
        if (this instanceof ISerializable ser) {
            var after = ser.toJson();

            if (editmode) {
                //if the json only edits some values, we only check those values
                for (Map.Entry<String, JsonElement> en : new HashSet<>(after.entrySet())) {
                    if (json.has(en.getKey())) {
                        after.remove(en.getKey());
                    }
                }
                return;
            }
            var v1 = JsonParser.parseString(json.toString());
            var v2 = JsonParser.parseString(after.toString());

            if (!v1.equals(v2)) {
                LOGGER.warn("============[" + getskillsandclassesRegistryType().getModName() + " Datapack Check Failed]=================");
                LOGGER.warn("The file with id " + this.getRegistryIdPlusGuid() + " is different after loading");
                LOGGER.warn("Json from your datapack:");
                LOGGER.warn(json.toString());
                LOGGER.warn("Json after it was loaded and turned back into json:");
                LOGGER.warn(after.toString());
                LOGGER.warn("Please check for things like wrong field names, missing fields, wrong types used etc.");
                LOGGER.warn("You can copy and paste these jsons into any online Json Comparison/Diff tools see what the difference is. Like: www.jsondiff.com");
                LOGGER.warn("===================================================================");
                addToInvalidJsons(getskillsandclassesRegistryType(), GUID());
            }

        }
    }

    @Override
    default boolean isFromDatapack() {
        return true;
    }
}
