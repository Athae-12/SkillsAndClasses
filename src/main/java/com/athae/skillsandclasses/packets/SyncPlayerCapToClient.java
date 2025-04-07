package com.athae.skillsandclasses.packets;

import com.athae.skillsandclasses.Ref;
import com.athae.skillsandclasses.comp.ICap;
import com.athae.skillsandclasses.comp.PlayerCapabilities;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;

public class SyncPlayerCapToClient extends MyPacket<SyncPlayerCapToClient> {

    public String capid;
    public CompoundTag nbt;

    public SyncPlayerCapToClient() {

    }

    public SyncPlayerCapToClient(Player player, String capid) {
        this.nbt = PlayerCapabilities.get(player, capid)
                .serializeNBT();
        this.capid = capid;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return new ResourceLocation(Ref.MODID, "syncplayercap");
    }

    @Override
    public void loadFromData(FriendlyByteBuf tag) {
        capid = tag.readUtf(100);
        nbt = tag.readNbt();

    }

    @Override
    public void saveToData(FriendlyByteBuf tag) {
        tag.writeUtf(capid, 100);
        tag.writeNbt(nbt);

    }

    @Override
    public void onReceived(PacketContext ctx) {

        try {
            Player player = ctx.getPlayer();

            if (player.level().isClientSide) { // just an extra check

                ICap cap = PlayerCapabilities.get(player, capid);
                if (cap != null) {
                    cap.deserializeNBT(nbt);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public MyPacket<SyncPlayerCapToClient> newInstance() {
        return new SyncPlayerCapToClient();
    }
}