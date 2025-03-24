package com.athae.skillsandclasses.Spells;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.registries.ForgeRegistries;
import com.athae.skillsandclasses.entity.data.SummonedEntityData;

import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class SummonSpell {

    private int currentLevel;
    private static final List<SummonedEntityData> summonedEntities = new ArrayList<>();

    public SummonSpell(int level) {
        this.currentLevel = level;
    }

    public void summon(ServerLevel level, double x, double y, double z, String spellName) {
        try {
            // Load the JSON file for the spell
            ResourceLocation spellResource = new ResourceLocation("skillsandclasses", "spells/" + spellName + ".json");
            InputStreamReader reader = new InputStreamReader(Files.newInputStream(Paths.get(spellResource.getPath())));
            JsonObject spellJson = JsonParser.parseReader(reader).getAsJsonObject();

            // Get the entity type from the JSON
            String entityTypeString = spellJson.get("entity").getAsJsonObject().get("type").getAsString();
            EntityType<?> entityType = ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(entityTypeString));

            // Create the entity
            LivingEntity entity = (LivingEntity) entityType.create(level);
            if (entity == null) return;
            entity.setPos(x, y, z);

            // Set the size if specified
            if (spellJson.get("entity").getAsJsonObject().has("size")) {
                float size = spellJson.get("entity").getAsJsonObject().get("size").getAsFloat();
                entity.setSize(size, size);
            }

            // Check if armor section exists and set armor based on spell level
            if (spellJson.get("entity").getAsJsonObject().has("armor")) {
                JsonObject armorJson = spellJson.get("entity").getAsJsonObject().get("armor").getAsJsonObject();
                setArmorBasedOnLevel(entity, armorJson);
            }

            // Add the entity to the level
            level.addFreshEntity(entity);

            // Store the summoned entity data
            summonedEntities.add(SummonedEntityData.fromEntity(entity));

            // Apply aura effect if present in the JSON
            if (spellJson.has("aura")) {
                JsonObject auraJson = spellJson.get("aura").getAsJsonObject();
                applyAuraEffect(level, entity, auraJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setArmorBasedOnLevel(LivingEntity entity, JsonObject armorJson) {
        for (Map.Entry<String, JsonElement> entry : armorJson.entrySet()) {
            String[] levels = entry.getKey().split("-");
            int minLevel = Integer.parseInt(levels[0]);
            int maxLevel = Integer.parseInt(levels[1]);
            if (currentLevel >= minLevel && currentLevel <= maxLevel) {
                JsonObject armorItems = entry.getValue().getAsJsonObject();
                setArmor(entity, armorItems);
                break;
            }
        }
    }

    private void setArmor(LivingEntity entity, JsonObject armorItems) {
        for (Map.Entry<String, JsonElement> entry : armorItems.entrySet()) {
            EquipmentSlot slot = getEquipmentSlot(entry.getKey());
            if (slot != null) {
                ItemStack itemStack = new ItemStack(ForgeRegistries.ITEMS.getValue(new ResourceLocation(entry.getValue().getAsString())));
                entity.setItemSlot(slot, itemStack);
            }
        }
    }

    private EquipmentSlot getEquipmentSlot(String slotName) {
        switch (slotName) {
            case "mainhand":
                return EquipmentSlot.MAINHAND;
            case "head":
                return EquipmentSlot.HEAD;
            case "chest":
                return EquipmentSlot.CHEST;
            case "legs":
                return EquipmentSlot.LEGS;
            case "feet":
                return EquipmentSlot.FEET;
            default:
                return null;
        }
    }

    private void applyAuraEffect(ServerLevel level, LivingEntity general, JsonObject auraJson) {
        float baseHealAmount = auraJson.get("base_heal_amount").getAsFloat();
        float healIncreasePerLevel = auraJson.get("heal_increase_per_level").getAsFloat();
        int interval = auraJson.get("interval").getAsInt();

        level.getServer().getScheduler().scheduleAtFixedRate(() -> {
            for (SummonedEntityData data : summonedEntities) {
                LivingEntity entity = data.createEntity(level);
                if (entity != null && entity.isAlive()) {
                    float healAmount = baseHealAmount + (currentLevel / 20) * healIncreasePerLevel;
                    entity.heal(healAmount * entity.getMaxHealth());
                }
            }
        }, 0, interval, TimeUnit.SECONDS);
    }

    public static List<SummonedEntityData> getSummonedEntities() {
        return summonedEntities;
    }
}