package com.athae.skillsandclasses.interfaces;

import com.athae.skillsandclasses.interfaces.IWeighted;
import com.athae.skillsandclasses.registry.IGUID;
import com.google.gson.JsonObject;

public interface ISerializable<T> {
    JsonObject toJson();

    T fromJson(JsonObject json);

    static String ID = "id";
    static String WEIGHT = "weight";

    default String datapackFolder() {
        return "";
    }

    default String getGUIDFromJson(JsonObject json) {
        return json.get(ID)
                .getAsString();
    }

    default int getWeightFromJson(JsonObject json) {
        return json.get(WEIGHT)
                .getAsInt();
    }

    default JsonObject getDefaultJson() {
        JsonObject json = new JsonObject();

        if (this instanceof IGUID) {
            IGUID claz = (IGUID) this;
            json.addProperty(ID, claz.GUID());
        }

        if (this instanceof IWeighted) {
            IWeighted claz = (IWeighted) this;
            json.addProperty(WEIGHT, claz.Weight());
        }

        return json;
    }

    default String toJsonString() {
        return toJson().toString();
    }

    default boolean shouldGenerateJson() {
        return true;
    }

}