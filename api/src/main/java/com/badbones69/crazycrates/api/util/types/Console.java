package com.badbones69.crazycrates.api.util.types;

import com.badbones69.crazycrates.api.util.AdventureUtil;
import com.badbones69.crazycrates.api.util.keys.Key;
import net.kyori.adventure.text.ComponentLike;
import org.jetbrains.annotations.NotNull;
import java.util.UUID;

/**
 * Represents the console command sender.
 *
 * @author BillyGalbreath
 */
public abstract class Console extends Sender {

    public Console() {
        super(Key.of(new UUID(0, 0)));
    }

    @Override
    public void send(boolean prefix, @NotNull ComponentLike message) {
        sendMessage(AdventureUtil.parse("<white>[<gradient:#FE5F55:#6b55b5>CrazyCrates</gradient>]</white> ").append(message));
    }
}