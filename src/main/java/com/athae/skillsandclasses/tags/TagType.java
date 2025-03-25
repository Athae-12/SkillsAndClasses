package com.athae.skillsandclasses.tags;

public enum TagType {
    Effect("effect"),
    Spell("spell"),
    Element("element"),
    Mob("mob"),
    MapAffix("map_affix"),
    Dungeon("dungeon"),
    GearSlot("gear_slot");

    public String id;

    TagType(String id) {
        this.id = id;
    }
}