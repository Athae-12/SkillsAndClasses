package com.athae.skillsandclasses.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientOnly {
    public static Player getPlayer() {
        return Minecraft.getInstance().player;
    }
}
