package com.athae.skillsandclasses.interfaces;

import com.athae.skillsandclasses.localization.Words;

public enum EffectSides {
    Source("source", Words.SOURCE),
    Target("target", Words.TARGET);

    public String id;
    public Words word;


    EffectSides(String id, Words word) {
        this.id = id;
        this.word = word;
    }
}