package com.athae.skillsandclasses.registry;

import com.athae.skillsandclasses.interfaces.ISerializable;
import com.athae.skillsandclasses.registry.util.RegistryUtil;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class DatapackGenerator<T extends IGUID & ISerializable<T>> extends BaseDatapackGenerator<T> implements DataProvider {
    String modid;


    public DatapackGenerator(String modid, List<T> list, String category) {
        super(list, category);
        this.modid = modid;

    }


    public Path resolve(Path path, T object) {
        return path.resolve(
                "data/" + modid + "/" + category + "/" + object.datapackFolder() + object.getFileName()
                        .replaceAll(":", "_") +
                        ".json");
    }

    protected CompletableFuture<?> generateAll(CachedOutput cache) {

        try {
            Path path = gameDirPath();

            for (T entry : list) {

                if (entry instanceof skillsandclassesRegistry<T> er) {
                    if (!RegistryUtil.MODID_TO_GENERATE_DATA.test(er.getRegistrationInfo().modid)) {
                        continue;
                    }
                }
                if (!entry.shouldGenerateJson()) {
                    continue;
                }

                Path target = movePath(resolve(path, entry));

                target = Paths.get(target.toString()
                        .replace("\\.\\", "\\"));

                try {
                    DataProvider.saveStable(cache, entry.toJson(), target);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (RuntimeException e) {
            e.printStackTrace();
        }

        return CompletableFuture.completedFuture(Optional.empty()); // todo..??
    }

    @Override
    public CompletableFuture<?> run(CachedOutput pOutput) {
        return generateAll(pOutput);
    }

    @Override
    public String getName() {
        return this.category;
    }
}