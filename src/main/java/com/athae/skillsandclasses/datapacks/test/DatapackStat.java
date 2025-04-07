package com.athae.skillsandclasses.datapacks.test;

import com.athae.skillsandclasses.data.Stat;
import com.athae.skillsandclasses.interfaces.IAutoGson;
import com.athae.skillsandclasses.resources.Elements;

import java.util.ArrayList;
import java.util.List;

public class DatapackStat extends Stat implements IAutoGson<DatapackStat> {

    public static DatapackStat SERIALIZER = new DatapackStat();
    public static String SER = "data";

    public String ser = SER;
    public String id = "";
    public Elements ele = Elements.Physical;

    public List<DatapackStatEffect> effect = new ArrayList<>();

    public transient String locname;
    public transient String locdesc;

    @Override
    public Elements getElement() {
        return this.ele;
    }

    @Override
    public String locDescForLangFile() {
        return locdesc;
    }

    @Override
    public String locNameForLangFile() {
        return locname;
    }

    @Override
    public String GUID() {
        return id;
    }

    @Override
    public Class<DatapackStat> getClassForSerialization() {
        return DatapackStat.class;
    }

    @Override
    public boolean isFromDatapack() {
        return true;
    }

}