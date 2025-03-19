package com.athae.skillsandclasses;

import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class ElementalDamageHandler {
    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerStats stats = player.getCapability(PlayerStatsCapability.STATS_CAPABILITY).orElse(null);
            if (stats != null) {
                double elementalResist = stats.getFireAffinity();
                if (event.getSource().isFire()) {
                    event.setAmount(event.getAmount() * (1 - elementalResist)); // Reduce fire damage
                }
            }
        }
    }
}