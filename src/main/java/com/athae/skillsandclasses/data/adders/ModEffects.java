package com.athae.skillsandclasses.data.adders;

import com.athae.skillsandclasses.registry.RegistryType;
import com.athae.skillsandclasses.interfaces.IRegistry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects implements IRegistry {

    @Override
    public RegistryType getRegistryType() {
        return RegistryType.EFFECT;
    }

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, "skillsandclasses");

    public static final RegistryObject<MobEffect> FROSTBITE =
            EFFECTS.register("frostbite", FrostbiteEffect::new);

    public static final RegistryObject<MobEffect> BLEED =
            EFFECTS.register("bleed", BleedEffect::new);

    public static final RegistryObject<MobEffect> VITALITY =
            EFFECTS.register("vitality", VitalityEffect::new);

    public static final RegistryObject<MobEffect> CHARM =
            EFFECTS.register("charm", CharmEffect::new);

    public static final RegistryObject<MobEffect> ENDURANCE_CHARGE =
            EFFECTS.register("endurance_charge", EnduranceChargeEffect::new);

    public static final RegistryObject<MobEffect> POWER_CHARGE =
            EFFECTS.register("power_charge", PowerChargeEffect::new);

    public static final RegistryObject<MobEffect> FRENZY_CHARGE =
            EFFECTS.register("frenzy_charge", FrenzyChargeEffect::new);

    @Override
    public void loadClass() {
        // Register effects
        FROSTBITE.toString(); // force class init
        BLEED.toString();
        VITALITY.toString();
        CHARM.toString();
        ENDURANCE_CHARGE.toString();
        POWER_CHARGE.toString();
        FRENZY_CHARGE.toString();
        }

    @Override
    public void registerAll(IEventBus eventBus) {
        EFFECTS.register(eventBus);
    }
    // ====== Effect Classes ======

    public static class FrostbiteEffect extends MobEffect {
        public FrostbiteEffect() {
            super(MobEffectCategory.HARMFUL, 0xADD8E6);
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            entity.hurt(entity.damageSources().freeze(), 1.0F + amplifier);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return duration % 40 == 0;
        }
    }

    public static class BleedEffect extends MobEffect {
        public BleedEffect() {
            super(MobEffectCategory.HARMFUL, 0x8B0000); // Dark red
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            entity.hurt(entity.damageSources().generic(), 0.5F + amplifier);
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return duration % 20 == 0;
        }
    }

    public static class VitalityEffect extends MobEffect {
        public VitalityEffect() {
            super(MobEffectCategory.BENEFICIAL, 0x00FF00); // Green
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            if (entity.getHealth() < entity.getMaxHealth()) {
                entity.heal(0.5F + (amplifier * 0.5F));
            }
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return duration % 40 == 0;
        }
    }

    public static class CharmEffect extends MobEffect {
        public CharmEffect() {
            super(MobEffectCategory.BENEFICIAL, 0xFFC0CB); // Light pink
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            // Custom charm logic (e.g., mobs don't attack you)
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
    }

    public static class EnduranceChargeEffect extends MobEffect {
        public EnduranceChargeEffect() {
            super(MobEffectCategory.BENEFICIAL, 0x808080); // Gray
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            // Reduce incoming damage or increase armor
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
    }

    public static class PowerChargeEffect extends MobEffect {
        public PowerChargeEffect() {
            super(MobEffectCategory.BENEFICIAL, 0xFF4500); // Orange red
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            // Increase outgoing damage
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
    }

    public static class FrenzyChargeEffect extends MobEffect {
        public FrenzyChargeEffect() {
            super(MobEffectCategory.BENEFICIAL, 0x7CFC00); // Lawn green
        }

        @Override
        public void applyEffectTick(LivingEntity entity, int amplifier) {
            // Increase movement and attack speed
        }

        @Override
        public boolean isDurationEffectTick(int duration, int amplifier) {
            return true;
        }
    }
}