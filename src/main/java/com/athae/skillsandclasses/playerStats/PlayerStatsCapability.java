package com.athae.skillsandclasses.playerStats;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityProvider;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.common.util.LazyOptional;

public class PlayerStatsCapability {
    public static final Capability<PlayerStats> STATS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    // Capability Registration
    @SubscribeEvent
    public static void registerCapabilities(RegisterCapabilitiesEvent event) {
        event.register(PlayerStats.class);
    }

    public static class Provider extends CapabilityProvider<Provider> {
        private final PlayerStats stats = new PlayerStats();
        private final LazyOptional<PlayerStats> statsOptional = LazyOptional.of(() -> stats);

        public Provider() {
            super(PlayerStats.class);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return STATS_CAPABILITY.orEmpty(cap, statsOptional);
        }
    }
}