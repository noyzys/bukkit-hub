package dev.noyzys.arhelion.hub.command;

import dev.noyzys.arhelion.hub.api.helper.command.CommandExtender;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtenderInfo;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

/**
 * @author: NoyZys on 19:48, 20.10.2019
 **/
@CommandExtenderInfo(
        name = "Teleport",
        aliases = {
                "tp"
        }
)
public class TeleportCommand extends CommandExtender {

    private final String teleport_permission;
    private final String teleport_permission_message;
    private final String teleport_player_not_found;
    private final String teleport_succes_teleported;

    public TeleportCommand(@NotNull FileConfiguration configuration) {
        this.teleport_permission = configuration.getString("messages_configuration.teleport_options.permission");
        this.teleport_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.teleport_options.permission_message"));
        this.teleport_player_not_found = StringMessageHelper.colored(configuration.getString("messages_configuration.teleport_options.player_not_found"));
        this.teleport_succes_teleported = StringMessageHelper.colored(configuration.getString("messages_configuration.teleport_options.sucess_teleported"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(this.teleport_permission)) return StringMessageHelper
                .create(this.teleport_permission_message)
                .sendMessage(player);

        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) return StringMessageHelper
                .create(this.teleport_player_not_found)
                .sendMessage(player);
        player.teleport(target.getLocation());
        return StringMessageHelper
                .create(this.teleport_succes_teleported
                .replace("{PLAYER}", target.getName()))
                .sendMessage(player);
    }
}
