package com.badbones69.crazycrates.api;

import com.badbones69.crazycrates.api.configuration.files.Base;
import com.badbones69.crazycrates.api.configuration.files.Config;
import com.badbones69.crazycrates.api.configuration.files.Locale;
import org.simpleyaml.configuration.file.FileConfiguration;

public class Starter {

    public void run() {
        Base.reload(Crates.api().getDirectory());


        if (Base.ENABLE_NEW_CONFIG_LAYOUT) {
            Config.reload(Crates.api().getDirectory());
            Locale.reload(Crates.api().getLocaleDirectory());

            //Crates.api().getCrateManager().load();

            return;
        }

        Crates.api().getFileManager().setLog(true)
                .registerDefaultGenerateFiles("CrateExample.yml", "/crates", "/crates")
                .registerDefaultGenerateFiles("QuadCrateExample.yml", "/crates", "/crates")
                .registerDefaultGenerateFiles("CosmicCrateExample.yml", "/crates", "/crates")
                .registerDefaultGenerateFiles("QuickCrateExample.yml", "/crates", "/crates")
                .registerDefaultGenerateFiles("classic.nbt", "/schematics", "/schematics")
                .registerDefaultGenerateFiles("nether.nbt", "/schematics", "/schematics")
                .registerDefaultGenerateFiles("outdoors.nbt", "/schematics", "/schematics")
                .registerDefaultGenerateFiles("sea.nbt", "/schematics", "/schematics")
                .registerDefaultGenerateFiles("soul.nbt", "/schematics", "/schematics")
                .registerDefaultGenerateFiles("wooden.nbt", "/schematics", "/schematics")
                .registerCustomFilesFolder("/crates")
                .registerCustomFilesFolder("/schematics")
                .setup();

        FileConfiguration config = FileManager.Files.CONFIG.getFile();

        String metricsPath = config.getString("Settings.Toggle-Metrics");

        String crateLogFile = config.getString("Settings.Crate-Actions.Log-File");
        String crateLogConsole = config.getString("Settings.Crate-Actions.Log-Console");

        if (crateLogFile == null) {
            config.set("Settings.Crate-Actions.Log-File", false);

            FileManager.Files.CONFIG.saveFile();
        }

        if (crateLogConsole == null) {
            config.set("Settings.Crate-Actions.Log-Console", false);

            FileManager.Files.CONFIG.saveFile();
        }

        if (metricsPath == null) {
            config.set("Settings.Toggle-Metrics", false);

            FileManager.Files.CONFIG.saveFile();
        }
    }

    public void stop() {
        if (Base.ENABLE_NEW_CONFIG_LAYOUT) {
            //Crates.api().getCrateManager().unload();

            return;
        }
    }
}