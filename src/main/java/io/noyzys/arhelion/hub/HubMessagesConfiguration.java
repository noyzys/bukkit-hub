package dev.noyzys.arhelion.hub;

import org.bukkit.configuration.file.FileConfiguration;

/**
 * @author: NoyZys on 20:43, 19.10.2019
 **/
final class HubMessagesConfiguration {

    private final FileConfiguration messagesConfiguration;

    HubMessagesConfiguration(HubPlugin plugin) {
        this.messagesConfiguration = plugin.createConfig();
    }

    FileConfiguration getMessagesConfiguration() {
        return messagesConfiguration;
    }
}
