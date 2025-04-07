package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.Skillsandclasses;
import com.athae.skillsandclasses.data.EntityConfig;
import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.entity.data.EntityData;
import com.athae.skillsandclasses.items.WeaponTypes;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.registries.ForgeRegistries;

public class skillsandclassesDB {

    // todo

    public static EntityConfig getEntityConfig(LivingEntity entity, EntityData data) {

        var id = ForgeRegistries.ENTITY_TYPES.getKey(entity.getType());
        String monster_id = id.toString();
        String mod_id = id.getNamespace();

        EntityConfig config = null;

        if (EntityConfigs().isRegistered(monster_id)) {
            config = EntityConfigs().get(monster_id);
            if (config != null) {
                return config;
            }
        } else {
            if (EntityConfigs().isRegistered(mod_id)) {
                config = EntityConfigs().get(mod_id);

                if (config != null) {
                    return config;
                }

            } else {
                config = EntityConfigs().get(data.getType().id);

                if (config != null) {
                    return config;
                }
            }
        }

        return EntityConfigs().getDefault();

    }

    public static skillsandclassesRegistryContainer<GearSlot> GearSlots() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.GEAR_SLOT);
    }

    public static skillsandclassesRegistryContainer<UniqueGear> UniqueGears() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.UNIQUE_GEAR);
    }

    public static skillsandclassesRegistryContainer<ExtendedOrb> OrbExtension() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.ORB_EXTEND);
    }


    /*
    public static skillsandclassesRegistryContainer<Currency> CurrencyItems() {
        return Database.getRegistry(skillsandclassesRegistryType.CURRENCY_ITEMS);
    }


     */
    public static skillsandclassesRegistryContainer<DimensionConfig> DimensionConfigs() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.DIMENSION_CONFIGS);
    }

    public static skillsandclassesRegistryContainer<StatCondition> StatConditions() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT_CONDITION);
    }


    public static skillsandclassesRegistryContainer<StatEffect> StatEffects() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT_EFFECT);
    }

    public static skillsandclassesRegistryContainer<Gem> Gems() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.GEM);
    }

    public static skillsandclassesRegistryContainer<ExileEffect> ExileEffects() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.EXILE_EFFECT);
    }


    public static skillsandclassesRegistryContainer<TalentTree> TalentTrees() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.TALENT_TREE);
    }


    public static skillsandclassesRegistryContainer<Perk> Perks() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.PERK);
    }

    public static skillsandclassesRegistryContainer<Rune> Runes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.RUNE);
    }

    public static skillsandclassesRegistryContainer<RuneWord> RuneWords() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.RUNEWORDS);
    }

    public static skillsandclassesRegistryContainer<Affix> Affixes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.AFFIX);
    }

    public static skillsandclassesRegistryContainer<GearRarity> GearRarities() {
        return (skillsandclassesRegistryContainer<GearRarity>) Database.ExileRegistryType(skillsandclassesRegistryType.GEAR_RARITY);
    }

    public static skillsandclassesRegistryContainer<MobRarity> MobRarities() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.MOB_RARITY);
    }

    public static skillsandclassesRegistryContainer<ProphecyModifier> ProphecyModifiers() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.PROPHECY_MODIFIER);
    }

    public static skillsandclassesRegistryContainer<ProphecyStart> ProphecyStarts() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.PROPHECY_START);
    }

    public static skillsandclassesRegistryContainer<BaseGearType> GearTypes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.GEAR_TYPE);
    }


    public static skillsandclassesRegistryContainer<Spell> Spells() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.SPELL);
    }

    public static skillsandclassesRegistryContainer<MobAffix> MobAffixes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.MOB_AFFIX);
    }

    public static skillsandclassesRegistryContainer<Ailment> Ailments() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.AILMENT);
    }

    public static skillsandclassesRegistryContainer<ValueCalculation> ValueCalculations() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.VALUE_CALC);
    }


    public static skillsandclassesRegistryContainer<Omen> Omens() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.OMEN);
    }


    public static skillsandclassesRegistryContainer<StatLayer> StatLayers() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT_LAYER);
    }

    public static skillsandclassesRegistryContainer<EntityConfig> EntityConfigs() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.ENTITY_CONFIGS);
    }


    public static skillsandclassesRegistryContainer<SpellSchool> SpellSchools() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.SPELL_SCHOOL);
    }


    public static skillsandclassesRegistryContainer<MapAffix> MapAffixes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.MAP_AFFIX);
    }


    public static skillsandclassesRegistryContainer<LootChest> LootChests() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.LOOT_CHEST);
    }


    public static skillsandclassesRegistryContainer<SupportGem> SupportGems() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.SUPPORT_GEM);
    }

    public static skillsandclassesRegistryContainer<Profession> Professions() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.PROFESSION);
    }

    public static skillsandclassesRegistryContainer<AutoItem> AutoItems() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.AUTO_ITEM);
    }

    public static skillsandclassesRegistryContainer<CustomItem> CustomItemGenerations() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.CUSTOM_ITEM);
    }

    public static skillsandclassesRegistryContainer<StatBuff> StatBuffs() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT_BUFF);
    }

    public static skillsandclassesRegistryContainer<ChaosStat> ChaosStats() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.CHAOS_STAT);
    }

    public static skillsandclassesRegistryContainer<ProfessionRecipe> Recipes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.RECIPE);
    }

    public static skillsandclassesRegistryContainer<WeaponTypes> WeaponTypes() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.WEAPON_TYPE);
    }


    public static skillsandclassesRegistryContainer<StatCompat> StatCompat() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT_COMPAT);
    }

    public static skillsandclassesRegistryContainer<AuraGem> AuraGems() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.AURA);
    }


    public static skillsandclassesRegistryContainer<Stat> Stats() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.STAT);
    }

    public static skillsandclassesRegistryContainer<BaseStatsConfig> BaseStats() {
        return Database.ExileRegistryType(skillsandclassesRegistryType.BASE_STATS);
    }

    public static void checkAllDatabasesHaveDefaultEmpty() {

        for (skillsandclassesRegistryType type : skillsandclassesRegistryType.getAllInRegisterOrder()) {
            var reg = Database.ExileRegistryType(type);
            var em = reg.getDefault();
            if (em == null) {
                if (Skillsandclasses.RUN_DEV_TOOLS) {
                    skillsandclassesLog.get().warn(type.id + " default is null or not registered");
                }
            }
        }
    }
}
