package com.athae.skillsandclasses.playerStats;

public class PlayerStats {
    private double movementSpeed = 0.1;
    private double jumpHeight = 1.0;
    private double damage = 1.0;
    private double defense = 0.0;

    // Elemental stats
    private double fireAffinity = 0.0;
    private double waterAffinity = 0.0;
    private double lightningAffinity = 0.0;

    // Player class
    private PlayerClass playerClass = PlayerClass.WARRIOR; // Default class is Warrior

    // Getters and setters
    public PlayerClass getPlayerClass() { return playerClass; }
    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;

        // Adjust base stats based on class multipliers
        this.damage *= playerClass.getDamageMultiplier();
        this.defense *= playerClass.getDefenseMultiplier();
        this.movementSpeed *= playerClass.getMovementSpeedMultiplier();
    }

    public double getMovementSpeed() { return movementSpeed; }
    public void setMovementSpeed(double movementSpeed) { this.movementSpeed = movementSpeed; }

    public double getJumpHeight() { return jumpHeight; }
    public void setJumpHeight(double jumpHeight) { this.jumpHeight = jumpHeight; }

    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }

    public double getDefense() { return defense; }
    public void setDefense(double defense) { this.defense = defense; }

    // Elemental stats
    public double getFireAffinity() { return fireAffinity; }
    public double getWaterAffinity() { return waterAffinity; }
    public double getLightningAffinity() { return lightningAffinity; }
}