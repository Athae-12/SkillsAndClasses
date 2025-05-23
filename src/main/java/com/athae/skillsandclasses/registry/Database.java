package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database {

    private static final HashMap<skillsandclassesRegistryType, skillsandclassesRegistryContainer> SERVER = new HashMap<>();
    //  private static HashMap<ExileRegistryType, skillsandclassesRegistryContainer> BACKUP = new HashMap<>();

    public static boolean areDatapacksLoaded(Level world) {
        return skillsandclassesRegistryType.getInRegisterOrder(SyncTime.ON_LOGIN)
                .stream()
                .allMatch(x -> Database.ExileRegistryType(x)
                        .isRegistrationDone());
    }


    public static List<skillsandclassesRegistryContainer> getAllRegistries() {
        return new ArrayList<>(SERVER.values());
    }

    public static skillsandclassesRegistryContainer ExileRegistryType(skillsandclassesRegistryType type) {
        return SERVER.get(type);
    }

    public static skillsandclassesRegistry<T> get(skillsandclassesRegistryType type, String guid) {
        return ExileRegistryType(type).get(guid);

    }

    public static void sendPacketsToClient(ServerPlayer player, SyncTime sync) {

        List<skillsandclassesRegistryType> list = skillsandclassesRegistryType.getInRegisterOrder(sync);

        list.forEach(x -> ExileRegistryType(x).sendUpdatePacket(player));
    }

    public static void checkGuidValidity() {

        SERVER.values()
                .forEach(c -> c.getAllIncludingSeriazable()
                        .forEach(x -> {
                            skillsandclassesRegistry<T> entry = (skillsandclassesRegistry<T>) x;
                            if (!entry.isGuidFormattedCorrectly()) {
                                throw new RuntimeException(entry.getInvalidGuidMessage());
                            }
                        }));

    }

    public static void unregisterInvalidEntries() {

        //System.out.println("Starting Mine and Slash Registry auto validation.");

        List<skillsandclassesRegistry<T>> invalid = new ArrayList<>();

        SERVER.values()
                .forEach(c -> c.getList()
                        .forEach(x -> {
                            skillsandclassesRegistry<T> entry = (skillsandclassesRegistry<T>) x;
                            if (!entry.isRegistryEntryValid()) {
                                invalid.add(entry);
                            }
                        }));

        invalid.forEach(x -> x.unregisterDueToInvalidity());

        if (invalid.isEmpty()) {
            skillsandclassesLog.get().debug("All Mine and Slash registries appear valid.");
        } else {
            skillsandclassesLog.get().warn(invalid.size() + " Mine and Slash entries are INVALID!");
        }

    }

    public static void addRegistry(skillsandclassesRegistryContainer cont) {
        SERVER.put(cont.getType(), cont);
    }
}
