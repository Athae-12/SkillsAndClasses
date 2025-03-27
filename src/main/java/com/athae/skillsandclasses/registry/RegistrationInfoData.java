package com.athae.skillsandclasses.registry;

import java.util.HashSet;
import java.util.Set;

// TODO, rework all registration code now to use these
public class RegistrationInfoData {
    public String modid = "";

    public Set<String> datapackRegistrations = new HashSet<>();

    //public HardcodedRegistration hardCoded = null;
    //public JsonRegistration json = null;


    public void onRegister(RegistrationInfo info) {

        // these 2 give me the modid of the mod that registered it
        if (info instanceof HardcodedRegistration hard) {
            // hardCoded = hard;
            this.modid = hard.modid;
        }
        if (info instanceof SeriazableRegistration j) {
            //json = j;
            this.modid = j.modid;
        }
        // hopefully allow me to easy track from which datapacks entries are from
        if (info instanceof FromDatapackRegistration data) {
            datapackRegistrations.add(data.jsonLocation.toString());
        }
    }
}
