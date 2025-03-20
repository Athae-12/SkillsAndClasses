package com.athae.skillsandclasses.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "skillsandclasses", value = Dist.CLIENT)
public class HealthBarOverlay {

    private static final ResourceLocation HEALTH_BAR_TEXTURE = new ResourceLocation("skillsandclasses:textures/gui/health_bar.png");

    // Instantiate the class to ensure it is registered
    public static final HealthBarOverlay INSTANCE = new HealthBarOverlay();

    @SubscribeEvent
    public static void onRenderGuiOverlay(RenderGuiOverlayEvent.Post event) {
        if (!event.getOverlay().id().equals("minecraft:health")) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        Player player = minecraft.player;
        if (player == null) {
            return;
        }

        int screenWidth = minecraft.getWindow().getGuiScaledWidth();
        int screenHeight = minecraft.getWindow().getGuiScaledHeight();
        int health = (int) player.getHealth();
        int maxHealth = (int) player.getMaxHealth();

        int barWidth = 182;
        int barHeight = 5;
        int healthBarWidth = (int) ((health / (float) maxHealth) * barWidth);

        RenderSystem.setShaderTexture(0, HEALTH_BAR_TEXTURE);
        event.getGuiGraphics().blit(HEALTH_BAR_TEXTURE, (screenWidth - barWidth) / 2, screenHeight - 39, 0, 0, barWidth, barHeight, barWidth, barHeight);
        event.getGuiGraphics().blit(HEALTH_BAR_TEXTURE, (screenWidth - barWidth) / 2, screenHeight - 39, 0, barHeight, healthBarWidth, barHeight, barWidth, barHeight);
    }
}
