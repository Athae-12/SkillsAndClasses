package com.athae.skillsandclasses.mixins;

import com.athae.skillsandclasses.events.skillsandclassesEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Inject(method = "actuallyHurt", at = @At("HEAD"))
    public void hookOnActuallyHurt(DamageSource source, float amount, CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        skillsandclassesEvents.OnDamageEntity event = new skillsandclassesEvents.OnDamageEntity(source, amount, entity);
        skillsandclassesEvents.DAMAGE_BEFORE_APPLIED.callEvents(event);
        if (event.canceled) {
            ci.cancel();
        }
    }
}