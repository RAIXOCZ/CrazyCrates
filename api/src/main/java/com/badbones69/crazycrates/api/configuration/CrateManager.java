package com.badbones69.crazycrates.api.configuration;

import com.badbones69.crazycrates.api.Crates;
import com.badbones69.crazycrates.api.configuration.files.CrateConfig;
import com.badbones69.crazycrates.api.records.Crate;
import com.badbones69.crazycrates.api.util.LoggerUtil;
import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class CrateManager {

    private final Set<Crate> crates = new HashSet<>();

    public void load() {
        File cratesDir = Crates.api().getCratesDirectory().toFile();

        // Loop through crate files.
        File[] crateFiles = cratesDir.listFiles(((dir, name) -> name.endsWith(".yml")));

        if (crateFiles == null) {
            LoggerUtil.severe("Could not read files from the crates directory! " + cratesDir.getAbsolutePath());
            return;
        }

        for (File file : crateFiles) {
            LoggerUtil.debug("<red>Loading file:</red> <gold>" + file.getName() + ".</gold>");

            CrateConfig crateConfig = new CrateConfig(file);

            try {
                crateConfig.load();
            } catch (IOException exception) {
                LoggerUtil.severe("Could not load voucher file: " + file.getName());
                exception.printStackTrace();
                continue;
            }

            //if (!crateConfig.isEnabled()) continue;

            Crate crate = new Crate(crateConfig);

            crates.add(crate);
        }
    }

    public void unload() {
        crates.clear();
    }

    public Set<Crate> getCrates() {
        return crates;
    }
}