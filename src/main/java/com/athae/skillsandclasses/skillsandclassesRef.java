package com.athae.skillsandclasses;

import net.minecraft.resources.ResourceLocation;

public class skillsandclassesRef {
    public static ResourceLocation id(String id) {
        return new ResourceLocation(MODID, id);
    }

    public static ResourceLocation guiId(String id) {
        return new ResourceLocation(skillsandclassesRef.MODID, "textures/gui/" + id + ".png");
    }

    public static final String MODID = "skillsandclasses";
    public static final String MOD_NAME = "Mine and Slash";
}
