package com.badbones69.crazycrates;

import com.badbones69.crazycrates.api.Crates;
import com.badbones69.crazycrates.api.CrazyManager;
import com.badbones69.crazycrates.api.EventLogger;
import com.badbones69.crazycrates.api.FileManager;
import com.badbones69.crazycrates.api.Starter;
import com.badbones69.crazycrates.api.enums.settings.Messages;
import com.badbones69.crazycrates.api.managers.quadcrates.SessionManager;
import com.badbones69.crazycrates.api.objects.CrateLocation;
import com.badbones69.crazycrates.commands.subs.CrateBaseCommand;
import com.badbones69.crazycrates.commands.subs.player.BaseKeyCommand;
import com.badbones69.crazycrates.cratetypes.CSGO;
import com.badbones69.crazycrates.cratetypes.Cosmic;
import com.badbones69.crazycrates.cratetypes.CrateOnTheGo;
import com.badbones69.crazycrates.cratetypes.QuadCrate;
import com.badbones69.crazycrates.cratetypes.QuickCrate;
import com.badbones69.crazycrates.cratetypes.Roulette;
import com.badbones69.crazycrates.cratetypes.War;
import com.badbones69.crazycrates.cratetypes.Wheel;
import com.badbones69.crazycrates.cratetypes.Wonder;
import com.badbones69.crazycrates.listeners.BrokeLocationsListener;
import com.badbones69.crazycrates.listeners.CrateControlListener;
import com.badbones69.crazycrates.listeners.FireworkDamageListener;
import com.badbones69.crazycrates.listeners.MenuListener;
import com.badbones69.crazycrates.listeners.MiscListener;
import com.badbones69.crazycrates.listeners.PreviewListener;
import com.badbones69.crazycrates.support.libs.PluginSupport;
import com.badbones69.crazycrates.support.placeholders.PlaceholderAPISupport;
import com.badbones69.crazycrates.support.structures.blocks.ChestStateHandler;
import dev.triumphteam.cmd.bukkit.BukkitCommandManager;
import dev.triumphteam.cmd.bukkit.message.BukkitMessageKey;
import dev.triumphteam.cmd.core.message.MessageKey;
import dev.triumphteam.cmd.core.suggestion.SuggestionKey;
import org.bstats.bukkit.Metrics;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import org.simpleyaml.configuration.file.FileConfiguration;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class CrazyCrates extends JavaPlugin implements Crates, Listener {

    private static CrazyCrates instance;

    private final BukkitCommandManager<CommandSender> manager = BukkitCommandManager.create(this);

    private final FileManager fileManager;

    private final Starter starter;

    private final CrazyManager crazyManager;
    private final ChestStateHandler chestStateHandler;
    private final EventLogger eventLogger;

    public CrazyCrates() {
        super();

        instance = this;

        this.fileManager = new FileManager();

        this.starter = new Starter();

        this.crazyManager = new CrazyManager();
        this.chestStateHandler = new ChestStateHandler();
        this.eventLogger = new EventLogger();

        try {
            Field api = Provider.class.getDeclaredField("api");
            api.setAccessible(true);
            api.set(null, this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        this.starter.run();

        cleanFiles();

        // Add extra messages.
        Messages.addMissingMessages();

        FileConfiguration config = FileManager.Files.CONFIG.getFile();

        boolean metricsEnabled = config.getBoolean("Settings.Toggle-Metrics");

        if (metricsEnabled) new Metrics(this, 4514);
    }

    public void cleanFiles() {
        if (!FileManager.Files.LOCATIONS.getFile().contains("Locations")) {
            FileManager.Files.LOCATIONS.getFile().set("Locations.Clear", null);
            FileManager.Files.LOCATIONS.saveFile();
        }

        if (!FileManager.Files.DATA.getFile().contains("Players")) {
            FileManager.Files.DATA.getFile().set("Players.Clear", null);
            FileManager.Files.DATA.saveFile();
        }
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        SessionManager.endCrates();

        QuickCrate.removeAllRewards();

        if (this.crazyManager.getHologramController() != null) this.crazyManager.getHologramController().removeAllHolograms();

        disable();
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onPlayerJoin(PlayerJoinEvent e) {
        this.crazyManager.setNewPlayerKeys(e.getPlayer());
        this.crazyManager.loadOfflinePlayersKeys(e.getPlayer());
    }

    /**
     * @return The instance of the plugin.
     */
    public static CrazyCrates getInstance() {
        return instance;
    }

    @Override
    public void enable() {
        this.starter.run();

        PluginManager pluginManager = getServer().getPluginManager();

        pluginManager.registerEvents(new MenuListener(), this);
        pluginManager.registerEvents(new PreviewListener(), this);
        pluginManager.registerEvents(new FireworkDamageListener(), this);
        pluginManager.registerEvents(new CrateControlListener(), this);
        pluginManager.registerEvents(new MiscListener(), this);

        pluginManager.registerEvents(new War(), this);
        pluginManager.registerEvents(new CSGO(), this);
        pluginManager.registerEvents(new Wheel(), this);
        pluginManager.registerEvents(new Wonder(), this);
        pluginManager.registerEvents(new Cosmic(), this);
        pluginManager.registerEvents(new Roulette(), this);
        pluginManager.registerEvents(new QuickCrate(), this);
        pluginManager.registerEvents(new CrateOnTheGo(), this);
        pluginManager.registerEvents(new QuadCrate(), this);

        pluginManager.registerEvents(this, this);

        this.crazyManager.loadCrates();

        if (!this.crazyManager.getBrokeCrateLocations().isEmpty()) pluginManager.registerEvents(new BrokeLocationsListener(), this);

        if (PluginSupport.PLACEHOLDERAPI.isPluginEnabled()) new PlaceholderAPISupport().register();

        this.manager.registerMessage(MessageKey.UNKNOWN_COMMAND, (sender, context) -> sender.sendMessage(Messages.UNKNOWN_COMMAND.getMessage()));

        this.manager.registerMessage(MessageKey.TOO_MANY_ARGUMENTS, (sender, context) -> sender.sendMessage(Messages.TOO_MANY_ARGS.getMessage()));

        this.manager.registerMessage(MessageKey.NOT_ENOUGH_ARGUMENTS, (sender, context) -> sender.sendMessage(Messages.NOT_ENOUGH_ARGS.getMessage()));

        this.manager.registerMessage(MessageKey.INVALID_ARGUMENT, (sender, context) -> sender.sendMessage(Messages.NOT_ONLINE.getMessage().replace("%player%", context.getTypedArgument())));

        this.manager.registerMessage(BukkitMessageKey.NO_PERMISSION, (sender, context) -> sender.sendMessage(Messages.NO_PERMISSION.getMessage()));

        this.manager.registerMessage(BukkitMessageKey.PLAYER_ONLY, (sender, context) -> sender.sendMessage(Messages.MUST_BE_A_PLAYER.getMessage()));

        this.manager.registerMessage(BukkitMessageKey.CONSOLE_ONLY, (sender, context) -> sender.sendMessage(Messages.MUST_BE_A_CONSOLE_SENDER.getMessage()));

        this.manager.registerSuggestion(SuggestionKey.of("crates"), (sender, context) -> this.fileManager.getAllCratesNames().stream().toList());

        this.manager.registerSuggestion(SuggestionKey.of("key-types"), (sender, context) -> KEYS);

        this.manager.registerSuggestion(SuggestionKey.of("online-players"), (sender, context) -> getServer().getOnlinePlayers().stream().map(Player::getName).toList());

        this.manager.registerSuggestion(SuggestionKey.of("locations"), (sender, context) -> this.crazyManager.getCrateLocations().stream().map(CrateLocation::getID).toList());

        this.manager.registerSuggestion(SuggestionKey.of("prizes"), (sender, context) -> {
            List<String> numbers = new ArrayList<>();

            this.crazyManager.getCrateFromName(context.getArgs().get(0)).getPrizes().forEach(prize -> numbers.add(prize.getName()));

            return numbers;
        });

        this.manager.registerSuggestion(SuggestionKey.of("numbers"), (sender, context) -> {
            List<String> numbers = new ArrayList<>();

            for (int i = 1; i <= 250; i++) numbers.add(i + "");

            return numbers;
        });

        this.manager.registerCommand(new BaseKeyCommand());
        this.manager.registerCommand(new CrateBaseCommand());
    }

    private final List<String> KEYS = List.of("virtual", "v", "physical", "p");

    @Override
    public void disable() {
        this.starter.stop();
    }

    @Override
    public @NotNull Path getDirectory() {
        return getDataFolder().toPath();
    }

    @Override
    public @NotNull Path getCratesDirectory() {
        return getDirectory().resolve("crates");
    }

    @Override
    public @NotNull Path getCacheDirectory() {
        return getDirectory().resolve("cache");
    }

    @Override
    public @NotNull Path getLocaleDirectory() {
        return getDirectory().resolve("locale");
    }

    @Override
    public @NotNull Path getDataDirectory() {
        return getDirectory().resolve("data");
    }

    @Override
    public @NotNull FileManager getFileManager() {
        return this.fileManager;
    }

    public CrazyManager getCrazyManager() {
        return crazyManager;
    }

    public ChestStateHandler getChestStateHandler() {
        return chestStateHandler;
    }

    public EventLogger getEventLogger() {
        return eventLogger;
    }
}