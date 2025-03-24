package com.athae.skillsandclasses;

import com.athae.skillsandclasses.Spells.Skill;
import com.athae.skillsandclasses.client.gui.InventoryTabHandler;
import com.athae.skillsandclasses.client.gui.SkillsScreen;
import com.athae.skillsandclasses.playerStats.PlayerStatsCapability;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.RegistryObject;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID, value = Dist.CLIENT)
public class KeyBindingsHandler {
    public static final KeyMapping[] SKILL_KEYS = new KeyMapping[9];

    static {
        for (int i = 0; i < SKILL_KEYS.length; i++) {
            SKILL_KEYS[i] = new KeyMapping("key.skillsandclasses.skill" + (i + 1), GLFW.GLFW_KEY_1 + i, "key.categories.skillsandclasses");
        }
    }

    public static final KeyMapping OPEN_SKILLS_SCREEN = new KeyMapping(
            "key.skillsandclasses.open_skills_screen",
            GLFW.GLFW_KEY_BACKSLASH,
            "key.categories.skillsandclasses"
    );

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        if (Minecraft.getInstance().player != null) {
            for (int i = 0; i < SKILL_KEYS.length; i++) {
                if (SKILL_KEYS[i].isDown()) {
                    ClientEvents.activateSkill(i);
                }
            }
            if (OPEN_SKILLS_SCREEN.consumeClick()) {
                Minecraft.getInstance().setScreen(new SkillsScreen());
            }
        }
    }

    @SubscribeEvent
    public static void registerKeyMappings(RegisterKeyMappingsEvent event) {
        for (KeyMapping key : SKILL_KEYS) {
            event.register(key);
        }
        event.register(OPEN_SKILLS_SCREEN);
    }

    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
    public static class ClientEvents {
        @SubscribeEvent
        public static void onKeyPress(InputEvent.Key event) {
            if (OPEN_SKILLS_SCREEN.isDown()) {
                Minecraft.getInstance().setScreen(new InventoryTabHandler());
            }
        }

        private static void activateSkill(int skillIndex) {
            Minecraft.getInstance().player.getCapability(PlayerStatsCapability.STATS_CAPABILITY).ifPresent(stats -> {
                ResourceLocation skillId = ModSkills.SKILLS.getEntries().stream().skip(skillIndex).findFirst().map(RegistryObject::getId).orElse(null);
                if (skillId != null) {
                    Skill skill = stats.getSkill(skillId);
                    if (skill != null) {
                        skill.activate(Minecraft.getInstance().player);
                    }
                }
            });
        }
    }
}