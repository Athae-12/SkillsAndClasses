package com.athae.skillsandclasses.tags.imp;

import com.athae.skillsandclasses.tags.NormalModTag;
import com.athae.skillsandclasses.tags.ModTag;
import com.athae.skillsandclasses.tags.TagType;

import java.util.List;
import java.util.stream.Collectors;

public class ElementTag extends NormalModTag {

    public static ElementTag of(String id) {
        return (ElementTag) register(TagType.Element, new ElementTag(id));
    }

    public static List<ElementTag> getAll() {
        return ModTag.MAP.get(TagType.Element).stream().map(x -> (ElementTag) x).collect(Collectors.toList());
    }

    public ElementTag(String id) {
        super(id);
    }

    @Override
    public ModTag fromString(String s) {
        return new ElementTag(s);
    }

    @Override
    public String getTagType() {
        return TagType.Element.id;
    }
}
