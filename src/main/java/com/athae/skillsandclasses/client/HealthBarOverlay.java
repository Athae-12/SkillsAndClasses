package com.athae.skillsandclasses.client;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "skillsandclasses", value = Dist.CLIENT)
public class HealthBarOverlay {

    private static final Identifier HEALTH_BAR_TEXTURE = new Identifier("skillsandclasses", "textures/gui/health_bar.png");

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.HEALTH) {
            return;
        }

        Minecraft minecraft = Minecraft.getInstance();
        PlayerEntity player = minecraft.player;
        if (player == null) {
            return;
        }

        int screenWidth = event.getWindow().getScaledWidth();
        int screenHeight = event.getWindow().getScaledHeight();
        int health = (int) player.getHealth();
        int maxHealth = (int) player.getMaxHealth();

        int barWidth = 182;
        int barHeight = 5;
        int healthBarWidth = (int) ((health / (float) maxHealth) * barWidth);

        RenderSystem.setShaderTexture(0, HEALTH_BAR_TEXTURE);
        GuiGraphics.blit(event.getMatrixStack(), (screenWidth - barWidth) / 2, screenHeight - 39, 0, 0, barWidth, barHeight, barWidth, barHeight);
        GuiGraphics.blit(event.getMatrixStack(), (screenWidth - barWidth) / 2, screenHeight - 39, 0, barHeight, healthBarWidth, barHeight, barWidth, barHeight);
    }
}