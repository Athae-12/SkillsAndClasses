package com.athae.skillsandclasses.entity.data;

import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class SummonedEntityData {
    private final EntityType<?> entityType;
    private final double x, y, z;
    private final Map<EquipmentSlot, ItemStack> armorItems;

    public SummonedEntityData(EntityType<?> entityType, double x, double y, double z, Map<EquipmentSlot, ItemStack> armorItems) {
        this.entityType = entityType;
        this.x = x;
        this.y = y;
        this.z = z;
        this.armorItems = armorItems;
    }

    public LivingEntity createEntity(ServerLevel level) {
        LivingEntity entity = (LivingEntity) entityType.create(level);
        if (entity != null) {
            entity.setPos(x, y, z);
            for (Map.Entry<EquipmentSlot, ItemStack> entry : armorItems.entrySet()) {
                entity.setItemSlot(entry.getKey(), entry.getValue());
            }
        }
        return entity;
    }

    public static SummonedEntityData fromEntity(LivingEntity entity) {
        Map<EquipmentSlot, ItemStack> armorItems = new HashMap<>();
        for (EquipmentSlot slot : EquipmentSlot.values()) {
            armorItems.put(slot, entity.getItemBySlot(slot));
        }
        return new SummonedEntityData(entity.getType(), entity.getX(), entity.getY(), entity.getZ(), armorItems);
    }
}