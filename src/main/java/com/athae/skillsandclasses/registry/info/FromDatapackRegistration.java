package com.athae.skillsandclasses.registry.info;

import net.minecraft.resources.ResourceLocation;

public class FromDatapackRegistration extends RegistrationInfo {

    public ResourceLocation jsonLocation;

    public FromDatapackRegistration(ResourceLocation jsonLocation) {
        this.jsonLocation = jsonLocation;
    }
}