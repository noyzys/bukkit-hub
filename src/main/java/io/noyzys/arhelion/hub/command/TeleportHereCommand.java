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
 * @author: NoyZys on 19:56, 20.10.2019
 **/
@CommandExtenderInfo(
        name = "TeleportHereTele"
)
public class TeleportHereCommand extends CommandExtender {

    private final String teleporthere_permission;
    private final String teleporthere_permission_message;
    private final String teleporthere_player_not_found;
    private final String teleporthere_succes_teleported;

    public TeleportHereCommand(@NotNull FileConfiguration configuration) {
        this.teleporthere_permission = configuration.getString("messages_configuration.teleporthere_options.permission");
        this.teleporthere_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.teleporthere_options.permission_message"));
        this.teleporthere_player_not_found = StringMessageHelper.colored(configuration.getString("messages_configuration.teleporthere_options.player_not_found"));
        this.teleporthere_succes_teleported = StringMessageHelper.colored(configuration.getString("messages_configuration.teleporthere_options.sucess_teleportedhere"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(teleporthere_permission_message)) return StringMessageHelper
                .create(teleporthere_permission_message)
                .sendMessage(player);

        final Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null) return StringMessageHelper
                .create(this.teleporthere_player_not_found)
                .sendMessage(player);
        target.teleport(player.getLocation());
        return StringMessageHelper
                .create(this.teleporthere_succes_teleported
                        .replace("{PLAYER}", target.getName()))
                .sendMessage(player);
    }
}
