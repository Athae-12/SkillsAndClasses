package com.athae.skillsandclasses.util;

import com.athae.skillsandclasses.items.GearItemData;
import net.minecraft.world.item.ItemStack;

public class StackSaving {
    public static final StackSaving GEARS = new StackSaving();

    public GearItemData loadFrom(ItemStack itemStack) {
        // Implement logic to load GearItemData from ItemStack
        return GearItemData.fromItemStack(itemStack);
    }
}