package com.athae.skillsandclasses.capabilities;

import net.minecraft.nbt.CompoundTag;

public interface INeededForClient {
    void addClientNBT(CompoundTag nbt);

    void loadFromClientNBT(CompoundTag nbt);
}