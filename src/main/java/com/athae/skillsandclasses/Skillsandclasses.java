package com.athae.skillsandclasses;

import com.athae.skillsandclasses.config.ClientConfigs;
import com.athae.skillsandclasses.config.Config;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import org.slf4j.Logger;
import com.athae.skillsandclasses.client.gui.SkillsScreenMenu;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.function.Consumer;

@Mod(skillsandclassesRef.MODID)
public class Skillsandclasses {
    public static boolean RUN_DEV_TOOLS = false;

    public static boolean RUN_DEV_TOOLS_REMOVE_WHEN_DONE = RUN_DEV_TOOLS; // this exists to stop me from making dumb mistakes when testing and forgetting about it

    public static final String MODID = "skillsandclasses";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MODID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, MODID);

    public static final RegistryObject<Block> EXAMPLE_BLOCK = BLOCKS.register("example_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE)));
    public static final RegistryObject<Item> EXAMPLE_BLOCK_ITEM = ITEMS.register("example_block",
            () -> new BlockItem(EXAMPLE_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<Item> EXAMPLE_ITEM = ITEMS.register("example_item", () -> new Item(new Item.Properties().food(new FoodProperties.Builder().alwaysEat().nutrition(1).saturationMod(2f).build())));

    public static final RegistryObject<CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register("example_tab", () -> CreativeModeTab.builder().withTabsBefore(CreativeModeTabs.COMBAT).icon(() -> EXAMPLE_ITEM.orElseThrow().getDefaultInstance()).displayItems((parameters, output) -> {
        EXAMPLE_ITEM.ifPresent(output::accept);
    }).build());

    public static final DeferredRegister<MenuType<?>> MENU_TYPES = DeferredRegister.create(ForgeRegistries.MENU_TYPES, MODID);
    public static final RegistryObject<MenuType<SkillsScreenMenu>> SKILLS_SCREEN_MENU_TYPE = MENU_TYPES.register("skills_screen", () -> new MenuType<>(SkillsScreenMenu::new, FeatureFlagSet.of()));

    public static void registerScreens(IEventBus eventBus) {
        MENU_TYPES.register(eventBus);
    }

    public Skillsandclasses() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModDamageSources.DAMAGE_TYPES.register(modEventBus);
        modEventBus.addListener(this::commonSetup);
        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        CREATIVE_MODE_TABS.register(modEventBus);
        MinecraftForge.EVENT_BUS.register(this);
        modEventBus.addListener(this::addCreative);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Config.SPEC);
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        registerScreens(modEventBus);
        MENU_TYPES.register(modEventBus);
        ModDamageSources.DAMAGE_TYPES.register(modEventBus);
        ModDamageEffects.DAMAGE_EFFECTS.register(modEventBus);
    }

    private void setup(final FMLClientSetupEvent event) {
        MinecraftForge.EVENT_BUS.register(new com.athae.skillsandclasses.client.HealthBarOverlay());
        MinecraftForge.EVENT_BUS.register(KeyBindingsHandler.class);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        LOGGER.info("HELLO FROM COMMON SETUP");
        LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        if (Config.logDirtBlock) LOGGER.info("DIRT BLOCK >> {}", ForgeRegistries.BLOCKS.getKey(Blocks.DIRT));
        LOGGER.info(Config.magicNumberIntroduction + Config.magicNumber);
        Config.items.forEach((item) -> LOGGER.info("ITEM >> {}", item.toString()));
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) EXAMPLE_BLOCK_ITEM.ifPresent(event::accept);
    }

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        LOGGER.info("HELLO from server starting");
    }

    @Mod.EventBusSubscriber(modid = MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {

        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            LOGGER.info("HELLO FROM CLIENT SETUP");
            LOGGER.info("MINECRAFT NAME >> {}", Minecraft.getInstance().getUser().getName());
        }
    }

    public static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("0.00");

    public static String formatNumber(float num) {
        if (num < ClientConfigs.getConfig().SHOW_DECIMALS_ON_NUMBER_SMALLER_THAN.get()) {
            return DECIMAL_FORMAT.format(num);
        } else {
            return ((int) num) + "";
        }
    }
}
