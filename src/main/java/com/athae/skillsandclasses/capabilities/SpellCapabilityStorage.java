package com.athae.skillsandclasses.capabilities;

    import net.minecraft.core.Direction;
    import net.minecraft.nbt.CompoundTag;
    import net.minecraft.nbt.ListTag;
    import net.minecraft.nbt.StringTag;
    import net.minecraft.nbt.Tag;
    import net.minecraftforge.common.capabilities.Capability;
    import net.minecraftforge.common.capabilities.ICapabilityProvider;
    import net.minecraftforge.common.capabilities.ICapabilitySerializable;
    import net.minecraftforge.common.util.INBTSerializable;

    public class SpellCapabilityStorage implements INBTSerializable<CompoundTag> {

        private final ISpellCapability instance;

        public SpellCapabilityStorage(ISpellCapability instance) {
            this.instance = instance;
        }

        @Override
        public CompoundTag serializeNBT() {
            ListTag tag = new ListTag();
            for (String spell : instance.getSpells()) {
                tag.add(StringTag.valueOf(spell));
            }
            CompoundTag compoundTag = new CompoundTag();
            compoundTag.put("spells", tag);
            return compoundTag;
        }

        @Override
        public void deserializeNBT(CompoundTag nbt) {
            ListTag tag = nbt.getList("spells", Tag.TAG_STRING);
            for (Tag element : tag) {
                instance.addSpell(element.getAsString());
            }
        }
    }