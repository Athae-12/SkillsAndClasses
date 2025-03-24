package com.athae.skillsandclasses.items;

import net.minecraft.world.item.ItemStack;

public class GearItemData {
    private final ItemStack itemStack;

    public GearItemData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

    public static GearItemData fromItemStack(ItemStack itemStack) {
        return new GearItemData(itemStack);
    }
}