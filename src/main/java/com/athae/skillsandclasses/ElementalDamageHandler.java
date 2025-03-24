package com.athae.skillsandclasses;

import com.athae.skillsandclasses.playerStats.PlayerStats;
import com.athae.skillsandclasses.playerStats.PlayerStatsCapability;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.resources.ResourceKey;

@Mod.EventBusSubscriber
public class ElementalDamageHandler {
    @SubscribeEvent
    public static void onPlayerDamage(LivingDamageEvent event) {
        if (event.getEntity() instanceof Player player) {
            PlayerStats stats = player.getCapability(PlayerStatsCapability.STATS_CAPABILITY).orElse(null);
            if (stats != null) {
                if (event.getSource() == ModDamageSources.FIRE) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getFireDefense())));
                } else if (event.getSource() == ModDamageSources.WATER) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getWaterDefense())));
                } else if (event.getSource() == ModDamageSources.LIGHTNING) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getLightningDefense())));
                } else if (event.getSource() == ModDamageSources.LIGHT) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getLightDefense())));
                } else if (event.getSource() == ModDamageSources.EARTH) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getEarthDefense())));
                } else if (event.getSource() == ModDamageSources.DARK) {
                    event.setAmount((float) (event.getAmount() * (1 - stats.getDarkDefense())));
                }
            }
        }
    }
}
