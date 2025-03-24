package com.athae.skillsandclasses.capabilities;

import java.util.List;

public interface ISpellCapability {
    List<String> getSpells();
    void addSpell(String spell);
    void removeSpell(String spell);
}