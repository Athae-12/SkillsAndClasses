package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.registry.info.RegistrationInfo;
import com.google.gson.JsonObject;
import com.athae.skillsandclasses.interfaces.IWeighted;

public interface skillsandclassesRegistry<T> extends IGUID, IWeighted {
    skillsandclassesRegistryType getExileRegistryType();


    default void registerToExileRegistry(RegistrationInfo info) {
        Database.ExileRegistryType(getExileRegistryType()).register(this, info);
    }

    default RegistrationInfoData getRegistrationInfo() {
        return (RegistrationInfoData) Database.ExileRegistryType(getExileRegistryType()).registrationInfo.get(this.GUID());
    }

    default void compareLoadedJsonAndFinalClass(JsonObject json, Boolean editmode) {
    }

    default void unregisterFromExileRegistry() {
        Database.ExileRegistryType(getExileRegistryType()).unRegister(this);
    }

    default String getRegistryIdPlusGuid() {
        return getExileRegistryType().id + ":" + GUID();
    }

    default boolean isEmpty() {
        var db = Database.ExileRegistryType(getExileRegistryType());
        var em = db.getDefault();

        if (em != null) {
            if (em.GUID().equals(GUID())) {
                return true;
            }
        }
        return db.isRegistered(GUID());
    }

    default void unregisterDueToInvalidity() {
        Database.ExileRegistryType(getExileRegistryType())
                .unRegister(this);
        try {
            throw new Exception("Registry Entry: " + GUID() + " of type: " + this.getExileRegistryType()
                    .id + " is invalid! Unregistering");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    default boolean isFromDatapack() {
        return false;
    }

    default boolean isRegistryEntryValid() {
        // override with an implementation of a validity test
        return true;
    }

    default String getInvalidGuidMessage() {
        return "Non [a-z0-9_.-] character in Mine and Slash GUID: " + GUID() + " of type " + getExileRegistryType().id;
    }
}
