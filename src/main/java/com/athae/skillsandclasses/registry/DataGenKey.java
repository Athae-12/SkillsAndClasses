package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.DatapackBoolean;

public class DataGenKey<T> implements IGUID {

    String id;

    public DataGenKey(String id) {
        errorIfNotDevMode();
        this.id = id;
    }

    private void errorIfNotDevMode() {
        if (!DatapackBoolean.runDevTools()) {
            // throw new RuntimeException("Do not use DataGenKeys outside of dev mode! These keys should only be used when generating data.");
        }
    }

    @Override
    public String GUID() {
        errorIfNotDevMode();
        return id;
    }
}
