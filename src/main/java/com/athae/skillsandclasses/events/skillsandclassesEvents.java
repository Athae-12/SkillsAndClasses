package com.athae.skillsandclasses.events;

import com.athae.skillsandclasses.registry.skillsandclassesRegistryType;
import com.google.gson.GsonBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.loot.LootParams;

import java.util.ArrayList;
import java.util.List;

public class skillsandclassesEvents {

    public static skillsandclassesCaller<OnEntityTick> LIVING_ENTITY_TICK = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<GrabMobAffixesEvent> GRAB_MOB_AFFIXES = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<skillsandclassesEvent> EXILE_REGISTRY_GATHER = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<PredeterminedRandomEvent> PREDETERMINED_RANDOM = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnMobExpDrop> MOB_EXP_DROP = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnMobDeath> MOB_DEATH = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnPlayerDeath> PLAYER_DEATH = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnMobKilledByPlayer> MOB_KILLED_BY_PLAYER = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnSetupLootChance> SETUP_LOOT_CHANCE = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<PlayerMineOreEvent> PLAYER_MINE_ORE = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<PlayerMineFarmableBlockEvent> PLAYER_MINE_FARMABLE = new skillsandclassesCaller<>();

    public static skillsandclassesCaller<OnDamageEntity> DAMAGE_BEFORE_CALC = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnDamageEntity> DAMAGE_AFTER_CALC = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnDamageEntity> DAMAGE_BEFORE_APPLIED = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnCheckIsDevToolsRunning> CHECK_IF_DEV_TOOLS_SHOULD_RUN = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<AfterDatabaseLoaded> AFTER_DATABASE_LOADED = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnPlayerLogin> ON_PLAYER_LOGIN = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnChestLooted> ON_CHEST_LOOTED = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<IsEntityKilledValid> IS_KILLED_ENTITY_VALID = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnRegisterToDatabase> ON_REGISTER_TO_DATABASE = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<DatapackGsonAdapterEvent> DATAPACK_GSON_ADAPTER_REGISTRY = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnProcessMapDataBlock> PROCESS_DATA_BLOCK = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<OnProcessChunkData> PROCESS_CHUNK_DATA = new skillsandclassesCaller<>();
    public static skillsandclassesCaller<GrabLibMapData> GRAB_LIB_MAP_DATA = new skillsandclassesCaller<>();

    public static class OnProcessChunkData extends skillsandclassesEvent {
        public Player p;
        public MapStructure struc;
        public ChunkPos cp;

        public OnProcessChunkData(Player p, MapStructure struc, ChunkPos cp) {
            this.p = p;
            this.struc = struc;
            this.cp = cp;
        }
    }

    public static class DatapackGsonAdapterEvent extends skillsandclassesEvent {
        public GsonBuilder b;

        public DatapackGsonAdapterEvent(GsonBuilder b) {
            this.b = b;
        }

        public void registerAdapter(GsonAdapter ada) {
            b.registerTypeAdapter(ada.getClass(), ada);
        }

    }


    public static class GrabLibMapData extends skillsandclassesEvent {
        public Level level;
        public BlockPos pos;

        public LibMapData data;

        public GrabLibMapData(Level level, BlockPos pos) {
            this.level = level;
            this.pos = pos;
        }
    }

    public static class OnEntityTick extends skillsandclassesEvent {
        public LivingEntity entity;

        public OnEntityTick(LivingEntity entity) {
            this.entity = entity;
        }
    }

    public static class IsEntityKilledValid extends OnMobDeath {
        public boolean isValid = true;

        public IsEntityKilledValid(LivingEntity mob, LivingEntity killer) {
            super(mob, killer);
        }
    }

    public static class OnChestLooted extends skillsandclassesEvent {
        public Player player;
        public LootParams ctx;
        public Container inventory;
        public BlockPos pos;

