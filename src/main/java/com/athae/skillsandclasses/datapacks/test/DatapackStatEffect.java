package com.athae.skillsandclasses.datapacks.test;

import com.athae.skillsandclasses.Classes.StatContainer.StatData;
import com.athae.skillsandclasses.Log.skillsandclassesLog;
import com.athae.skillsandclasses.Skillsandclasses;
import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.data.StatPriority;
import com.athae.skillsandclasses.events.EffectEvent;
import com.athae.skillsandclasses.interfaces.EffectSides;
import com.athae.skillsandclasses.interfaces.IStatEffect;
import com.athae.skillsandclasses.localization.Words;
import com.athae.skillsandclasses.registry.skillsandclassesDB;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;

public class DatapackStatEffect implements IStatEffect {

    public String order = "";

    public EffectSides side = EffectSides.Source;

    public List<String> ifs = new ArrayList<>();

    public List<String> effects = new ArrayList<>();

    public List<String> events = new ArrayList<>();

    public boolean worksOnEvent(EffectEvent ev) {
        return events.contains(ev.GUID());
    }

    @Override
    public EffectSides Side() {
        return side;
    }


    @Override
    public List<MutableComponent> getTooltip() {
        List<MutableComponent> t = new ArrayList<>();

        // Display priority using a translatable key.
        var prio = GetPriority();
        t.add(
                Component.literal("Priority: ")
                        .append(Component.translatable(prio.locNameLangFileGUID()))
                        .append(Component.literal(" (" + prio.priority + ")"))
        );

        // Assuming side.word is of type Words, use locNameLangFileGUID() here too.
        t.add(Component.translatable(side.word.locNameLangFileGUID()));

        // For events, assuming the strings are localization keys, you can do:
        t.add(Component.translatable(Words.WorksOnEvent.locNameLangFileGUID()));
        for (String eventKey : events) {
            t.add(Component.translatable(eventKey));
        }

        // Localize conditions using the translation keys from the 'ifs' list.
        t.add(Component.translatable(Words.Conditions.locNameLangFileGUID()));
        for (String conditionKey : ifs) {
            t.add(Component.literal("- ").append(Component.translatable(conditionKey)));
        }

        // Localize effects using the translation keys from the 'effects' list.
        t.add(Component.translatable(Words.Effects.locNameLangFileGUID()));
        for (String effectKey : effects) {
            t.add(Component.literal("- ").append(Component.translatable(effectKey)));
        }

        return t;
    }



    @Override
    public StatPriority GetPriority() {
        var p = StatPriority.MAP.get(order);
        if (p == null) {
            skillsandclassesLog.get().warn("No such stat priority: " + order + ". Using default priority.");
            return StatPriority.Unknown; // Assuming you have a priority to denote an unknown state.
        }
        return p;
    }

    @Override
    public void TryModifyEffect(EffectEvent effect, EffectSides statSource, StatData data, Stat stat) {

        if (Skillsandclasses.RUN_DEV_TOOLS) {
            if (stat.GUID().equals(DefenseStats.NO_SELF_DAMAGE_STATS.getId())) {
                boolean bo = true;
            }
        }

        if (ifs.stream()
                .allMatch(x -> {
                    StatCondition cond = skillsandclassesDB.StatConditions().get(x);
                    if (cond == null) {
                        return false;
                    }
                    Boolean istrue = cond.can(effect, statSource, data, stat) == cond.getConditionBoolean();
                    return istrue;
                })) {

            effects.forEach(x -> {
                StatEffect e = skillsandclassesDB.StatEffects().get(x);
                if (e == null) {
                    return;
                }
                e.activate(effect, statSource, data, stat);
            });

        }


    }
}
