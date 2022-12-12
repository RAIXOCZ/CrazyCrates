package com.badbones69.crazycrates.api.configuration.files;

import com.badbones69.crazycrates.api.configuration.AbstractConfig;
import java.nio.file.Path;

public class Base extends AbstractConfig {

    @Key("new-config-layout")
    @Comment("""
            I could not find a decent way to figure out how to update the config.yml/messages.yml & crate configurations then I thought of this
            Switching this from "false" to "true" will use a new config layout for crates & config.yml/messages.yml
            Do not enable this unless you want to use the new configuration layouts.
            
            While this option is "true" or "false", You will have a log in console telling you what config version you are using.
            
            What happens when I enable this?
            1) The entire directory as is will get thrown into a .zip file
            2) config.yml, messages.yml, crates folder will be deleted.
            3) A new config.yml, a locale folder & a crates folder will appear.
            4) A big fat warning will show up in console in bright red letters.""")
    public static boolean ENABLE_NEW_CONFIG_LAYOUT = false;

    private static final Base BASE_FILE = new Base();

    public static void reload(Path directory) {
        BASE_FILE.handle(directory.resolve("base.yml"), Base.class);
    }
}