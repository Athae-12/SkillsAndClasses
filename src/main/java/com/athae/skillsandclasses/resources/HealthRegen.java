package com.athae.skillsandclasses.resources;

import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatScaling;
import net.minecraftforge.common.data.ExistingFileHelper;

public class HealthRegen extends BaseRegenClass {
    public static String GUID = "health_regen";

    public static HealthRegen getInstance() {
        return SingletonHolder.INSTANCE;

    }

    private HealthRegen() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = Stat.StatGroup.MAIN;
    }

    @Override
    public String GUID() {
        return GUID;
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    @Override
    public ExistingFileHelper.ResourceType getResourceType() {
        return ResourceType.health;
    }

    @Override
    public String locNameForLangFile() {
        return "Health Regen";
    }

    private static class SingletonHolder {
        private static final HealthRegen INSTANCE = new HealthRegen();
    }
}