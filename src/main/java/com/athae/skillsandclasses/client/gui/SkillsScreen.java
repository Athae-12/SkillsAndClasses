package com.athae.skillsandclasses.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

public class SkillsScreen extends Screen {
    private static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("skillsandclasses", "textures/gui/skills_screen.png");
    private static final ResourceLocation CUSTOM_ELEMENT_TEXTURE = new ResourceLocation("skillsandclasses", "textures/gui/custom_element.png");

    public SkillsScreen() {
        super(Component.translatable("screen.skillsandclasses.skills"));
    }

    @Override
    protected void init() {
        super.init();
        // Add custom buttons or other widgets here
        this.addRenderableWidget(new Button.Builder(Component.literal("Click Me"), button -> {
            // Handle button click
        }).bounds(this.width / 2 - 50, this.height / 2 - 10, 100, 20).build());
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        RenderSystem.setShaderTexture(0, BACKGROUND_TEXTURE);
        int x = (this.width - 176) / 2;
        int y = (this.height - 166) / 2;
        guiGraphics.blit(BACKGROUND_TEXTURE, x, y, 0, 0, 176, 166);

        // Draw custom elements
        RenderSystem.setShaderTexture(0, CUSTOM_ELEMENT_TEXTURE);
        guiGraphics.blit(CUSTOM_ELEMENT_TEXTURE, x + 10, y + 10, 0, 0, 64, 64);

        // Draw custom text
        guiGraphics.drawCenteredString(this.font, "Skills Screen", this.width / 2, y + 10, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTicks);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}