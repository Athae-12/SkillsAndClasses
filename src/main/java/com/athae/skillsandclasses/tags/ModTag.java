package com.athae.skillsandclasses.tags;

import com.athae.skillsandclasses.util.StringUTIL;
import com.athae.skillsandclasses.interfaces.IAutoLocName;
import com.athae.skillsandclasses.skillsandclassesRef;
import com.athae.skillsandclasses.registry.IGUID;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public abstract class ModTag implements IAutoLocName, IGUID {

    // todo maybe have tags use resourceloc as as tagtype:tagid thin?
    public static HashMap<TagType, List<ModTag>> MAP = new HashMap<>();

    public static ModTag register(TagType type, ModTag tag) {
        if (!MAP.containsKey(type)) {
            MAP.put(type, new ArrayList<>());
        }
        MAP.get(type).add(tag);
        return tag;
    }


    public abstract ModTag fromString(String s);

    public abstract String getTagType();

    @Override
    public AutoLocGroup locNameGroup() {
        return AutoLocGroup.Tags;
    }

    @Override
    public String locNameLangFileGUID() {
        return skillsandclassesRef.MODID + ".tag." + getTagType() + "." + GUID();
    }

    @Override
    public String locNameForLangFile() {
        return StringUTIL.capitalise(GUID().replaceAll("_", " "));
    }


}
