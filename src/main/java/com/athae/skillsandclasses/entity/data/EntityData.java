package com.athae.skillsandclasses.entity.data;

import com.athae.skillsandclasses.capabilities.DirtySync;
import com.athae.skillsandclasses.capabilities.INeededForClient;
import com.athae.skillsandclasses.comp.ICap;
import com.athae.skillsandclasses.event_hooks.events.CachedEntityStats;
import com.athae.skillsandclasses.events.DamageEvent;
import com.athae.skillsandclasses.skillsandclassesRef;
import com.athae.skillsandclasses.unit.Unit;
import net.minecraft.ChatFormatting;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.HoverEvent;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.TamableAnimal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;


public class EntityData implements ICap, INeededForClient {

    public static final ResourceLocation RESOURCE = new ResourceLocation(skillsandclassesRef.MODID, "entity_data");
    public static Capability<EntityData> INSTANCE = CapabilityManager.get(new CapabilityToken<>() {
    });

    public static EntityData get(LivingEntity entity) {
        return entity.getCapability(INSTANCE)
                .orElse(null);
    }

    transient final LazyOptional<EntityData> supp = LazyOptional.of(() -> this);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (cap == INSTANCE) {
            return supp.cast();
        }

        return LazyOptional.empty();

    }

    public EntityData(LivingEntity entity) {
        this.entity = entity;
        this.equipmentCache = new CachedEntityStats(entity);
    }

    public CachedEntityStats equipmentCache;


    private static final String RARITY = "rarity";
    private static final String LEVEL = "level";
    private static final String EXP = "exp";
    private static final String EXP_DEBT = "ex_d";
    private static final String HP = "hp";
    private static final String UUID = "uuid";
    private static final String SET_MOB_STATS = "set_mob_stats";
    private static final String NEWBIE_STATUS = "is_a_newbie";
    private static final String AFFIXES = "affix";
    private static final String ENTITY_TYPE = "ENTITY_TYPE";
    private static final String RESOURCES_LOC = "res_loc";
    private static final String STATUSES = "statuses";
    private static final String AILMENTS = "ailments";
    private static final String COOLDOWNS = "cds";
    private static final String THREAT = "th";
    private static final String PET = "pet";
    private static final String BOSS = "boss";
    private static final String CUSTOM_STATS = "custom_stats";
    private static final String LEECH = "leech";
    private static final String MAP_ID = "mapid";
    private static final String MAP_MOB = "map_mob";

    private transient int dontSyncTicks = 0;

    public DirtySync sync = new DirtySync("endata sync", x -> syncData()) {
        @Override
        public void onSynced(Entity p) {
            super.onSynced(p);
            if (entity instanceof Player == false) {
                dontSyncTicks = 20; // let's not sync mob data too often
            }
        }

        @Override
        public void onTickTrySync(Entity p) {
            if (dontSyncTicks-- > 0) {
                return;
            }
            super.onTickTrySync(p);
        }

    };
    // public DirtySync gear = new DirtySync("gear_recalc", x -> recalcStats());


    public int immuneTicks = 0;

    public UnsavedMaxEffectStacksData maxCharges = new UnsavedMaxEffectStacksData();

    public DamageEvent lastDamageTaken = null;

    transient LivingEntity entity;

    transient EntityGears gears = new EntityGears();
    public transient HashMap<UUID, List<UUID>> mobsHit = new HashMap<>();

    public SummonedPetData summonedPetData = new SummonedPetData();

    public String mapUUID = "";


    public int lastHealth = 0;
    public int heartsWithoutMnsHealth = 0;

    // sync these for mobs
    transient Unit unit = new Unit();
    String rarity = IRarity.COMMON_ID;
    int level = 1;
    int exp = 0;
    int expDebt = 0;
    int maxHealth = 0;
    MobData affixes = new MobData();
    public EntityStatusEffectsData statusEffects = new EntityStatusEffectsData();
    public EntityAilmentData ailments = new EntityAilmentData();
    CooldownsData cooldowns = new CooldownsData();
    ThreatData threat = new ThreatData();
    public EntityLeechData leech = new EntityLeechData();

    public boolean didStatCalcThisTickForPlayer = false;


    EntityTypeUtils.EntityClassification type = EntityTypeUtils.EntityClassification.PLAYER;
    // sync these for mobs

    boolean setMobStats = false;
    String uuid = "";
    boolean isNewbie = true;

    ResourcesData resources = new ResourcesData();
    CustomExactStatsData customExactStats = new CustomExactStatsData();


    @Override
    public void addClientNBT(CompoundTag nbt) {

        try {
            nbt.putInt(LEVEL, level);
            nbt.putString(RARITY, rarity);
            nbt.putInt(HP, maxHealth);
            nbt.putString(ENTITY_TYPE, this.type.toString());

            if (affixes != null) {
                LoadSave.Save(affixes, nbt, AFFIXES);
            }
            LoadSave.Save(statusEffects, nbt, STATUSES);

            if (unit != null && entity instanceof Player) {
                UnitNbt.Save(nbt, unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void loadFromClientNBT(CompoundTag nbt) {

        try {
            this.rarity = nbt.getString(RARITY);
            this.level = nbt.getInt(LEVEL);
            if (level < 1) {
                level = 1;
            }
            this.maxHealth = nbt.getInt(HP);

            try {
                String typestring = nbt.getString(ENTITY_TYPE);
                this.type = EntityTypeUtils.EntityClassification.valueOf(typestring);
            } catch (Exception e) {
                this.type = EntityTypeUtils.EntityClassification.OTHER;
            }

            this.affixes = LoadSave.Load(MobData.class, new MobData(), nbt, AFFIXES);
            if (affixes == null) {
                affixes = new MobData();
            }

            this.statusEffects = loadOrBlank(EntityStatusEffectsData.class, new EntityStatusEffectsData(), nbt, STATUSES, new EntityStatusEffectsData());

            if (entity instanceof Player) {
                this.unit = UnitNbt.Load(nbt);
            }
            if (this.unit == null) {
                this.unit = new Unit();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public CompoundTag serializeNBT() {
        CompoundTag nbt = new CompoundTag();

        addClientNBT(nbt);

        try {
            nbt.putInt(EXP, exp);
            nbt.putInt(EXP_DEBT, expDebt);
            nbt.putString(UUID, uuid);
            nbt.putString(MAP_ID, this.mapUUID);
            nbt.putBoolean(SET_MOB_STATS, setMobStats);
            nbt.putBoolean(NEWBIE_STATUS, this.isNewbie);
            LoadSave.Save(cooldowns, nbt, COOLDOWNS);
            // LoadSave.Save(ailments, nbt, AILMENTS);
            LoadSave.Save(summonedPetData, nbt, PET);
            LoadSave.Save(leech, nbt, LEECH);
            LoadSave.Save(customExactStats, nbt, CUSTOM_STATS);


            if (customExactStats != null) {
                CustomExactStats.Save(nbt, customExactStats);
            }

            if (resources != null) {
                LoadSave.Save(resources, nbt, RESOURCES_LOC);
            }

            if (threat != null) {
                LoadSave.Save(threat, nbt, THREAT);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        return nbt;

    }

    public static <OBJ> OBJ loadOrBlank(Class theclass, OBJ newobj, CompoundTag nbt, String loc, OBJ blank) {
        try {
            OBJ data = LoadSave.Load(theclass, newobj, nbt, loc);
            if (data == null) {
                return blank;
            } else {
                return data;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return blank;
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {

        try {
            loadFromClientNBT(nbt);

            this.exp = nbt.getInt(EXP);
            this.expDebt = nbt.getInt(EXP_DEBT);
            this.uuid = nbt.getString(UUID);
            this.mapUUID = nbt.getString(MAP_ID);
            this.setMobStats = nbt.getBoolean(SET_MOB_STATS);

            if (nbt.contains(NEWBIE_STATUS)) {
                this.isNewbie = nbt.getBoolean(NEWBIE_STATUS);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            this.summonedPetData = loadOrBlank(SummonedPetData.class, new SummonedPetData(), nbt, PET, new SummonedPetData());
            // this.ailments = loadOrBlank(EntityAilmentData.class, new EntityAilmentData(), nbt, AILMENTS, new EntityAilmentData());
            this.threat = loadOrBlank(ThreatData.class, new ThreatData(), nbt, THREAT, new ThreatData());
            this.customExactStats = loadOrBlank(CustomExactStatsData.class, new CustomExactStatsData(), nbt, CUSTOM_STATS, new CustomExactStatsData());
            this.resources = loadOrBlank(ResourcesData.class, new ResourcesData(), nbt, RESOURCES_LOC, new ResourcesData());
            this.cooldowns = loadOrBlank(CooldownsData.class, new CooldownsData(), nbt, COOLDOWNS, new CooldownsData());
            this.leech = loadOrBlank(EntityLeechData.class, new EntityLeechData(), nbt, LEECH, new EntityLeechData());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public void setAllDirtyOnLoginEtc() {
        setEquipsChanged();
        sync.setDirty();
        didStatCalcThisTickForPlayer = true; // temp fix, somehow the stat calc is being called before everything is set to dirty even on login?
        if (entity instanceof Player p) {
            Load.player(p).cachedStats.setAllDirty();
            Load.player(p).playerDataSync.setDirty();
        }

        //this.recalcStats_DONT_CALL();
    }

    public void setEquipsChanged() {
        this.equipmentCache.setAllDirty();
    }

    public CooldownsData getCooldowns() {
        return cooldowns;
    }

    public EntityStatusEffectsData getStatusEffectsData() {
        return this.statusEffects;
    }

    public float getMaximumResource(ResourceType type) {

        if (type == ResourceType.blood) {
            return getUnit().bloodData().getValue();
        } else if (type == ResourceType.mana) {
            return getUnit().manaData().getValue();
        } else if (type == ResourceType.health) {
            return getUnit().healthData().getValue();
        } else if (type == ResourceType.energy) {
            return getUnit().energyData().getValue();
        } else if (type == ResourceType.magic_shield) {
            return getUnit().magicShieldData().getValue();
        }
        return 0;

    }

    public void onDeathDoPenalty() {

        if (entity instanceof Player p) {
            int loss = (int) (getExpRequiredForLevelUp() * ServerContainer.get().EXP_LOSS_ON_DEATH.get());
            int debt = (int) (getExpRequiredForLevelUp() * ServerContainer.get().EXP_DEBT_ON_DEATH.get());


            int maxdebt = (int) (getExpRequiredForLevelUp() * ServerContainer.get().MAX_EXP_DEBT_MULTI.get());
            this.expDebt += debt;
            if (debt > maxdebt) {
                expDebt = maxdebt;
            }


            loss = MathHelper.clamp(loss, 0, exp);

            if (loss > 0) {
                this.exp = MathHelper.clamp(exp - loss, 0, Integer.MAX_VALUE);
            }


            p.sendSystemMessage(Chats.DEATH_EXP_LOSS_MSG.locName(loss, debt).withStyle(ChatFormatting.RED));

        }
    }

    public void setType() {
        this.type = EntityTypeUtils.getType(entity);
    }

    public EntityTypeUtils.EntityClassification getType() {
        return this.type;
    }

    public ThreatData getThreat() {
        return threat;
    }


    public Unit getUnit() {
        return unit;
    }

    public void unarmedAttack(AttackInformation data) {
        float cost = ServerContainer.get().UNARMED_ENERGY_COST.get().floatValue();

        cost = Energy.getInstance().scale(ModType.FLAT, cost, getLevel());


        SpendResourceEvent event = new SpendResourceEvent(entity, null, ResourceType.energy, cost);
        event.calculateEffects();


        if (data.getAttackerEntity() instanceof Player p && PlayerUTIL.isFake(p)) {
            // this is a bit jank but it solves 2 things: fake players not having energy to attack, and fake players not having stats because they dont tick
            // and stats are calc on tick..
            Load.Unit(p).equipmentCache.setAllDirty();
        } else {
            if (event.data.getNumber() > resources.getEnergy()) {
                data.setCanceled(true);
                return;
            }
        }

        event.Activate();

        WeaponTypes weptype = WeaponTypes.none;

        int num = (int) data.getAttackerEntityData()
                .getUnit()
                .getCalculatedStat(WeaponDamage.getInstance())
                .getValue();

        if (num > 0) {
            DamageEvent dmg = EventBuilder.ofDamage(data, data.getAttackerEntity(), data.getTargetEntity(), num)
                    .setupDamage(AttackType.hit, weptype, PlayStyle.STR)
                    .setIsBasicAttack()
                    .build();
            dmg.data.setBoolean(EventData.UNARMED_ATTACK, true);
            dmg.Activate();
        } else {
            data.setAmount(0);
            data.setCanceled(true);
        }
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
        this.sync.setDirty();
        this.equipmentCache.setAllDirty();
    }

    @Override
    public void syncToClient(Player player) {


    }

    private void syncData() {
        if (entity.level().isClientSide) {
            return;
        }
        if (entity instanceof Player p) {
            Packets.sendToClient(p, new EntityUnitPacket(p));
        } else {
            if (Unit.shouldSendUpdatePackets(entity)) {
                Packets.sendToTracking(Unit.getUpdatePacketFor(entity, this), entity);
            }
        }
    }


    public String getRarity() {
        return rarity;
    }

    public String getUUID() {
        return uuid;
    }

    public MobRarity getMobRarity() {
        if (ExileDB.MobRarities().isRegistered(rarity)) {
            return ExileDB.MobRarities().get(rarity);
        }
        return ExileDB.MobRarities().get(IRarity.COMMON_ID);
    }

    public void setUUID(UUID id) {
        uuid = id.toString();
    }

    public MutableComponent getName() {

        if (entity instanceof Player) {
            return ExileText.emptyLine().get()
                    .append(entity.getDisplayName());

        } else {

            MobRarity rarity = getMobRarity();

            ChatFormatting rarformat = rarity.textFormatting();

            MutableComponent name = ExileText.emptyLine().get().append(entity.getDisplayName())
                    .withStyle(rarformat);

            String icons = "";

            for (MobAffix x : getAffixData().getAffixes()) {
                icons += CLOC.translate(x.locName());
            }
            if (!icons.isEmpty()) {
                icons += " ";
            }

            MutableComponent finalName = ExileText.ofText(icons).get().append(
                    name);

            MutableComponent part = ExileText.emptyLine().get()
                    .append(finalName)
                    .withStyle(rarformat);

            MutableComponent tx = (part);

            return tx;

        }
    }

    public void recalcStats_DONT_CALL() {

        if (this.entity.level().isClientSide()) {
            return;
        }


        if (unit == null) {
            unit = new Unit();
        }

        if (entity instanceof Player p && didStatCalcThisTickForPlayer) {
            if (MMORPG.RUN_DEV_TOOLS) {
                //   p.sendSystemMessage(Component.literal("Stats Skipped because done this tick already!").withStyle(ChatFormatting.GREEN));
            }
            return;
        }

        int oldhp = this.maxHealth;

        //Watch watch = new Watch();
        this.unit = new Unit();

        var stats = StatCalculation.getStatsWithoutSuppGems(entity, this);

        StatCalculation.calc(unit, stats, entity, null, -1);

        if (entity instanceof Player p) {
            this.didStatCalcThisTickForPlayer = true;

            var data = Load.player(p);

            data.cachedStats.allStatsWithoutSuppGems = stats;

            data.setSpellUnitsDirty();

            Load.player(p).spellCastingData.calcSpellLevels(unit);
            Load.player(p).getSkillGemInventory().removeAurasIfCantWear(p);

            UnequipGear.check(p);

            data.getSkillGemInventory().removeSupportGemsIfTooMany(p);
            data.getJewels().checkRemoveJewels(p);

            this.maxCharges.calc(this.unit.getStats());

            this.sync.setDirty();

        } else {
            if (true || oldhp != maxHealth) {
                this.sync.setDirty();
            }
        }

        this.maxHealth = (int) getUnit().getCalculatedStat(Health.getInstance()).getValue();
        //watch.print("stat calc for " + (entity instanceof PlayerEntity ? "player " : "mob "));

        HealthUtils.addHearts(entity);
    }


    public boolean canUseWeapon(GearItemData weaponData) {
        return weaponData != null;
    }

    public void onLogin(Player player) {

        try {

            if (unit == null) {
                unit = new Unit();
            }

            // check if newbie
            if (isNewbie()) {
                setNewbieStatus(false);

                resources.restore(entity, ResourceType.energy, 100);
                resources.restore(entity, ResourceType.mana, 100);

                if (ServerContainer.get().GET_STARTER_ITEMS.get()) {
                    OnLogin.GiveStarterItems(player);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getSyncedMaxHealth() {
        return this.maxHealth;
    }


    public CustomExactStatsData getCustomExactStats() {
        return this.customExactStats;
    }

    public ResourcesData getResources() {
        return this.resources;
    }

    public float getCurrentMana() {
        return this.resources.getMana();
    }

    public void mobStatsAreSet() {
        this.setMobStats = true;
    }

    public void attackWithWeapon(AttackInformation data) {

        if (data.weaponData.GetBaseGearType().getWeaponMechanic() != null) {

            GearSlot slot = data.weaponData.GetBaseGearType().getGearSlot();

            float cost = Energy.getInstance().scale(ModType.FLAT, slot.weapon_data.energy_cost_per_mob_attacked, getLevel());

            if (!Load.Unit(entity).cooldowns.isOnCooldown("swing_cost")) {
                Load.Unit(entity).cooldowns.setOnCooldown("swing_cost", 3);
                cost += Energy.getInstance().scale(ModType.FLAT, slot.weapon_data.energy_cost_per_swing, getLevel());
            }

            SpendResourceEvent event = new SpendResourceEvent(entity, null, ResourceType.energy, cost);
            event.calculateEffects();

            if (data.getAttackerEntity() instanceof Player p && PlayerUTIL.isFake(p)) {
                // this is a bit jank but it solves 2 things: fake players not having energy to attack, and fake players not having stats because they dont tick
                // and stats are calc on tick..
                Load.Unit(p).equipmentCache.setAllDirty();
                Load.Unit(p).recalcStats_DONT_CALL();
            } else {
                if (event.data.getNumber() > resources.getEnergy()) {
                    data.setCanceled(true);
                    return;
                }
            }

            event.Activate();

            data.weaponData.GetBaseGearType().getWeaponMechanic().attack(data);

        } else {
            data.setCanceled(true);
        }
    }


    public float getMobBaseDamage() {

        float multi = (float) (ServerContainer.get().VANILLA_MOB_DMG_AS_EXILE_DMG.get().floatValue());

        float num = 8 * multi;

        num = StatScaling.MOB_DAMAGE.scale(num, getLevel());

        return num;
    }

    public void mobBasicAttack(AttackInformation data) {


        if (this.cooldowns.isOnCooldown("basic_attack")) {
            data.setCanceled(true);
            return;
        }

        cooldowns.setOnCooldown("basic_attack", 5);


        float multi = (float) (ServerContainer.get().VANILLA_MOB_DMG_AS_EXILE_DMG.get().floatValue());

        float num = (float) ((data.getAmount() * CompatConfig.get().mobPercentBonusDamage() / 100f) + CompatConfig.get().mobFlatDmg());

        num *= multi;


        PlayStyle style = PlayStyle.STR;

        if (data.getSource() != null && data.getSource().is(DamageTypeTags.IS_PROJECTILE)) {
            style = PlayStyle.DEX;
        }

        num = StatScaling.MOB_DAMAGE.scale(num, getLevel()); // this should be scaled last

        DamageEvent dmg = EventBuilder.ofDamage(data, entity, data.getTargetEntity(), num)
                .setupDamage(AttackType.hit, WeaponTypes.none, style)
                .setIsBasicAttack()
                .build();

        dmg.Activate();

    }

    public boolean isNewbie() {
        return isNewbie;
    }

    public void setNewbieStatus(boolean bool) {
        isNewbie = bool;
    }

    public boolean needsToBeGivenStats() {
        return this.setMobStats == false;
    }

    public int getExpRequiredForLevelUp() {
        return LevelUtils.getExpRequiredForLevel(this.getLevel() + 1);
    }

    public EntityGears getCurrentGears() {
        return gears;
    }

    public MobData getAffixData() {
        return affixes;
    }


    public void SetMobLevelAtSpawn(Player nearestPlayer) {
        this.setMobStats = true;


        setMobLvlNormally(entity, nearestPlayer);

    }

    private void setMobLvlNormally(LivingEntity entity, Player nearestPlayer) {

        LevelInfo lvl = LevelUtils.determineLevel(entity, entity.level(), entity.blockPosition(), nearestPlayer, true);

        this.setLevel(lvl.getLevel());
    }

    public int GiveExp(Player player, int i, LootModifiersList mods) {
        if (player.isDeadOrDying()) {
            return i;
        }
        if (expDebt > 0) {
            int reduced = MathHelper.clamp(i / 2, 0, expDebt);
            i -= reduced;
            this.expDebt -= reduced;
        }
        var rested = Load.player(player).rested_xp;

        rested.onGiveCombatExp(i);

        if (rested.bonusCombatExp > 0) {
            int added = MathHelper.clamp(rested.bonusCombatExp, 0, i);
            rested.bonusCombatExp -= added;
            i += added;
        }

        setExp(exp + i);

        float perc = MathHelper.clamp(1.0f * exp / getExpRequiredForLevelUp() * 100F, 0.0f, 100.0f);
        var msg = Gui.EXP_GAIN_PERCENT.locName(i, "", NumberUtils.singleDigitFloat(perc)).withStyle(ChatFormatting.GREEN);

        if (mods != null) {
            if (Load.player(player).config.isConfigEnabled(PlayerConfigData.Config.EXP_CHAT_MESSAGES)) {
                msg.withStyle(Style.EMPTY.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, mods.getHoverText())));
                player.sendSystemMessage(msg);
            }
        }
        OnScreenMessageUtils.actionBar((ServerPlayer) player, msg);

        if (exp >= this.getExpRequiredForLevelUp()) {
            if (this.CheckIfCanLevelUp() && this.CheckLevelCap()) {
                this.LevelUp(player);
            }
            return i;
        }
        return i;
    }

    public boolean isSummon() {
        return entity instanceof TamableAnimal && !this.summonedPetData.isEmpty();
    }

    public TamableAnimal getSummonClass() {
        return (TamableAnimal) entity;
    }

    public boolean CheckIfCanLevelUp() {

        return getExp() >= getExpRequiredForLevelUp();

    }

    public int getRemainingExp() {
        int num = getExp() - getExpRequiredForLevelUp();

        if (num < 0) {
            num = 0;
        }
        return num;
    }

    public boolean CheckLevelCap() {
        return getLevel() + 1 <= GameBalanceConfig.get().MAX_LEVEL;
    }

    public boolean LevelUp(Player player) {

        if (!CheckIfCanLevelUp()) {
            player.displayClientMessage(Chats.Not_enough_experience.locName(), false);
        } else if (!CheckLevelCap()) {
            player.displayClientMessage(Chats.Can_not_go_over_maximum_level.locName(), false);
        }

        if (CheckIfCanLevelUp() && CheckLevelCap()) {

            if (player instanceof ServerPlayer) {
                //ModCriteria.PLAYER_LEVEL.trigger((ServerPlayerEntity) player);
            }

            // fully restore on lvlup

            getResources().restore(player, ResourceType.mana, Integer.MAX_VALUE);
            getResources().restore(player, ResourceType.health, Integer.MAX_VALUE);
            getResources().restore(player, ResourceType.blood, Integer.MAX_VALUE);

            // fully restore on lvlup

            setExp(getRemainingExp());
            this.setLevel(level + 1);


            OnScreenMessageUtils.sendLevelUpMessage(player, Words.LEVEL_UP_TYPE_PLAYER.locName(), level - 1, level);

            return true;
        }
        return false;
    }

    public int getLevel() {
        return level;
    }


    public void setLevel(int lvl) {
        level = Mth.clamp(lvl, 1, GameBalanceConfig.get().MAX_LEVEL);

        if (entity instanceof Player p) {
            p.resetStat(Stats.CUSTOM.get(PlayerStats.LEVELS_GAINED));
            p.awardStat(Stats.CUSTOM.get(PlayerStats.LEVELS_GAINED), lvl);
        }

        this.equipmentCache.setAllDirty();
        this.sync.setDirty();
    }


    public int getExp() {
        return exp;
    }

    public void setExp(int exp) {
        this.exp = exp;
    }

    public LivingEntity getEntity() {
        return entity;
    }


    public void onSpellHitTarget(Entity spellEntity, LivingEntity target) {

        UUID id = target.getUUID();

        UUID key = spellEntity.getUUID();

        if (!mobsHit.containsKey(key)) {
            mobsHit.put(key, new ArrayList<>());
        }
        mobsHit.get(key)
                .add(id);

        if (mobsHit.size() > 1000) {
            mobsHit.clear();
        }

    }


    public boolean alreadyHit(Entity spellEntity, LivingEntity target) {
        // this makes sure piercing projectiles hit target only once and then pass through
        // i can replace this with an effect that tags them too

        UUID key = spellEntity.getUUID();

        if (!mobsHit.containsKey(key)) {
            return false;
        }
        return mobsHit.get(key)
                .contains(target.getUUID());
    }


    @Override
    public String getCapIdForSyncing() {
        return "entity_data";
    }

}