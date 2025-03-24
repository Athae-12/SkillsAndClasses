package com.athae.skillsandclasses.util;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;

public class WeaponFinderUtil {
    public static ItemStack getWeapon(LivingEntity attacker, Entity target) {
        // Implement logic to find the weapon used by the attacker
        return attacker.getMainHandItem();
    }
}