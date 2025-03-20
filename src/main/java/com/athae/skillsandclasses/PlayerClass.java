package com.athae.skillsandclasses.stats;

public enum PlayerClass {
    WARRIOR("Warrior", 1.5, 1.2, 0.9, 1.0, 1.0), // Damage Multiplier, Defense Multiplier, Movement Speed Multiplier, Health Multiplier, Mana Multiplier
    MAGE("Mage", 0.8, 0.5, 1.0, 0.8, 1.5),
    ROGUE("Rogue", 1.1, 0.8, 1.2, 0.9, 0.9);

    private final String name;
    private final double damageMultiplier;
    private final double defenseMultiplier;
    private final double movementSpeedMultiplier;
    private final double healthMultiplier;
    private final double manaMultiplier;

    PlayerClass(String name, double damageMultiplier, double defenseMultiplier, double movementSpeedMultiplier, double healthMultiplier, double manaMultiplier) {
        this.name = name;
        this.damageMultiplier = damageMultiplier;
        this.defenseMultiplier = defenseMultiplier;
        this.movementSpeedMultiplier = movementSpeedMultiplier;
        this.healthMultiplier = healthMultiplier;
        this.manaMultiplier = manaMultiplier;
    }

    public String getName() {
        return name;
    }

    public double getDamageMultiplier() {
        return damageMultiplier;
    }

    public double getDefenseMultiplier() {
        return defenseMultiplier;
    }

    public double getMovementSpeedMultiplier() {
        return movementSpeedMultiplier;
    }

    public double getHealthMultiplier() {
        return healthMultiplier;
    }

    public double getManaMultiplier() {
        return manaMultiplier;
    }
}