package com.athae.skillsandclasses.registry.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class LibClientOnly {
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}