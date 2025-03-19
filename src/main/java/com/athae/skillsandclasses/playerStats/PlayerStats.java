package com.athae.skillsandclasses.playerStats;

import com.athae.skillsandclasses.items.StatModifyingItem;
import net.minecraft.world.item.ItemStack;

public class PlayerStats {
    private int level = 1;
    private double experience = 0.0;
    private double movementSpeed = 0.1;
    private double jumpHeight = 1.0;
    private double damage = 1.0;
    private double defense = 0.0;
    private double health = 100.0;
    private double mana = 50.0;
    private double stamina = 50.0;
    private double critDamage = 1.5;
    private double critChance = 0.1;

    // Elemental stats
    private double fireAffinity = 0.0;
    private double waterAffinity = 0.0;
    private double lightningAffinity = 0.0;
    private double lightAffinity = 0.0;
    private double earthAffinity = 0.0;
    private double darkAffinity = 0.0;
    // Elemental defenses
    private double fireDefense = 0.0;
    private double waterDefense = 0.0;
    private double lightningDefense = 0.0;
    private double lightDefense = 0.0;
    private double earthDefense = 0.0;
    private double darkDefense = 0.0;

    // Player class
    private PlayerClass playerClass = null; // No default class

    // Getters and setters
    public int getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }

    public double getExperience() { return experience; }
    public void setExperience(double experience) { this.experience = experience; }

    public PlayerClass getPlayerClass() { return playerClass; }
    public void setPlayerClass(PlayerClass playerClass) {
        this.playerClass = playerClass;

        if (playerClass != null) {
            // Adjust base stats based on class multipliers
            this.damage *= playerClass.getDamageMultiplier();
            this.defense *= playerClass.getDefenseMultiplier();
            this.movementSpeed *= playerClass.getMovementSpeedMultiplier();
        }
    }

    public double getMovementSpeed() { return movementSpeed; }
    public void setMovementSpeed(double movementSpeed) { this.movementSpeed = movementSpeed; }

    public double getJumpHeight() { return jumpHeight; }
    public void setJumpHeight(double jumpHeight) { this.jumpHeight = jumpHeight; }

    public double getDamage() { return damage; }
    public void setDamage(double damage) { this.damage = damage; }

    public double getDefense() { return defense; }
    public void setDefense(double defense) { this.defense = defense; }

    public double getHealth() { return health; }
    public void setHealth(double health) { this.health = health; }

    public double getMana() { return mana; }
    public void setMana(double mana) { this.mana = mana; }

    public double getStamina() { return stamina; }
    public void setStamina(double stamina) { this.stamina = stamina; }

    public double getCritDamage() { return critDamage; }
    public void setCritDamage(double critDamage) { this.critDamage = critDamage; }

    public double getCritChance() { return critChance; }
    public void setCritChance(double critChance) { this.critChance = critChance; }

    // Elemental stats
    public double getFireAffinity() { return fireAffinity; }
    public void setFireAffinity(double fireAffinity) { this.fireAffinity = fireAffinity; }

    public double getWaterAffinity() { return waterAffinity; }
    public void setWaterAffinity(double waterAffinity) { this.waterAffinity = waterAffinity; }

    public double getLightningAffinity() { return lightningAffinity; }
    public void setLightningAffinity(double lightningAffinity) { this.lightningAffinity = lightningAffinity; }

    public double getLightAffinity() { return lightAffinity; }
    public void setLightAffinity(double lightAffinity) { this.lightAffinity = lightAffinity; }

    public double getEarthAffinity() { return earthAffinity; }
    public void setEarthAffinity(double earthAffinity) { this.earthAffinity = earthAffinity; }

    public double getDarkAffinity() { return darkAffinity; }
    public void setDarkAffinity(double darkAffinity) { this.darkAffinity = darkAffinity; }

    public double getFireDefense() { return fireDefense; }
    public void setFireDefense(double fireDefense) { this.fireDefense = fireDefense; }

    public double getWaterDefense() { return waterDefense; }
    public void setWaterDefense(double waterDefense) { this.waterDefense = waterDefense; }

    public double getLightningDefense() { return lightningDefense; }
    public void setLightningDefense(double lightningDefense) { this.lightningDefense = lightningDefense; }

    public double getLightDefense() { return lightDefense; }
    public void setLightDefense(double lightDefense) { this.lightDefense = lightDefense; }

    public double getEarthDefense() { return earthDefense; }
    public void setEarthDefense(double earthDefense) { this.earthDefense = earthDefense; }

    public double getDarkDefense() { return darkDefense; }
    public void setDarkDefense(double darkDefense) { this.darkDefense = darkDefense; }

    // Methods to modify stats
    public void addExperience(double amount) {
        this.experience += amount;
        // Check for level up
        if (this.experience >= getExperienceForNextLevel()) {
            levelUp();
        }
    }

    public double getExperienceForNextLevel() {
        return 100 * this.level; // Example formula for next level experience
    }

    public void levelUp() {
        this.level++;
        this.experience = 0; // Reset experience or carry over excess
        // Increase stats on level up
        this.health += 10;
        this.mana += 5;
        this.stamina += 5;
        this.damage += 2;
        this.defense += 1;
    }

    public void modifyStat(String stat, double amount) {
        switch (stat.toLowerCase()) {
            case "movement_speed" -> this.movementSpeed += amount;
            case "jump_height" -> this.jumpHeight += amount;
            case "damage" -> this.damage += amount;
            case "defense" -> this.defense += amount;
            case "health" -> this.health += amount;
            case "mana" -> this.mana += amount;
            case "stamina" -> this.stamina += amount;
            case "crit_damage" -> this.critDamage += amount;
            case "crit_chance" -> this.critChance += amount;
            case "fire_affinity" -> this.fireAffinity += amount;
            case "water_affinity" -> this.waterAffinity += amount;
            case "lightning_affinity" -> this.lightningAffinity += amount;
            case "light_affinity" -> this.lightAffinity += amount;
            case "earth_affinity" -> this.earthAffinity += amount;
            case "dark_affinity" -> this.darkAffinity += amount;
            case "fire_defense" -> this.fireDefense += amount;
            case "water_defense" -> this.waterDefense += amount;
            case "lightning_defense" -> this.lightningDefense += amount;
            case "light_defense" -> this.lightDefense += amount;
            case "earth_defense" -> this.earthDefense += amount;
            case "dark_defense" -> this.darkDefense += amount;
        }
    }

    public void applyItemModifiers(ItemStack itemStack) {
        if (itemStack.getItem() instanceof StatModifyingItem statModifyingItem) {
            statModifyingItem.applyStatModifiers(this);
        }
    }

    public void removeItemModifiers(ItemStack itemStack) {
        if (itemStack.getItem() instanceof StatModifyingItem statModifyingItem) {
            statModifyingItem.removeStatModifiers(this);
        }
    }

    public void resetStats() {
        this.level = 1;
        this.experience = 0.0;
        this.playerClass = null;
        // Reset other stats if needed
    }
}