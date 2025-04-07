package com.athae.skillsandclasses.interfaces;

import com.athae.skillsandclasses.Classes.StatContainer.StatData;
import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatPriority;
import com.athae.skillsandclasses.events.EffectEvent;

public interface IStatEffect {


    public boolean worksOnEvent(EffectEvent ev);

    public abstract EffectSides Side();

    public abstract StatPriority GetPriority();

    public abstract void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat);

}
