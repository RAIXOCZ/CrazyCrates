package com.badbones69.crazycrates.api.configuration.files;

import org.simpleyaml.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;

public class CrateConfig extends YamlConfiguration {

    private final File file;

    public CrateConfig(File file) {
        this.file = file;
    }

    public void load() throws IOException {
        load(file);
    }

    public File getFile() {
        return file;
    }

    public String getFileName() {
        return file.getName().replace(".yml", "");
    }
}