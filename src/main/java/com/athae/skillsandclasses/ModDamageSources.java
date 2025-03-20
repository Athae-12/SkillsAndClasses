package com.athae.skillsandclasses;

import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources {
    public static final DamageSource PHYSICAL = new DamageSource("physical").bypassArmor();
    public static final DamageSource FIRE = new DamageSource("fire").setIsFire();
    public static final DamageSource WATER = new DamageSource("water");
    public static final DamageSource LIGHTNING = new DamageSource("lightning");
    public static final DamageSource LIGHT = new DamageSource("light");
    public static final DamageSource EARTH = new DamageSource("earth");
    public static final DamageSource DARK = new DamageSource("dark");

    // Add more custom damage sources as needed
}