package dev.noyzys.arhelion.hub.command;

import dev.noyzys.arhelion.hub.api.helper.command.CommandExtender;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtenderInfo;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author: NoyZys on 19:33, 20.10.2019
 **/
@CommandExtenderInfo(
        name = "SetSpawn",
        aliases = {
                "ustawspawn",
                "resp"
        }
)
public class SetSpawnCommand extends CommandExtender {

    private final String setspawn_permission;
    private final String setspawn_permission_message;
    private final String setspawn_setspawn;

    public SetSpawnCommand(@NotNull FileConfiguration configuration) {
        this.setspawn_permission = configuration.getString("messages_configuration.setspawn_options.permission");
        this.setspawn_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.setspawn_options.permission_message"));
        this.setspawn_setspawn = StringMessageHelper.colored(configuration.getString("messages_configuration.setspawn_options.setspawn"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(this.setspawn_permission)) return StringMessageHelper
                .create(this.setspawn_permission_message)
                .sendMessage(player);
        player.getWorld().setSpawnLocation(player.getLocation().getBlockX(), player.getLocation().getBlockY(), player.getLocation().getBlockZ());
        return StringMessageHelper
                .create(this.setspawn_setspawn
                .replace("{X}", player.getLocation().getBlockY() + "")
                .replace("{Y}", player.getLocation().getBlockY() + "")
                .replace("{Z}", player.getLocation().getBlockZ() + ""))
                .sendMessage(player);
    }
}
