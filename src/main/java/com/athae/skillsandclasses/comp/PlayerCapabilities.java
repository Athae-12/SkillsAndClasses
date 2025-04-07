package com.athae.skillsandclasses.comp;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.event.entity.player.PlayerEvent;

import java.util.HashMap;

public class PlayerCapabilities {
    static HashMap<String, Capability<? extends ICap>> caps = new HashMap<>();

    public static void register(Capability<? extends ICap> cap, ICap obj) {
        caps.put(obj.getCapIdForSyncing(), cap);
    }

    public static void register(Capability<? extends ICap> cap, String syncid) {
        caps.put(syncid, cap);
    }

    public static void saveAllOnDeath(PlayerEvent.Clone event) {

        Player original = event.getOriginal();
        Player current = event.getEntity();

        original.reviveCaps();

        caps.values()
                .forEach(x -> {

                    try {
                        ICap data = current.getCapability(x)
                                .orElse(null);

                        ICap origcap = original.getCapability(x)
                                .orElse(null);
                        if (origcap != null) {
                            data.deserializeNBT(origcap.serializeNBT());
                            data.syncToClient(current);
                        } else {
                            skillsandclassesLog.get().log("couldn't get original player's " + x.getName() + " capability");
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });

        original.invalidateCaps();

    }

    public static void syncAllToClient(Player player) {

        try {
            caps.values()
                    .forEach(x -> {
                        player.getCapability(x)
                                .orElse(null)
                                .syncToClient(player);
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static ICap get(Player player, String id) {
        if (caps.containsKey(id)) {
            Capability cap = caps.get(id);
            return (ICap) player.getCapability(cap)
                    .orElse(null);
        }
        return null;
    }

}