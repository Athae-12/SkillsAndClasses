package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.interfaces.ISerializable;
import com.google.common.base.Preconditions;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.fml.ModList;

import java.util.*;
import java.util.stream.Collectors;

public class skillsandclassesRegistryType {

    private static HashMap<String, skillsandclassesRegistryType> map = new HashMap<>();

    public String id;
    ISerializable ser;
    int order;
    public SyncTime syncTime;
    public String modid;
    // used for lang file tc
    public String idWithoutModid;

    public skillsandclassesRegistryType(String modid, String id, int order, ISerializable ser, SyncTime synctime) {

        Preconditions.checkNotNull(modid);
        Preconditions.checkNotNull(id);
        Preconditions.checkNotNull(order);
        Preconditions.checkNotNull(synctime);

        this.modid = modid;
        this.idWithoutModid = id;
        this.id = modid + "_" + id;
        this.order = order;
        this.ser = ser;
        this.syncTime = synctime;
    }

    public String getModName() {
        return ModList.get().getModContainerById(modid).get().getModInfo().getDisplayName();
    }

    public static skillsandclassesRegistryType get(String id) {
        return map.get(id);
    }

    public static skillsandclassesRegistryType register(skillsandclassesRegistryType type) {
        Preconditions.checkNotNull(type);

        if (map.containsKey(type.id)) {
            skillsandclassesLog.get().warn("Duplicate skillandclassesRegistryType: " + type.id);
        }
        map.put(type.id, type);

        return type;
    }

    public static skillsandclassesRegistryType register(String modid, String id, int order, ISerializable ser, SyncTime synctime) {
        skillsandclassesRegistryType type = new skillsandclassesRegistryType(modid, id, order, ser, synctime);
        return register(type);
    }

    public static List<skillsandclassesRegistryType> getInRegisterOrder(SyncTime sync) {
        List<skillsandclassesRegistryType> list = map.values().stream()
                .filter(x -> x.syncTime == sync)
                .collect(Collectors.toList());
        list.sort(Comparator.comparingInt(x -> x.order));
        return list;

    }

    public static List<skillsandclassesRegistryType> getAllInRegisterOrder() {
        List<skillsandclassesRegistryType> list = new ArrayList<>();

        for (Map.Entry<String, skillsandclassesRegistryType> en : map.entrySet()) {
            if (en.getValue() == null) {
                throw new RuntimeException(en.getKey() + " is a null registry type, how?!");
            } else {
                list.add(en.getValue());
            }
        }
        list.sort(Comparator.comparingInt(x -> {
            return x.order;
        }));
        return list;
    }


    public static void registerJsonListeners(AddReloadListenerEvent manager) {
        List<skillsandclassesRegistryType> list = getAllInRegisterOrder();
        list.forEach(x -> {
            if (x.getLoader() != null) {
                manager.addListener(x.getLoader());
            }
        });
    }


    public BaseDataPackLoader getLoader() {
        if (this.ser == null) {
            return null;
        }
        return new BaseDataPackLoader(this, this.id, this.ser);
    }

    public DatapackGenerator getDatapackGenerator() {
        return new DatapackGenerator<>(modid, getAllForSerialization(), this.id);
    }


    public List getAllForSerialization() {
        return Database.ExileRegistryType(this)
                .getSerializable();
    }

    public final ISerializable getSerializer() {
        return ser;
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return obj.hashCode() == this.hashCode();
    }
}
