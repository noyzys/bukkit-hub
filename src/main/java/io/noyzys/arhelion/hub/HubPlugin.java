package dev.noyzys.arhelion.hub;

import dev.noyzys.arhelion.hub.command.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * @author: NoyZys on 19:53, 19.10.2019
 **/
public final class HubPlugin extends JavaPlugin {

    private static HubPlugin instance;
    private HubMessagesConfiguration    messagesConfiguration;

    @Override
    public void onEnable() {
        this.initialize();
    }

    private void initialize() {
        //<-- initialize configuration -->
        this.saveDefaultConfig();
        this.messagesConfiguration = new HubMessagesConfiguration(this);
        final FileConfiguration messagesConfiguration = this.messagesConfiguration.getMessagesConfiguration();
        new HubListener(this);

        //<-- register commands, listeners -->
        new GameModeCommand(messagesConfiguration);
        new SetSpawnCommand(messagesConfiguration);
        new TeleportCommand(messagesConfiguration);
        new TeleportHereCommand(messagesConfiguration);
        new WhiteListCommand(messagesConfiguration);

    }

    @Override
    public void onDisable() {
    }

    FileConfiguration createConfig() {
        final File file = new File(this.getDataFolder(), "config.yml");
        final FileConfiguration fileConfiguration = new YamlConfiguration();

        if (!file.exists()) this.saveResource("config.yml", false);

        try {
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return fileConfiguration;
    }

    public static HubPlugin getInstance() {
        return instance;
    }
}
