package com.athae.skillsandclasses.util;

import com.athae.skillsandclasses.entity.data.EntityData;
import net.minecraft.world.entity.Entity;

public class Load {
    public static EntityData Unit(Entity entity) {
        return EntityData.fromEntity(entity);
    }
}