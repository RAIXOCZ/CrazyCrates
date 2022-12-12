package com.badbones69.crazycrates.api.util;

import com.badbones69.crazycrates.api.Crates;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class AdventureUtil {

    /**
     * Parse a message.
     *
     * @param message message to parse
     * @param placeholders message placeholders
     */
    public static Component parse(String message, TagResolver.Single... placeholders) {
        return Crates.api().getMessage().deserialize(message, placeholders);
    }
}