package com.athae.skillsandclasses.data;

import com.athae.skillsandclasses.data.adders.ModEffects;
import com.athae.skillsandclasses.registry.IGUID;
import com.athae.skillsandclasses.resources.Elements;
import com.athae.skillsandclasses.util.Load;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;

import java.util.Objects;

public class EffectCtx extends AutoHashClass implements IGUID {

    public EffectType type;
    public String resourcePath;
    public String id;
    public Elements element;
    public String locname;

    public ResourceLocation getEffectLocation() {
        return new ResourceLocation(SlashRef.MODID, resourcePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public ExileEffectInstanceData getData(LivingEntity en) {
        var data = Load.Unit(en).getStatusEffectsData();
        var effect = ExileDB.ExileEffects().get(id);
        var result = data.get(effect);
        return result;
    }

    public EffectCtx(String id, String locname, Elements element, EffectType type) {
        this.id = id;
        this.resourcePath = id;
        this.element = element;
        this.locname = locname;
        this.type = type;

        ModEffects.ALL.add(this);
    }

    @Override
    public String GUID() {
        return resourcePath;
    }
}