package com.athae.skillsandclasses.tags;

public abstract class NormalModTag extends ModTag {

    private String id;

    public NormalModTag(String id) {
        this.id = id;
    }

    @Override
    public String GUID() {
        return id;
    }
}