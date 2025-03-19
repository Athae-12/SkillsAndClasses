package com.athae.skillsandclasses.init;

import com.athae.skillsandclasses.items.PotionOfTrueSelf;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, "skillsandclasses");

    public static final RegistryObject<Item> POTION_OF_TRUE_SELF = ITEMS.register("potion_of_true_self",
            () -> new PotionOfTrueSelf(new Item.Properties().stacksTo(1)));
}