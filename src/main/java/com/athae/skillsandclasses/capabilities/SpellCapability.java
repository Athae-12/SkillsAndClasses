package com.athae.skillsandclasses.capabilities;

import java.util.ArrayList;
import java.util.List;

public class SpellCapability implements ISpellCapability {
    private final List<String> spells = new ArrayList<>();

    @Override
    public List<String> getSpells() {
        return spells;
    }

    @Override
    public void addSpell(String spell) {
        if (!spells.contains(spell)) {
            spells.add(spell);
        }
    }

    @Override
    public void removeSpell(String spell) {
        spells.remove(spell);
    }
}