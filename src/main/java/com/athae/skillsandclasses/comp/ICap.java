package com.athae.skillsandclasses.comp;

import com.athae.skillsandclasses.packets.Packets;
import com.athae.skillsandclasses.packets.SyncPlayerCapToClient;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;

public interface ICap extends ICapabilitySerializable<CompoundTag> {
    public String getCapIdForSyncing();

    public default void syncToClient(Player player) {
        Packets.sendToClient(player, new SyncPlayerCapToClient(player, getCapIdForSyncing()));
    }
}