package com.athae.skillsandclasses.entity.data;

import com.athae.skillsandclasses.Classes.StatContainer.StatContainer;
import com.athae.skillsandclasses.Classes.StatContainer.StatData;

import java.util.HashMap;


// todo maybe instead there should be a way for stats to modify player data in a datapack way?
// like a on stat calculation event?
public class UnsavedMaxEffectStacksData {

    public HashMap<String, Integer> bonus = new HashMap<>();

    public void calc(StatContainer c) {

        for (StatData stat : c.stats.values()) {
            if (stat.GetStat() instanceof MaximumChargesStat b) {
                bonus.put(b.effect.GUID(), (int) stat.getValue());
            }
        }
    }

}
