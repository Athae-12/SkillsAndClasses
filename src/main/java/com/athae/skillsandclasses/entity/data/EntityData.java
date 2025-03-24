package com.athae.skillsandclasses.entity.data;

import com.athae.skillsandclasses.Unit;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class EntityData {
    private final LivingEntity entity;
    public int immuneTicks = 0;

    public EntityData(LivingEntity entity) {
        this.entity = entity;
    }

    public LivingEntity getEntity() {
        return entity;
    }

    public static EntityData fromEntity(Entity entity) {
        if (entity instanceof LivingEntity) {
            return new EntityData((LivingEntity) entity);
        }
        return null;
    }

    private static final String RARITY = "rarity";
    private static final String LEVEL = "level";
    private static final String EXP = "exp";
    private static final String EXP_DEBT = "ex_d";
    private static final String HP = "hp";
    private static final String UUID = "uuid";
    private static final String SET_MOB_STATS = "set_mob_stats";
    private static final String ENTITY_TYPE = "ENTITY_TYPE";
    private static final String RESOURCES_LOC = "res_loc";
    private static final String STATUSES = "statuses";
    private static final String AILMENTS = "ailments";
    private static final String COOLDOWNS = "cds";
    private static final String THREAT = "th";
    private static final String PET = "pet";
    private static final String CUSTOM_STATS = "custom_stats";

    transient Unit unit = new Unit();
    String rarity = IRarity.COMMON_ID;
    int level = 1;
    int exp = 0;
    int expDebt = 0;
    int maxHealth = 0;

    public int getLevel() {
        return level;
    }
}