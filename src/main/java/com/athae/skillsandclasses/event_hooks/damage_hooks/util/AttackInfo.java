package com.athae.skillsandclasses.event_hooks.damage_hooks.util;

            import com.athae.skillsandclasses.config.CompatConfig;
            import com.athae.skillsandclasses.util.DamageSourceDuck;
            import com.athae.skillsandclasses.util.Load;
            import com.athae.skillsandclasses.util.StackSaving;
            import com.athae.skillsandclasses.util.WeaponFinderUtil;
            import com.google.common.base.Preconditions;
            import com.athae.skillsandclasses.events.skillandclassesEvents;
            import com.athae.skillsandclasses.entity.data.EntityData;
            import com.athae.skillsandclasses.items.GearItemData;
            import net.minecraft.world.damagesource.DamageSource;
            import net.minecraft.world.entity.Entity;
            import net.minecraft.world.entity.LivingEntity;
            import net.minecraft.world.item.ItemStack;

            public class AttackInfo {
                private skillandclassesEvents.OnDamageEntity event;
                Mitigation mitigation;
                boolean canceled = false;
                LivingEntity targetEntity;
                LivingEntity attackerEntity;
                DamageSource damageSource;
                float amount;

                public ItemStack weapon;
                public GearItemData weaponData;

                public AttackInfo(skillandclassesEvents.OnDamageEntity event, Mitigation miti, LivingEntity target, DamageSource source, float amount) {
                    this.targetEntity = target;
                    this.damageSource = source;
                    this.amount = amount;
                    this.mitigation = miti;
                    this.event = event;
                    this.weapon = WeaponFinderUtil.getWeapon((LivingEntity)source.getEntity(), source.getDirectEntity());
                    this.weaponData = (GearItemData) StackSaving.GEARS.loadFrom(this.weapon);
                    if (source instanceof DamageSourceDuck) {
                        DamageSourceDuck duck = (DamageSourceDuck)source;
                        duck.setOriginalHP(amount);
                    }
                    Preconditions.checkArgument((boolean)(source.getEntity() instanceof LivingEntity));
                    this.attackerEntity = (LivingEntity)source.getEntity();
                }

                public DamageSource getSource() {
                    return this.damageSource;
                }

                public float getAmount() {
                    return this.amount;
                }

                public void setAmount(float f) {
                    this.amount = f;
                    if (this.event != null) {
                        this.event.damage = f;
                        this.event.canceled = !(f > 0.0f);
                    }
                }

                public void setToMinimalNonZero() {
                    this.setAmount(0.001f);
                }

                public LivingEntity getTargetEntity() {
                    return this.targetEntity;
                }

                public LivingEntity getAttackerEntity() {
                    return this.attackerEntity;
                }

                public EntityData getAttackerEntityData() {
                    return Load.Unit((Entity)this.attackerEntity);
                }

                public EntityData getTargetEntityData() {
                    return Load.Unit((Entity)this.targetEntity);
                }

                public void setCanceled(boolean canceled) {
                    if (CompatConfig.get().damageSystem().overridesDamage) {
                        this.canceled = canceled;
                    }
                }

                public static enum Mitigation {
                    PRE,
                    POST;
                }
            }