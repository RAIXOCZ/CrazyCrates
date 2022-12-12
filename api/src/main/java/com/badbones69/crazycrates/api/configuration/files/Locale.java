package com.badbones69.crazycrates.api.configuration.files;

import com.badbones69.crazycrates.api.configuration.AbstractConfig;
import com.badbones69.crazycrates.api.util.FileUtil;
import java.nio.file.Path;

public class Locale extends AbstractConfig {

    @Key("prefix.command")
    @Comment("Change how the prefix for commands will look!")
    public static String PREFIX_COMMAND = "<white>[<gradient:#FE5F55:#6b55b5>CrazyCrates</gradient>]</white> ";

    private static final Locale LOCALE_FILE = new Locale();

    public static void reload(Path directory) {
        FileUtil.extract("/locale/", directory, false);

        LOCALE_FILE.handle(directory.resolve(Config.LANGUAGE_FILE), Locale.class);
    }
}