package com.athae.skillsandclasses;

import com.athae.skillsandclasses.capabilities.SpellCapabilityProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID)
public class CapabilityEventHandler {

    @SubscribeEvent
    public static void onRegisterCapabilities(RegisterCapabilitiesEvent event) {
        event.register(SpellCapabilityProvider.class);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<Player> event) {
        event.addCapability(new ResourceLocation(Skillsandclasses.MODID, "spells"), new SpellCapabilityProvider());
    }

    @SubscribeEvent
    public static void onPlayerClone(PlayerEvent.Clone event) {
        if (event.isWasDeath()) {
            event.getOriginal().getCapability(SpellCapabilityProvider.SPELL_CAPABILITY).ifPresent(oldStore -> {
                event.getEntity().getCapability(SpellCapabilityProvider.SPELL_CAPABILITY).ifPresent(newStore -> {
                    newStore.getSpells().addAll(oldStore.getSpells());
                });
            });
        }
    }
}