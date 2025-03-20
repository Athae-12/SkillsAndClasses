package com.athae.skillsandclasses.playerStats;

public class PlayerClass {
    private double damageMultiplier;
    private double defenseMultiplier;
    private double movementSpeedMultiplier;

    public PlayerClass(double damageMultiplier, double defenseMultiplier, double movementSpeedMultiplier) {
        this.damageMultiplier = damageMultiplier;
        this.defenseMultiplier = defenseMultiplier;
        this.movementSpeedMultiplier = movementSpeedMultiplier;
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
}