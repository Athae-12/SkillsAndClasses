package com.athae.skillsandclasses.packets;

import com.athae.skillsandclasses.util.ClientOnly;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class skillsandclassesPacketContext {
    NetworkEvent.Context forgeCTX;

    public skillsandclassesPacketContext(NetworkEvent.Context forgeCTX) {
        this.forgeCTX = forgeCTX;
    }

    public Player getPlayer() {

        Player p = forgeCTX.getSender();

        if (p == null) {
            return ClientOnly.getPlayer();
        }
        return p;
    }
}

