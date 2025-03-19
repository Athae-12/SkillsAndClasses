package com.athae.skillsandclasses.Spells;

import net.minecraft.world.entity.player.Player;

public abstract class Skill {
    protected String name;
    protected int cooldown;
    protected int manaCost;
    protected String particleEffect;
    protected String elementType;
    protected int damage;
    protected int range;

    public Skill(String name, int cooldown, int manaCost, String particleEffect, String elementType, int damage, int range) {
        this.name = name;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.particleEffect = particleEffect;
        this.elementType = elementType;
        this.damage = damage;
        this.range = range;
    }

    public String getName() {
        return name;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getParticleEffect() {
        return particleEffect;
    }

    public String getElementType() {
        return elementType;
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public abstract void activate(Player player);
}