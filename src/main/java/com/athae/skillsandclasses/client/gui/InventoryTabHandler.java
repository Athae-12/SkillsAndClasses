package com.athae.skillsandclasses.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraftforge.client.event.ScreenOpenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class InventoryTabHandler {

    @SubscribeEvent
    public static void onScreenOpen(ScreenOpenEvent event) {
        if (event.getScreen() instanceof InventoryScreen || event.getScreen() instanceof CreativeModeInventoryScreen) {
            InventoryScreen inventoryScreen = (InventoryScreen) event.getScreen();
            inventoryScreen.addRenderableWidget(new Button(inventoryScreen.getGuiLeft() + 120, inventoryScreen.getGuiTop() + 10, 20, 20, Component.literal("S"), button -> {
                Minecraft.getInstance().setScreen(new SkillsScreen());
            }));
        }
    }
}