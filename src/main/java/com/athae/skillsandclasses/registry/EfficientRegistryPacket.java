package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Ref;
import com.athae.skillsandclasses.interfaces.ISerializable;
import com.athae.skillsandclasses.packets.skillsandclassesPacketContext;
import com.athae.skillsandclasses.registry.JsonRegistry;
import com.athae.skillsandclasses.registry.info.ClientSyncRegistration;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.MyPacket;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.List;

public class EfficientRegistryPacket<T extends ISerializable & JsonRegistry> extends MyPacket<EfficientRegistryPacket> {
    public static final JsonParser PARSER = new JsonParser();

    public static ResourceLocation ID = new ResourceLocation(Ref.MODID, "eff_reg");
    private List<T> items;

    skillsandclassesRegistryType type;

    public EfficientRegistryPacket() {

    }

    public EfficientRegistryPacket(skillsandclassesRegistryType type, List<T> list) {
        this.type = type;
        this.items = list;
    }

    @Override
    public ResourceLocation getIdentifier() {
        return ID;
    }

    @Override
    public void loadFromData(FriendlyByteBuf buf) {

        this.type = skillsandclassesRegistryType.get(buf.readUtf());

        ISerializable<T> serializer = type.getSerializer();

        this.items = Lists.newArrayList();

        int i = buf.readVarInt();

        for (int j = 0; j < i; ++j) {
            JsonObject json = (JsonObject) PARSER.parse(buf.readUtf(Integer.MAX_VALUE));
            this.items.add(serializer.fromJson(json));
        }
    }

    @Override
    public void saveToData(FriendlyByteBuf buf) {

        buf.writeUtf(type.id);
        buf.writeVarInt(this.items.size());
        items.forEach(x -> {
            if (x.isFromDatapack()) {
                buf.writeUtf(x.toJsonString(), Integer.MAX_VALUE);
            }
        });
    }

    @Override
    public void onReceived(skillsandclassesPacketContext ctx) {

    }

    @Override
    public void onReceived(NetworkManager.PacketContext ctx) {

        skillsandclassesRegistryContainer reg = Database.getRegistry(type);

        items.forEach(x -> {
            x.unregisterFromExileRegistry();
            x.registerToExileRegistry(ClientSyncRegistration.INSTANCE);
        });

        skillsandclassesLog.get().onlyInConsole("Efficient " + type.id + " reg load on client success with: " + reg.getSize() + " entries.");

    }

    @Override
    public MyPacket<EfficientRegistryPacket> newInstance() {
        return new EfficientRegistryPacket();
    }
}
