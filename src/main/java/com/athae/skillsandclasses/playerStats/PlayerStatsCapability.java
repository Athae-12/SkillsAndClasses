package com.athae.skillsandclasses.playerStats;

import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber
public class PlayerStatsCapability {
    public static final Capability<PlayerStats> STATS_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

    @SubscribeEvent
    public static void attachCapabilities(AttachCapabilitiesEvent<Player> event) {
        event.addCapability(ResourceKey.create(ForgeRegistries.Keys.CAPABILITIES, new ResourceLocation("skillsandclasses", "player_stats")), new CapabilityProvider<>(STATS_CAPABILITY, null));
    }

    public static class CapabilityProvider<T> implements ICapabilityProvider {
        private final LazyOptional<T> instance;

        public CapabilityProvider(Capability<T> capability, T instance) {
            this.instance = LazyOptional.of(() -> instance);
        }

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == STATS_CAPABILITY ? instance.cast() : LazyOptional.empty();
        }
    }
}
