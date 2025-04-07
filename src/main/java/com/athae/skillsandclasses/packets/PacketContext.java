package com.athae.skillsandclasses.packets;

import com.athae.skillsandclasses.registry.util.LibClientOnly;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

public class PacketContext {
    NetworkEvent.Context forgeCTX;

    public PacketContext(NetworkEvent.Context forgeCTX) {
        this.forgeCTX = forgeCTX;
    }

    public Player getPlayer() {

        Player p = forgeCTX.getSender();

        if (p == null) {
            return LibClientOnly.getPlayer();
        }
        return p;
    }
}
