package com.badbones69.crazycrates.api;

import com.badbones69.crazycrates.api.configuration.CrateManager;
import com.badbones69.crazycrates.api.util.types.Console;
import com.badbones69.crazycrates.api.util.types.player.PlayerRegistryImpl;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.jetbrains.annotations.NotNull;
import java.nio.file.Path;

public interface Crates {

    final class Provider {
        static Crates api;

        @NotNull
        public static Crates api() {
            return Provider.api;
        }
    }

    @NotNull
    static Crates api() {
        return Provider.api();
    }

    void enable();

    void disable();

    /**
     * @return The plugin directory
     */
    @NotNull Path getDirectory();

    /**
     * A parent folder for voucher codes / items.
     *
     * @return The folder called "types"
     */
    @NotNull Path getTypesDirectory();

    /**
     * Stores all crate types.
     *
     * @return The folder called "crates"
     */
    @NotNull Path getCratesDirectory();

    /**
     * Used to cache any 3rd party jars that we don't want shaded.
     *
     * @return The folder called "cache"
     */
    @NotNull Path getCacheDirectory();

    /**
     * Used to store all locale files ( multi lang support )
     *
     * @return The folder called "locale"
     */
    @NotNull Path getLocaleDirectory();

    /**
     * Used to store all data related files.
     *
     * @return The directory called "data"
     */
    @NotNull Path getDataDirectory();

    /**
     * A platform-agnostic implementation of Console Sender.
     *
     * @return The console sender
     */
    @NotNull Console getConsole();
    @NotNull CrateManager getCrateManager();

    @NotNull FileManager getFileManager();
    @NotNull PlayerRegistryImpl getPlayerRegistry();
    @NotNull MiniMessage getMessage();

}