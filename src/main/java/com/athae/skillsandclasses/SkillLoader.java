package com.athae.skillsandclasses;

import com.athae.skillsandclasses.Spells.Fireball;
import com.athae.skillsandclasses.Spells.Skill;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = Skillsandclasses.MODID)
public class SkillLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Map<String, Skill> SKILL_MAP = new HashMap<>();

    public SkillLoader() {
        super(GSON, "skills");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonObject> object, ResourceManager resourceManager, ProfilerFiller profiler) {
        SKILL_MAP.clear();
        object.forEach((resourceLocation, jsonObject) -> {
            String name = jsonObject.get("name").getAsString();
            int cooldown = jsonObject.get("cooldown").getAsInt();
            int manaCost = jsonObject.get("manaCost").getAsInt();
            String particleEffect = jsonObject.get("particleEffect").getAsString();
            String elementType = jsonObject.get("elementType").getAsString();
            int damage = jsonObject.get("damage").getAsInt();
            int range = jsonObject.get("range").getAsInt();
            // Create skill instance based on the type
            Skill skill = new Fireball(name, cooldown, manaCost, particleEffect, elementType, damage, range); // Example for FireballSkill
            SKILL_MAP.put(name, skill);
        });
    }

    @SubscribeEvent
    public static void onAddReloadListener(AddReloadListenerEvent event) {
        event.addListener(new SkillLoader());
    }

    public static Skill getSkill(String name) {
        return SKILL_MAP.get(name);
    }
}