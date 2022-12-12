package com.badbones69.crazycrates.api.configuration.files;

import com.badbones69.crazycrates.api.configuration.AbstractConfig;
import java.nio.file.Path;

public class Config extends AbstractConfig {

    @Key("settings.language-file")
    @Comment("""
            The language file to use from the locale folder.
            Supported languages are English(en).""")
    public static String LANGUAGE_FILE = "locale-en.yml";

    @Key("settings.verbose")
    @Comment("Whether you want to have verbose logging enabled.")
    public static boolean VERBOSE = true;

    @Key("settings.metrics")
    @Comment("Whether you want metrics to be enabled.")
    public static boolean METRICS = true;

    private static final Config CONFIG_FILE = new Config();

    public static void reload(Path directory) {
        CONFIG_FILE.handle(directory.resolve("config.yml"), Config.class);
    }
}