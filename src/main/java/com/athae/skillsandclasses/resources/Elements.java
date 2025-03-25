package com.athae.skillsandclasses.resources;

import com.athae.skillsandclasses.tags.ElementTags;
import com.athae.skillsandclasses.registry.IGUID;
import com.athae.skillsandclasses.skillsandclassesRef;
import com.athae.skillsandclasses.tags.SpellTags;
import com.athae.skillsandclasses.tags.TagList;
import com.athae.skillsandclasses.tags.imp.ElementTag;
import com.athae.skillsandclasses.tags.imp.SpellTag;
import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// todo for now keep lightning/nature mostly the same, or revert the rename
// shadow will be chaos and need a few new priest abilities or turn a few to holy
// todo
public enum Elements implements IGUID {

    Physical(Arrays.asList(), Arrays.asList(ElementTags.PHYSICAL), "Physical", ChatFormatting.GOLD, "physical", "\u2600", SpellTags.PHYSICAL),
    // elementals
    Fire(Arrays.asList(), Arrays.asList(ElementTags.ELEMENTAL), "Fire", ChatFormatting.RED, ElementID.FIRE, "\u2600", SpellTags.FIRE),
    Cold(Arrays.asList(), Arrays.asList(ElementTags.ELEMENTAL), "Cold", ChatFormatting.AQUA, ElementID.WATER, "\u2600", SpellTags.COLD),
    Nature(Arrays.asList(), Arrays.asList(ElementTags.ELEMENTAL), "Lightning", ChatFormatting.YELLOW, ElementID.LIGHTNING, "\u2600", SpellTags.LIGHTNING),
    Earth(Arrays.asList(), Arrays.asList(ElementTags.ELEMENTAL), "Earth", ChatFormatting.DARK_GREEN, ElementID.EARTH, "\u2600", SpellTags.EARTH),
    // alignments
    Shadow(Arrays.asList(), Arrays.asList(ElementTags.ALIGNMENT), "Shadow", ChatFormatting.DARK_PURPLE, "shadow", "\u2600", SpellTags.SHADOW),
    Holy(Arrays.asList(), Arrays.asList(ElementTags.ALIGNMENT), "Holy", ChatFormatting.YELLOW, "holy", "\u2600", SpellTags.HOLY),
    // multi ele
    Elemental(Arrays.asList(ElementID.LIGHTNING, ElementID.FIRE, ElementID.WATER, ElementID.EARTH), Arrays.asList(ElementTags.ELEMENTAL), "Elemental", ChatFormatting.LIGHT_PURPLE, "elemental", "\u2600", SpellTags.PHYSICAL),

    ALL(Arrays.asList(ElementID.LIGHTNING, ElementID.FIRE, ElementID.WATER, Shadow.GUID()), Arrays.asList(), "", ChatFormatting.LIGHT_PURPLE, "all", "\u2600", SpellTags.PHYSICAL);

    public SpellTag spellTag;

    public List<String> multiElements = new ArrayList<>();
    public TagList<ElementTag> tags = new TagList();

    Elements(List<String> multiElements, List<ElementTag> tags, String dmgname, ChatFormatting format, String guidname, String icon, SpellTag tag) {

        this.multiElements = multiElements;
        this.tags.addAll(tags);
        this.dmgName = dmgname;
        this.format = format;
        this.guidName = guidname;
        this.icon = icon;
        this.spellTag = tag;
    }

    public String dmgName;
    public String guidName;
    public String icon;

    public ChatFormatting format;


    public boolean isValid() {
        return this != ALL;
    }

    public boolean shouldShowInStatPanel() {
        return this != ALL && this != Elemental;
    }

    public boolean isSingleElement() {
        return this.multiElements.isEmpty();
    }

    public String getIconNameDmg() {
        return getIconNameFormat(dmgName) + " Damage";
    }

    public String getIconNameDmgWithSpecialColor(ChatFormatting color) {
        return getIconNameFormat(dmgName) + color + " Damage";
    }

    public String getIconNameFormat() {
        return getIconNameFormat(dmgName);
    }

    public String getIconNameFormat(String str) {
        return this.format + this.icon + " " + str + ChatFormatting.GRAY;
    }

    public ResourceLocation getIconLocation() {
        return skillsandclassesRef.guiId("stat_icons/element_icons/" + GUID());
    }

    private static List<Elements> allSingle = Arrays.stream(Elements.values()).filter(x -> x.isSingleElement() && x.isValid()).collect(Collectors.toList());
    private static List<Elements> ele = Arrays.stream(Elements.values()).filter(x -> x.tags.contains(ElementTags.ELEMENTAL) && x.isSingleElement() && x.isValid()).collect(Collectors.toList());


    public static List<Elements> getAllSingle() {
        return allSingle;
    }

    public static List<Elements> getAllSingleElemental() {
        return ele;
    }


    public boolean elementsMatch(Elements other) {
        if (other == null) {
            return false;
        }

        if (this == other) {
            return true;
        }

        if (this.isSingleElement() && !other.isSingleElement()) {
            if (other.multiElements.stream().anyMatch(x -> x.equals(this.guidName))) {
                return true;
            }
        }
        if (!this.isSingleElement() && other.isSingleElement()) {
            if (multiElements.stream().anyMatch(x -> x.equals(other.guidName))) {
                return true;
            }
        }

        return false;
    }

    @Override
    public String GUID() {
        return guidName;
    }
}
