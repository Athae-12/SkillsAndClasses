package com.athae.skillsandclasses.config;

public class CompatConfig {
    private static final CompatConfig INSTANCE = new CompatConfig();

    public static CompatConfig get() {
        return INSTANCE;
    }

    public DamageSystemConfig damageSystem() {
        return new DamageSystemConfig();
    }

    public static class DamageSystemConfig {
        public boolean overridesDamage = true;
    }
}