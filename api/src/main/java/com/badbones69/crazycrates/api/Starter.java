package com.badbones69.crazycrates.api;

import com.badbones69.crazycrates.api.configuration.files.Base;
import com.badbones69.crazycrates.api.configuration.files.Config;
import com.badbones69.crazycrates.api.configuration.files.Locale;

public class Starter {

    public void run() {
        Base.reload(Crates.api().getDirectory());


        if (Base.ENABLE_NEW_CONFIG_LAYOUT) {
            Config.reload(Crates.api().getDirectory());
            Locale.reload(Crates.api().getLocaleDirectory());

            Crates.api().getCrateManager().load();
        }
    }

    public void stop() {
        if (Base.ENABLE_NEW_CONFIG_LAYOUT) {
            Crates.api().getCrateManager().unload();
        }
    }
}