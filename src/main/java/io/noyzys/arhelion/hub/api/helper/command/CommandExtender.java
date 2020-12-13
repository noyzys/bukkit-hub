package dev.noyzys.arhelion.hub.api.helper.command;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.command.CommandSender;
import org.bukkit.command.defaults.BukkitCommand;

import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * @author: NoyZys on 20:47, 19.10.2019
 **/
public abstract class CommandExtender {

    public CommandExtender() {
        final CommandExtenderInfo commandExtenderInfo = this.getClass().getDeclaredAnnotation(CommandExtenderInfo.class);
        final BukkitCommand bukkitCommand = new BukkitCommand(commandExtenderInfo.name()) {
            @Override
            public boolean execute(final CommandSender sender, final String label, final String... args) {
                CommandExtender.this.execute(sender, args);
                return true;
            }
        };
        bukkitCommand.setAliases(Arrays.asList(commandExtenderInfo.aliases()));
        try {
            final Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
            field.setAccessible(true);
            ((CommandMap) field.get(Bukkit.getServer())).register("HubPlugin-1.0-SNAPSHOT", bukkitCommand);
            field.setAccessible(false);
        } catch (final Exception ex) {
            ex.printStackTrace();
        }
    }

    public abstract boolean execute(final CommandSender sender, final String... args);
}
