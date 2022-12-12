package com.badbones69.crazycrates.api.util.types.player;

import com.badbones69.crazycrates.api.configuration.files.Locale;
import com.badbones69.crazycrates.api.util.AdventureUtil;
import com.badbones69.crazycrates.api.util.keys.Key;
import com.badbones69.crazycrates.api.util.types.Sender;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

/**
 * Represents a player object.
 *
 * @author BillyGalbreath
 */
public abstract class PlayerImpl extends Sender {

    public PlayerImpl(Key key) {
        super(key);
    }

    /**
     * Create a new key.
     *
     * @param uuid player uuid
     * @return a new key
     */
    public static Key createKey(@NotNull UUID uuid) {
        return Key.of(uuid);
    }

    /**
     * Get the player's name.
     *
     * @return player's name
     */
    public abstract String getName();

    /**
     * Get the player's uuid.
     *
     * @return player's uuid
     */
    public abstract UUID getUUID();

    @Override
    public void send(boolean prefix, @NotNull ComponentLike message) {
        sendMessage(prefix ? AdventureUtil.parse(Locale.PREFIX_COMMAND).append(message) : message);
    }
}