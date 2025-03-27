package com.athae.skillsandclasses.resources;

import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatScaling;
import com.athae.skillsandclasses.resources.Elements;
import net.minecraft.ChatFormatting;

public class Stamina extends Stat {
    public static String GUID = "energy";

    public static Stamina getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Energy is used to attack with a weapon";
    }

    private Stamina() {
        this.min = 0;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.format = ChatFormatting.GREEN.getName();
        this.icon = "\u262F";
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
    public String locNameForLangFile() {
        return "Energy";
    }

    private static class SingletonHolder {
        private static final Stamina INSTANCE = new Stamina();
    }
}
