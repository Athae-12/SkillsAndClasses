package com.athae.skillsandclasses;

import com.athae.skillsandclasses.Spells.Skill;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModSkills {
    public static final DeferredRegister<Skill> SKILLS = DeferredRegister.create(ForgeRegistries.CUSTOM_REGISTRY, Skillsandclasses.MODID);

    public static final RegistryObject<Skill> FIREBALL = SKILLS.register("fireball", FIREBALL::new);
}