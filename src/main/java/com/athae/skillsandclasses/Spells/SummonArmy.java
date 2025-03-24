package com.athae.skillsandclasses.Spells;

import net.minecraft.server.level.ServerLevel;

public class SummonArmy {

    public static void summonArmy(ServerLevel level) {
        for (SummonedEntityData data : SummonSpell.getSummonedEntities()) {
            LivingEntity entity = data.createEntity(level);
            if (entity != null) {
                level.addFreshEntity(entity);
            }
        }
    }
}

