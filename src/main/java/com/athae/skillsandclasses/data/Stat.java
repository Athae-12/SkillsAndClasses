package com.athae.skillsandclasses.data;

import com.athae.skillsandclasses.unit.Unit;
import com.athae.skillsandclasses.datapacks.BaseDatapackStat;
import com.athae.skillsandclasses.interfaces.IAutoLocDesc;
import com.athae.skillsandclasses.interfaces.IAutoLocName;
import com.athae.skillsandclasses.interfaces.IStatEffect;
import com.athae.skillsandclasses.interfaces.IWeighted;
import com.athae.skillsandclasses.localization.Words;
import com.athae.skillsandclasses.registry.IGUID;
import com.athae.skillsandclasses.registry.JsonRegistry;
import com.athae.skillsandclasses.registry.skillsandclassesRegistryType;
import com.athae.skillsandclasses.resources.Elements;
import com.athae.skillsandclasses.skillsandclassesRef;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.List;

public abstract class Stat extends IAutoLocDesc implements IGUID, IAutoLocName, IWeighted, JsonRegistry<BaseDatapackStat> {

    public static String VAL1 = "[VAL1]";
    static ChatFormatting FORMAT = ChatFormatting.GRAY;
    static ChatFormatting NUMBER = ChatFormatting.GREEN;

    public static String format(String str) {

        str = FORMAT + str;
        if (str.contains(VAL1 + "%")) {
            str = str.replace(VAL1 + "%", NUMBER + VAL1 + "%" + FORMAT);
        } else {
            str = str.replace(VAL1, NUMBER + VAL1 + FORMAT);
        }

        return str;
    }

    public Stat() {

    }


    // can't serialize
    public transient IStatEffect statEffect = null;
    public transient IStatCtxModifier statContextModifier;

    public float min = -1000;
    public float max = STATICS.MAX_FLOAT;
    private float softcap = 0;
    public boolean has_softcap = false;
    public float base = 0;
    public boolean is_perc = false;
    public StatScaling scaling = StatScaling.NONE;
    public boolean is_long = false;
    public String icon = UNICODE.STAR;
    public int order = 100;
    public String format = ChatFormatting.AQUA.getName();
    public StatGroup group = StatGroup.Misc;
    private MultiUseType multiUseType = MultiUseType.MULTIPLY_STAT;
    public boolean minus_is_good = false; // so stats like minus mana cost dont have nasty red tooltip
    public boolean show_in_gui = true;


    public StatGuiGroup gui_group = StatGuiGroup.NONE;

    public void setSoftCap(float cap) {
        this.has_softcap = true;
        this.softcap = cap;
    }

    public boolean hasSoftCap() {
        return has_softcap;
    }

    public final float getHardCap() {
        return this.max;
    }

    public final String getHardCapTooltipText() {
        if (max >= STATICS.MAX_FLOAT) {
            return "Inf";
        }
        return ((int) this.max) + "";
    }

    public final String getMinCapTooltipText() {
        if (min <= Integer.MIN_VALUE) {
            return "Inf";
        }
        return ((int) this.min) + "";
    }

    public final float getSoftCap(Unit data) {
        return softcap + getAdditionalMax(data);
    }

    public final float getCap(Unit data) {
        if (this.hasSoftCap()) {
            return softcap + getAdditionalMax(data);
        }
        return this.getHardCap();
    }

    public final float getDefaultSoftCap() {
        return softcap;
    }

    public float getAdditionalMax(Unit data) {
        return 0;
    }

    public void setUsesMoreMultiplier() {
        this.multiUseType = MultiUseType.MULTIPLICATIVE_DAMAGE;
    }

    public MultiUseType getMultiUseType() {
        if (this.statEffect instanceof BaseDamageIncreaseEffect) {
            this.multiUseType = MultiUseType.MULTIPLICATIVE_DAMAGE;
            return MultiUseType.MULTIPLICATIVE_DAMAGE;
        }
        return multiUseType;
    }

    public enum MultiUseType {
        // todo will this still be confusing
        MULTIPLY_STAT(Words.MULTIPLICATIVE_DAMAGE_MORE, Words.MULTIPLICATIVE_DAMAGE_LESS),
        MULTIPLICATIVE_DAMAGE(Words.MULTIPLICATIVE_DAMAGE_MORE, Words.MULTIPLICATIVE_DAMAGE_LESS);

        public Words prefixWord;
        public Words prefixLessWord;

        MultiUseType(Words prefixWord, Words prefixLessWord) {
            this.prefixWord = prefixWord;
            this.prefixLessWord = prefixLessWord;
        }

    }


    public ChatFormatting getFormat() {
        return ChatFormatting.getByName(format);
    }