        public OnChestLooted(Player player, LootParams ctx, Container inventory, BlockPos pos) {
            this.player = player;
            this.ctx = ctx;
            this.inventory = inventory;
            this.pos = pos;
        }
    }

    public static class OnCheckIsDevToolsRunning extends skillsandclassesEvent {
        public Boolean run = false;

        public OnCheckIsDevToolsRunning() {

        }
    }

    public static class OnProcessMapDataBlock extends skillsandclassesEvent {
        public MapDataBlock dataBlock;
        public String key;
        public BlockPos pos;
        public Level world;
        public CompoundTag nbt;


        public OnProcessMapDataBlock(MapDataBlock dataBlock, String key, BlockPos pos, Level world, CompoundTag nbt) {
            this.dataBlock = dataBlock;
            this.key = key;
            this.pos = pos;
            this.world = world;
            this.nbt = nbt;
        }
    }

    public static class PlayerMineFarmableBlockEvent extends PlayerMineOreEvent {
        public List<ItemStack> droppedItems;

        public PlayerMineFarmableBlockEvent(List<ItemStack> droppedItems, BlockState state, Player player, BlockPos pos) {
            super(state, player, pos);
            this.droppedItems = droppedItems;
        }
    }

    // it's called when player mines any block that doesn't drop itself.. Not just ores
    public static class PlayerMineOreEvent extends skillsandclassesEvent {

        public BlockState state;
        public Player player;
        public BlockPos pos;
        public List<ItemStack> itemsToAddToDrop = new ArrayList<>();

        public PlayerMineOreEvent(BlockState state, Player player, BlockPos pos) {
            this.state = state;
            this.player = player;
            this.pos = pos;
        }
    }

    public static class OnPlayerLogin extends skillsandclassesEvent {

        public ServerPlayer player;

        public OnPlayerLogin(ServerPlayer player) {
            this.player = player;
        }
    }

    public static class OnRegisterToDatabase extends skillsandclassesEvent {

        public Object item;
        public skillsandclassesRegistryType type;


        public OnRegisterToDatabase(Object item, skillsandclassesRegistryType type) {
            this.item = item;
            this.type = type;
        }
    }

    public static class AfterDatabaseLoaded extends skillsandclassesEvent {

        public AfterDatabaseLoaded() {

        }
    }

    public static class OnMobExpDrop extends skillsandclassesEvent {
        public LivingEntity mobKilled;
        public float exp;

        public OnMobExpDrop(LivingEntity mobKilled, float exp) {
            this.mobKilled = mobKilled;
            this.exp = exp;
        }
    }

    public static class OnSetupLootChance extends skillsandclassesEvent {
        public LivingEntity mobKilled;
        public Player player;
        public float lootChance;

        public OnSetupLootChance(LivingEntity mobKilled, Player player, float lootChance) {
            this.mobKilled = mobKilled;
            this.player = player;
            this.lootChance = lootChance;
        }
    }

    public static class OnMobDeath extends skillsandclassesEvent {
        public LivingEntity mob;
        public LivingEntity killer;

        public OnMobDeath(LivingEntity mob, LivingEntity killer) {
            this.mob = mob;
            this.killer = killer;
        }
    }

    public static class OnPlayerDeath extends skillsandclassesEvent {
        public Player player;
        public LivingEntity killer;

        public OnPlayerDeath(Player player) {
            this.player = player;
        }
    }

    public static class OnMobKilledByPlayer extends skillsandclassesEvent {
        public LivingEntity mob;
        public Player player;

        public OnMobKilledByPlayer(LivingEntity mob, Player player) {
            this.mob = mob;
            this.player = player;
        }
    }

    public static class OnDamageEntity extends skillsandclassesEvent {
        public DamageSource source;
        public float damage;
        public LivingEntity mob;

        public OnDamageEntity(DamageSource source, float damage, LivingEntity mob) {
            this.source = source;
            this.damage = damage;
            this.mob = mob;
        }
    }
}
