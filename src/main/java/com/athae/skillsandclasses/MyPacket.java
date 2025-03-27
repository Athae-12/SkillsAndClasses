package com.athae.skillsandclasses;

import com.athae.skillsandclasses.packets.skillsandclassesPacketContext;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public abstract class MyPacket<T> {

    public abstract ResourceLocation getIdentifier();

    public abstract void loadFromData(FriendlyByteBuf tag);

    public abstract void saveToData(FriendlyByteBuf tag);

    public abstract void onReceived(skillsandclassesPacketContext ctx);

    public abstract MyPacket<T> newInstance();

    public final MyPacket loadFromDataUSETHIS(FriendlyByteBuf buf) {

        MyPacket<T> data = newInstance();
        try {
            data.loadFromData(buf);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return data;

    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {

        ctx.get()
                .enqueueWork(
                        () -> {
                            try {
                                onReceived(new skillsandclassesPacketContext(ctx.get()));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });

        ctx.get().setPacketHandled(true);
    }

}
