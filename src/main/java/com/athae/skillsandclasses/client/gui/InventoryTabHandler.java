package com.athae.skillsandclasses.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class InventoryTabHandler extends Screen {

    public InventoryTabHandler() {
        super(Component.literal("Custom GUI"));
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics); // Render background first

        // Render text in the center
        drawCenteredString(guiGraphics, this.font, Component.literal("Hello, GUI!"), this.width / 2, this.height / 2, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    private void drawCenteredString(GuiGraphics guiGraphics, Font font, Component text, int x, int y, int color) {
        int width = font.width(text);
        guiGraphics.drawString(font, text, x - width / 2, y, color);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
