package com.badbones69.crazycrates.api.util.types.player;

import com.badbones69.crazycrates.api.configuration.files.Config;
import com.badbones69.crazycrates.api.util.LoggerUtil;
import org.jetbrains.annotations.Nullable;

/**
 * Player event listener.
 *
 * @author BillyGalbreath
 */
public interface PlayerListenerImpl {

    /**
     * Fired when a player joins the server.
     *
     * @param playerImpl player that joined
     */
    default void onPlayerJoin(@Nullable PlayerImpl playerImpl) {
        if (playerImpl == null) return;

        if (Config.VERBOSE) LoggerUtil.debug("<gold>" + playerImpl.getName() + "</gold> <red>has joined the server.</red>");
    }

    /**
     * Fired when a player leaves the server.
     *
     * @param playerImpl player that left
     */
    default void onPlayerQuit(@Nullable PlayerImpl playerImpl) {
        if (playerImpl == null) return;

        if (Config.VERBOSE) LoggerUtil.debug("<gold>" + playerImpl.getName() + "</gold> <red>has left the server.</red>");
    }
}