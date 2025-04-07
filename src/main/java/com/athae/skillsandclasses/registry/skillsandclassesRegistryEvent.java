package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.events.skillsandclassesEvent;
import com.athae.skillsandclasses.registry.info.RegistrationInfo;

public class skillsandclassesRegistryEvent extends skillsandclassesEvent {
    public skillsandclassesRegistryType type;

    public skillsandclassesRegistryEvent(skillsandclassesRegistryType type) {
        this.type = type;
    }

    public void addSeriazable(skillsandclassesRegistry<?> en, RegistrationInfo info) {
        if (en instanceof JsonRegistry<?> == false) {
            throw new RuntimeException("Not seriazable: " + en.GUID());
        }
        ((JsonRegistry<?>) en).addToSerializables(info);
    }

    public void add(skillsandclassesRegistry<?> en, RegistrationInfo info) {
        en.registerToExileRegistry(info);
    }
}
