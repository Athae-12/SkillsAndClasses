package com.athae.skillsandclasses.client.gui;

import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack; // Import the ItemStack class

import javax.annotation.Nonnull; // Import the Nonnull annotation

public class SkillsScreenMenu extends AbstractContainerMenu {

    // Constructor for the menu
    public SkillsScreenMenu(int pContainerId, Player player) {
        super(null, pContainerId);
        // Define the layout and logic here
        // For example: add a custom container, buttons, etc.
    }

    @Override
    public boolean stillValid(@Nonnull Player player) {
        return true;
    }

    @Override
    @Nonnull
    public ItemStack quickMoveStack(@Nonnull Player player, int index) {
        // Implement the logic for quick moving stacks here
        return ItemStack.EMPTY;
    }
}