package com.athae.skillsandclasses.capabilities;

    import net.minecraft.core.Direction;
    import net.minecraft.nbt.CompoundTag;
    import net.minecraftforge.common.capabilities.Capability;
    import net.minecraftforge.common.capabilities.CapabilityManager;
    import net.minecraftforge.common.capabilities.CapabilityProvider;
    import net.minecraftforge.common.capabilities.CapabilityToken;
    import net.minecraftforge.common.capabilities.ICapabilityProvider;
    import net.minecraftforge.common.capabilities.ICapabilitySerializable;
    import net.minecraftforge.common.util.LazyOptional;

    public class SpellCapabilityProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
        public static final Capability<ISpellCapability> SPELL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {});

        private final ISpellCapability instance = new SpellCapability();
        private final LazyOptional<ISpellCapability> lazyOptional = LazyOptional.of(() -> instance);
        private final SpellCapabilityStorage storage = new SpellCapabilityStorage(instance);

        @Override
        public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
            return cap == SPELL_CAPABILITY ? lazyOptional.cast() : LazyOptional.empty();
        }

        @Override
        public CompoundTag serializeNBT() {
            return storage.serializeNBT();
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            storage.deserializeNBT(nbt);
        }
    }