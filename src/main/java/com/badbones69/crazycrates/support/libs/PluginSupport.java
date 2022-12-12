package com.badbones69.crazycrates.support.libs;

import com.badbones69.crazycrates.CrazyCrates;

public enum PluginSupport {

    HOLOGRAPHIC_DISPLAYS("HolographicDisplays"),
    DECENT_HOLOGRAMS("DecentHolograms"),
    CMI("CMI"),
    PLACEHOLDERAPI("PlaceholderAPI");
    
    private final String name;

    private static final CrazyCrates plugin = CrazyCrates.getPlugin();
    
    PluginSupport(String name) {
        this.name = name;
    }
    
    public String getName() {
        return name;
    }

    public boolean isPluginEnabled() {
        return plugin.getServer().getPluginManager().isPluginEnabled(name);
    }
}