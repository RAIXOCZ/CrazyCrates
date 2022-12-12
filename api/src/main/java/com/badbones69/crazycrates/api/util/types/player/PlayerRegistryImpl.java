package com.badbones69.crazycrates.api.util.types.player;

import com.badbones69.crazycrates.api.util.types.registry.KeyedRegistry;
import org.jetbrains.annotations.Nullable;
import java.util.Locale;
import java.util.UUID;

/**
 * Manages player specific data.
 *
 * @author BillyGalbreath
 */
public abstract class PlayerRegistryImpl extends KeyedRegistry<PlayerImpl> {

    /**
     * Get the registered player by uuid.
     * <p>
     * Will return null if no player registered.
     *
     * @param uuid player uuid
     * @return registered player or null
     */
    @Nullable
    public PlayerImpl get(UUID uuid) {
        return get(PlayerImpl.createKey(uuid));
    }

    /**
     * Get the registered player by name.
     * <p>
     * Will return null if no player registered.
     *
     * @param name player name
     * @return registered player or null
     */
    @Nullable
    public PlayerImpl get(String name) {
        String lowercaseName = name.toLowerCase(Locale.ROOT);

        for (PlayerImpl playerImpl : entries().values()) {
            if (playerImpl.getName().toLowerCase(Locale.ROOT).equals(lowercaseName)) return playerImpl;
        }

        return null;
    }
}