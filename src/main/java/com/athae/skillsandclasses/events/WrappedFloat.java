package com.athae.skillsandclasses.events;

public class WrappedFloat {
    private float value;

    public WrappedFloat(float value) {
        this.value = value;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}