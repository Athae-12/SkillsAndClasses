package com.athae.skillsandclasses.items;

import com.athae.skillsandclasses.playerStats.PlayerStats;

public interface StatModifyingItem {
    void applyStatModifiers(PlayerStats stats);
    void removeStatModifiers(PlayerStats stats);
}