package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.registry.info.FromDatapackRegistration;
import com.athae.skillsandclasses.util.Watch;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.athae.skillsandclasses.interfaces.IAutoGson;
import com.athae.skillsandclasses.interfaces.ISerializable;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.*;

public class BaseDataPackLoader<T extends skillsandclassesRegistry<T>> extends SimpleJsonResourceReloadListener {
    private static Gson GSON = IAutoGson.createGson();


    public String id;
    ISerializable<T> serializer;
    public skillsandclassesRegistryType registryType;


    public static HashMap<skillsandclassesRegistryType, List<String>> INFO_MAP = new HashMap<>();

    public BaseDataPackLoader(skillsandclassesRegistryType registryType, String id, ISerializable<T> serializer) {
        super(GSON, id);
        Objects.requireNonNull(registryType);
        this.id = id;
        this.serializer = serializer;
        this.registryType = registryType;
    }

    public enum LoaderType {
        REPLACE_FULLY, REPLACE_FIELDS, ERROR_LOADING
    }


    @Override
    protected Map<ResourceLocation, JsonElement> prepare(ResourceManager manager, ProfilerFiller profiler) {


        return super.prepare(manager, profiler);
    }

    public static String ENABLED = "enabled";
    public static String LOADER = "loader";

    String getInfoString(ResourceLocation key, LoaderType type) {
        return key.getNamespace() + ":" + key.getPath() + ":" + type.name();
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> mapToLoad, ResourceManager manager, ProfilerFiller profilerIn) {

        try {
            skillsandclassesRegistryContainer reg = Database.ExileRegistryType(registryType);

            Watch normal = new Watch();
            normal.min = 50000;

            INFO_MAP.put(registryType, new ArrayList<>());

            for (Map.Entry<ResourceLocation, JsonElement> entry : mapToLoad.entrySet()) {
                ResourceLocation key = entry.getKey();
                JsonElement value = entry.getValue();
                try {

                    JsonObject json = null;
                    T object = null;
                    try {
                        json = value.getAsJsonObject();
                        object = serializer.fromJson(json);
                    } catch (Exception e) {
                        e.printStackTrace();

                        String info = getInfoString(entry.getKey(), LoaderType.ERROR_LOADING);
                        INFO_MAP.get(registryType).add(info);

                        continue;
                    }


                    LoaderType type = LoaderType.REPLACE_FULLY;

                    if (json.has(LOADER)) {
                        try {
                            type = LoaderType.valueOf(json.get(LOADER).getAsString());
                        } catch (IllegalArgumentException e) {
                            type = LoaderType.REPLACE_FULLY;
                        }
                    }

                    if (!Database.ExileRegistryType(registryType).isExistingSeriazable(object.GUID())) {
                        //type = LoaderType.NEW;
                    } else {
                        if (type == LoaderType.REPLACE_FIELDS && Database.ExileRegistryType(registryType).isExistingSeriazable(object.GUID())) {
                            T existing = (T) Database.ExileRegistryType(registryType).get(object.GUID());
                            ISerializable<T> exSer = (ISerializable<T>) existing;
                            JsonObject existingJson = exSer.toJson();

                            for (Map.Entry<String, JsonElement> en : json.entrySet()) {
                                existingJson.add(en.getKey(), en.getValue());
                            }
                            object = this.serializer.fromJson(existingJson);
                        }
                    }

                    if (!json.has(ENABLED) || json.get(ENABLED).getAsBoolean()) {
                        object.unregisterFromExileRegistry();
                        object.registerToExileRegistry(new FromDatapackRegistration(key));

                        String infostring = getInfoString(key, type);
                        INFO_MAP.get(registryType).add(infostring);
                    }

                    if (json.has(ENABLED)) {
                        if (!json.get(ENABLED).getAsBoolean()) {
                            object.unregisterFromExileRegistry();
                        }
                    }

                    if (object != null) {
                        JsonObject compare = json.deepCopy();
                        compare.remove(ENABLED);
                        object.compareLoadedJsonAndFinalClass(compare, type == LoaderType.REPLACE_FIELDS);
                    }

                } catch (Exception exception) {
                    skillsandclassesLog.get().warn(key.toString() + " is a broken datapack entry.");
                    JsonRegistry.addToErroredJsons(registryType, key);
                    exception.printStackTrace();
                }
            }

            normal.print("Loading " + registryType.id + " jsons ");

            if (reg.isEmpty()) {
                throw new RuntimeException("Exile Registry of type " + registryType.id + " is EMPTY after datapack loading!");
            } else {
                // System.out.println(registryType.name() + " Registry succeeded loading: " + reg.getSize() + " datapack entries.");
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

    }

}
