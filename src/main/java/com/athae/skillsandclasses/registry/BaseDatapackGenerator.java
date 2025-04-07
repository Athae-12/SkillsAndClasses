package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.interfaces.ISerializable;
import net.minecraftforge.fml.loading.FMLPaths;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public abstract class BaseDatapackGenerator<T extends IGUID & ISerializable<T>> {

    public String category;
    public List<T> list;

    public BaseDatapackGenerator(List<T> list, String category) {
        this.list = list;
        this.category = category;
    }


    protected Path gameDirPath() {
        return FMLPaths.GAMEDIR.get();
    }

    protected Path movePath(Path target) {
        String movedpath = target.toString();
        movedpath = movedpath.replace("run/", "src/generated/resources/");
        movedpath = movedpath.replace("run\\", "src/generated/resources\\");

        return Paths.get(movedpath);
    }
}
