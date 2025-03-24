package com.athae.skillsandclasses;

                    import net.minecraft.core.Holder;
                    import net.minecraft.resources.ResourceLocation;
                    import net.minecraft.world.damagesource.DamageSource;
                    import net.minecraft.world.damagesource.DamageType;
                    import net.minecraft.world.damagesource.DamageScaling;
                    import net.minecraft.world.damagesource.DamageEffects;
                    import net.minecraft.world.damagesource.DeathMessageType;
                    import net.minecraft.world.entity.LivingEntity;
                    import net.minecraftforge.registries.DeferredRegister;
                    import net.minecraftforge.registries.ForgeRegistries;
                    import net.minecraftforge.registries.RegistryObject;

                    public class ModDamageSources {

                        // Register DamageTypes using Deferred Register
                        public static final DeferredRegister<DamageType> DAMAGE_TYPES = DeferredRegister.create(ForgeRegistries.Keys.DAMAGE_TYPES, Skillsandclasses.MODID);

                        // Register custom DamageTypes with ResourceLocation
                        public static final RegistryObject<DamageType> FIRE_TYPE = DAMAGE_TYPES.register("fire", () -> new DamageType("fire", DamageScaling.ALWAYS, 0.1f, DamageEffects.BURNING, DeathMessageType.DEFAULT));
                        public static final RegistryObject<DamageType> WATER_TYPE = DAMAGE_TYPES.register("water", () -> new DamageType("water", DamageScaling.ALWAYS, 0.1f, DamageEffects.DROWNING, DeathMessageType.DEFAULT));
                        public static final RegistryObject<DamageType> LIGHTNING_TYPE = DAMAGE_TYPES.register("lightning", () -> new DamageType("lightning", DamageScaling.ALWAYS, 0.1f, DamageEffects.ELECTROCUTION, DeathMessageType.DEFAULT));
                        public static final RegistryObject<DamageType> LIGHT_TYPE = DAMAGE_TYPES.register("light", () -> new DamageType("light", DamageScaling.ALWAYS, 0.1f, DamageEffects.MAGIC, DeathMessageType.DEFAULT));
                        public static final RegistryObject<DamageType> EARTH_TYPE = DAMAGE_TYPES.register("earth", () -> new DamageType("earth", DamageScaling.ALWAYS, 0.1f, DamageEffects.CRUSHING, DeathMessageType.DEFAULT));
                        public static final RegistryObject<DamageType> DARK_TYPE = DAMAGE_TYPES.register("dark", () -> new DamageType("dark", DamageScaling.ALWAYS, 0.1f, DamageEffects.MAGIC, DeathMessageType.DEFAULT));

                        // Create DamageSource instances
                        public static DamageSource FIRE;
                        public static DamageSource WATER;
                        public static DamageSource LIGHTNING;
                        public static DamageSource LIGHT;
                        public static DamageSource EARTH;
                        public static DamageSource DARK;

                        // Initialize DamageSources with Holder<DamageType>
                        public static void initialize() {
                            // Convert RegistryObject<DamageType> to Holder<DamageType>
                            FIRE = createDamageSource(FIRE_TYPE);
                            WATER = createDamageSource(WATER_TYPE);
                            LIGHTNING = createDamageSource(LIGHTNING_TYPE);
                            LIGHT = createDamageSource(LIGHT_TYPE);
                            EARTH = createDamageSource(EARTH_TYPE);
                            DARK = createDamageSource(DARK_TYPE);
                        }

                        // Method to create DamageSource from RegistryObject<DamageType>
                        private static DamageSource createDamageSource(RegistryObject<DamageType> damageTypeRegistryObject) {
                            // Convert RegistryObject to Holder<DamageType>
                            Holder<DamageType> damageTypeHolder = damageTypeRegistryObject.getHolder().orElseThrow();
                            return new DamageSource(damageTypeHolder);
                        }

                        // Method to apply Damage with custom DamageSource
                        public static void applyElementalDamage(LivingEntity entity, DamageSource damageSource, float damageAmount) {
                            entity.hurt(damageSource, damageAmount);
                        }

                        // Example of applying fire damage in your mod
                        public static void applyFireDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, FIRE, damageAmount);
                        }

                        // Similarly for other damage types
                        public static void applyWaterDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, WATER, damageAmount);
                        }

                        public static void applyLightningDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, LIGHTNING, damageAmount);
                        }

                        public static void applyLightDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, LIGHT, damageAmount);
                        }

                        public static void applyEarthDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, EARTH, damageAmount);
                        }

                        public static void applyDarkDamage(LivingEntity entity, float damageAmount) {
                            applyElementalDamage(entity, DARK, damageAmount);
                        }
                    }