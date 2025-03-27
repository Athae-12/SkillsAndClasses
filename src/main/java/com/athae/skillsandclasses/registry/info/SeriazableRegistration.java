package com.athae.skillsandclasses.registry.info;

import com.athae.skillsandclasses.registry.RegistrationInfo;

public class SeriazableRegistration extends RegistrationInfo {

    public String modid;

    public SeriazableRegistration(String modid) {
        this.modid = modid;
    }
}