    public String getIconNameFormat() {
        return getIconNameFormat(locNameForLangFile());
    }

    public String getFormatAndIcon() {
        return getFormat() + icon;
    }

    public String getIconNameFormat(String str) {
        return this.getFormat() + this.icon + " " + str + ChatFormatting.GRAY;
    }

    public MutableComponent getMutableIconNameFormat() {
        return Formatter.ICON_AND_DAMAGE_IN_SPELL_DAMAGE_PROPORTION.locName(this.getFormat() + this.icon, this.locName());
    }

    @Override
    public boolean isFromDatapack() {
        return false;
    }

    @Override
    public boolean isRegistryEntryValid() {
        return true;
    }

    public StatNameRegex getStatNameRegex() {
        return StatNameRegex.BASIC;
    }

    public final StatScaling getScaling() {
        return scaling;
    }

    public final float scale(ModType mod, float stat, float lvl) {
        if (mod.isFlat()) {
            return getScaling().scale(stat, lvl);
        }
        return stat;
    }

    public List<MutableComponent> getCutDescTooltip() {
        List<MutableComponent> list = new ArrayList<>();

        List<Component> cut = TooltipUtils.cutIfTooLong(locDesc());

        for (int i = 0; i < cut.size(); i++) {

            MutableComponent comp = ExileText.emptyLine().get();
            if (i == 0) {
                comp.append(" [");
            }
            comp.append(cut.get(i));

            if (i == cut.size() - 1) {
                comp.append("]");
            }

            list.add(comp);

            comp.withStyle(ChatFormatting.BLUE);

        }
        return list;
    }

    // this is used for alltraitmods, check if confused
    @Override
    public int Weight() {
        return 1000;
    }

    @Override
    public skillsandclassesRegistryType getskillsandclassesRegistryType() {
        return skillsandclassesRegistryType.STAT;
    }

    public static ResourceLocation MISSING_ICON = new ResourceLocation(skillsandclassesRef.MODID, "textures/gui/stat_icons/missing.png");
    public static ResourceLocation DEFAULT_ICON = new ResourceLocation(skillsandclassesRef.MODID, "textures/gui/stat_icons/default.png");

    public ResourceLocation getIconLocation() {
        return new ResourceLocation(skillsandclassesRef.MODID, "textures/gui/stat_icons/" + group.id + "/" + GUID() + ".png");
    }

    transient ResourceLocation cachedIcon = null;

    transient Boolean exists = null;

    public ResourceLocation getIconForRendering() {
        if (cachedIcon == null) {
            ResourceLocation id = getIconLocation();
            if (ClientTextureUtils.textureExists(id)) {
                cachedIcon = id;
            } else {
                cachedIcon = Stat.MISSING_ICON;
            }
        }
        return cachedIcon;
    }

    public ResourceLocation getIconForRenderingWithDefault() {
        if (exists == null) {
            ResourceLocation id = getIconLocation();
            this.exists = ClientTextureUtils.textureExists(id);
        }
        return exists ? getIconForRendering() : DEFAULT_ICON;
    }

    public ResourceLocation getIconForRenderingInGroup() {
        if (this.gui_group.isValid()) {
            return this.getElement().getIconLocation(); // todo will i use other ways to group render stats?
        }

        var icon = getIconForRendering();
        if (icon.equals(MISSING_ICON)) {
            return DEFAULT_ICON;
        }
        return icon;
    }

    public ChatFormatting getStatGuiTooltipNumberColor(StatData data) {
        return ChatFormatting.YELLOW;
    }

    public int getStatGuiPanelButtonYSize() {
        return gui_group.isValid() ? StatPanelButton.ySize + StatIconAndNumberButton.ySize : StatPanelButton.ySize;
    }

    @Override
    public AutoLocGroup locDescGroup() {
        return AutoLocGroup.Stats;
    }

    @Override
    public String locNameLangFileGUID() {
        return skillsandclassesRef.MODID + ".stat." + GUID();
    }

    @Override
    public String locDescLangFileGUID() {
        return skillsandclassesRef.MODID + ".stat_desc." + GUID();
    }

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Stats;
    }

    public boolean IsPercent() {
        return is_perc;
    }

    public abstract Elements getElement();

    public List<MutableComponent> getTooltipList(TooltipStatWithContext info) {
        return info.statinfo.tooltipInfo.statTooltipType.impl.getTooltipList(null, info);
    }

    public StatMod mod(float v1, float v2) {
        return new StatMod(v1, v2, this);
    }

    public enum StatGroup {
        MAIN("main"),
        WEAPON("weapon"),
        CORE("core"),
        ELEMENTAL("elemental"),
        RESTORATION("restoration"),
        Misc("misc");

        public String id;

        StatGroup(String id) {
            this.id = id;
        }
    }

}