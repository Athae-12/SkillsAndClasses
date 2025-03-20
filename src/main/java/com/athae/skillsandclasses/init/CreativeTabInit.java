package com.athae.skillsandclasses.init;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class CreativeTabInit {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, "skillsandclasses");

    public static final RegistryObject<CreativeModeTab> SKILLS_AND_CLASSES_TAB = TABS.register("skills_and_classes_tab",
            () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.skills_and_classes_tab"))
                    .icon(() -> ModItems.POTION_OF_TRUE_SELF.get().getDefaultInstance()) // Set the icon of the tab
                    .displayItems((parameters, output) -> {
                        output.accept(ModItems.POTION_OF_TRUE_SELF.get()); // Add items to the tab
                    })
                    .build()
    );
}