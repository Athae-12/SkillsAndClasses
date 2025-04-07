package com.athae.skillsandclasses.resources;

import com.athae.skillsandclasses.Classes.StatContainer.StatData;
import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatPriority;
import com.athae.skillsandclasses.events.EventData;
import com.athae.skillsandclasses.interfaces.EffectSides;
import net.minecraftforge.common.data.ExistingFileHelper;

public abstract class BaseRegenClass extends Stat {

    @Override
    public String locDescForLangFile() {
        return "Restores this much every few seconds.";
    }

    public BaseRegenClass() {

        this.statEffect = new BaseRegenEffect() {

            @Override
            public EffectSides Side() {
                return EffectSides.Source;
            }

            @Override
            public StatPriority GetPriority() {
                return StatPriority.Spell.FIRST;
            }

            @Override
            public RestoreResourceEvent activate(RestoreResourceEvent effect, StatData data, Stat stat) {
                effect.data.getNumber(EventData.NUMBER).number += data.getValue();
                return effect;
            }

            @Override
            public boolean canActivate(RestoreResourceEvent effect, StatData data, Stat stat) {
                return effect.data.getResourceType() == getResourceType() && effect.data.getRestoreType() == RestoreType.regen;
            }
        };
    }

    @Override
    public Elements getElement() {
        return null;
    }

    @Override
    public boolean IsPercent() {
        return false;
    }

    public abstract ExistingFileHelper.ResourceType getResourceType();
}
