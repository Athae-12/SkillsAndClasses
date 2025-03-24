package com.athae.skillsandclasses;

import com.athae.skillsandclasses.client.gui.SkillsScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.inventory.InventoryScreen;
import net.minecraft.client.gui.components.Button;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.ScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        // Only register MenuScreens if there's a valid MenuType
        // MenuScreens.register(Skillsandclasses.SKILLS_SCREEN.get(), SkillsScreen::new);
    }

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Init event) {
        if (event.getScreen() instanceof InventoryScreen inventoryScreen) {
            Button button = Button.builder(
                            net.minecraft.network.chat.Component.literal("Skills"),
                            btn -> Minecraft.getInstance().setScreen(new SkillsScreen())
                    )
                    .pos(inventoryScreen.width / 2 + 100, inventoryScreen.height / 2 - 20)
                    .size(50, 20)
                    .build();

            event.addListener(button);
        }
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (KeyBindingsHandler.OPEN_SKILLS_SCREEN.consumeClick() && Minecraft.getInstance().screen == null) {
            Minecraft.getInstance().setScreen(new SkillsScreen());
        }
    }
}
