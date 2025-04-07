package com.athae.skillsandclasses.data;

import com.athae.skillsandclasses.Skillsandclasses;
import com.athae.skillsandclasses.enumClasses.ModType;
import com.athae.skillsandclasses.interfaces.ISerializable;
import com.athae.skillsandclasses.registry.skillsandclassesDB;
import com.google.gson.JsonObject;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.List;


public class StatMod implements ISerializable<StatMod> {

    public float min = 0;
    public float max = 0;

    public String stat;
    public String type;

    public static StatMod EMPTY = new StatMod();

    private StatMod() {

    }


    public static StatMod percent(float firstMin, float firstMax, Stat stat) {
        StatMod mod = new StatMod();
        mod.min = firstMin;
        mod.max = firstMax;
        mod.stat = stat.GUID();
        if (!stat.IsPercent()) {
            mod.type = ModType.PERCENT.name();
        } else {
            mod.type = ModType.FLAT.name();
        }
        return mod;
    }

    public StatMod(float firstMin, float firstMax, Stat stat) {
        this.min = firstMin;
        this.max = firstMax;
        this.stat = stat.GUID();
        this.type = ModType.FLAT.name();
    }

    public StatMod(float firstMin, float firstMax, String stat, ModType type) {
        this.min = firstMin;
        this.max = firstMax;
        this.stat = stat;
        this.type = type.name();
    }

    public StatMod(float firstMin, float firstMax, Stat stat, ModType type) {

        this.min = firstMin;
        this.max = firstMax;
        this.stat = stat.GUID();
        this.type = type.name();
    }

    public StatMod(float firstMin, float firstMax, float secondMin, float secondMax, String stat, ModType type) {

        this.min = firstMin;
        this.max = firstMax;
        this.stat = stat;
        this.type = type.name();
    }

    public Stat GetStat() {
        return skillsandclassesDB.Stats()
                .get(stat);
    }

    public MutableComponent getRangeToShow(int lvl) {


        float fmin = min;
        float fmax = max;

        fmin = GetStat().scale(getModType(), min, lvl);
        fmax = GetStat().scale(getModType(), max, lvl);

        Boolean perc = GetStat().IsPercent() || getModType().isPercent();
        String ptext = perc ? "%" : "";

        String text = Skillsandclasses.formatNumber(fmin) + ptext + " -> " + Skillsandclasses.formatNumber(fmax) + ptext;

        if (!perc && getModType() == ModType.MORE) {
            if (fmin > 0) {
                text = text + " " + GetStat().getMultiUseType().prefixWord.locName().getString();
            } else {
                text = text + " " + GetStat().getMultiUseType().prefixLessWord.locName().getString();
            }
        }

        return ExileText.ofText("(").get().withStyle(ChatFormatting.GREEN)
                .append(text)
                .append(")");

    }

    public ModType getModType() {
        return ModType.fromString(type);
    }

    public StatMod percent() {
        //if (!this.GetStat().IsPercent()) {
        this.type = ModType.PERCENT.name();
        // }
        return this;
    }

    public StatMod more() {
        this.type = ModType.MORE.name();
        return this;
    }

    @Override
    public JsonObject toJson() {

        JsonObject json = new JsonObject();

        json.addProperty("min", min);
        json.addProperty("max", max);

        json.addProperty("stat", stat);
        json.addProperty("type", ModType.valueOf(type).id);

        return json;
    }

    @Override
    public StatMod fromJson(JsonObject json) {

        float firstMin = json.get("min")
                .getAsFloat();
        float firstMax = json.get("max")
                .getAsFloat();

        String stat = json.get("stat")
                .getAsString();

        ModType type = ModType.fromString(json.get("type")
                .getAsString());

        return new StatMod(firstMin, firstMax, stat, type);

    }

    public List<Component> getEstimationTooltip(int lvl) {

        List<Component> list = new ArrayList<>();

        if (GetStat().is_long) {
            return TooltipUtils.cutIfTooLong(ExileText.ofText(
                    StatNameRegex.JUST_NAME.translate(ChatFormatting.GREEN, null, getModType(), min, GetStat())
            ).get());
        }
        var c = new TooltipStatWithContext(new TooltipStatInfo(this.ToExactStat(100, lvl), 100, new StatRangeInfo(ModRange.of(new MinMax(0, 100)))), this, lvl);
        c.showNumber = false;
        c.disablestatranges = true;

        Component add = StatTooltipType.NORMAL.impl.getTooltipList(null, c).get(0);

        Component txt = getRangeToShow(lvl).append(" ").append(add);


        list.add(txt);

        var info = new StatRangeInfo(ModRange.hide());
        if (info.shouldShowDescriptions()) {
            list.addAll(GetStat().getCutDescTooltip());
        }

        return list;
    }

    public ExactStatData ToExactStat(int percent, float lvl) {
        return ExactStatData.fromStatModifier(this, percent, lvl);
    }

}
