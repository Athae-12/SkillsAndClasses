package com.athae.skillsandclasses.interfaces;

import com.athae.skillsandclasses.registry.RegistryType;
import net.minecraftforge.eventbus.api.IEventBus;

public interface IRegistry {
    RegistryType getRegistryType();
    void loadClass();
    void registerAll(IEventBus eventBus);
}