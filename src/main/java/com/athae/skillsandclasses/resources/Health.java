package com.athae.skillsandclasses.resources;

import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatScaling;
import net.minecraft.ChatFormatting;

public class Health extends Stat {
    public static String GUID = "health";

    private Health() {
        this.min = 1;
        this.scaling = StatScaling.NORMAL;
        this.group = StatGroup.MAIN;

        this.order = 0;
        this.icon = "\u2764";
        this.format = ChatFormatting.RED.getName();

    }

    public static Health getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String locDescForLangFile() {
        return "Increases your total hearts amount.";
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
        return "Health";
    }


    private static class SingletonHolder {
        private static final Health INSTANCE = new Health();
    }
}
