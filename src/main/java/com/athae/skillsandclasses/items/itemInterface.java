package com.athae.skillsandclasses.items;

import com.athae.skillsandclasses.playerStats.PlayerStats;
import net.minecraft.world.item.Item;

public class ExampleWeapon extends Item implements StatModifyingItem {
    public ExampleWeapon(Properties properties) {
        super(properties);
    }

    @Override
    public void applyStatModifiers(PlayerStats stats) {
        stats.modifyStat("damage", 5.0);
        stats.modifyStat("crit_chance", 0.05);
    }

    @Override
    public void removeStatModifiers(PlayerStats stats) {
        stats.modifyStat("damage", -5.0);
        stats.modifyStat("crit_chance", -0.05);
    }
}