package dev.noyzys.arhelion.hub.command;

import dev.noyzys.arhelion.hub.HubPlugin;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtender;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtenderInfo;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author: NoyZys on 20:03, 20.10.2019
 **/
@CommandExtenderInfo(
        name = "WhiteList",
        aliases = {
                "wh",
                "white",
                "bialalista"
        }
)
public class WhiteListCommand extends CommandExtender {

    private final String whitelist_permission;
    private final String whitelist_permission_message;
    private final String whitelist_usage;

    private static boolean whitelist_status;

    private static List<String> whitelist_list_players_onwhitelist;

    private final String whitelist_isenable;

    private final String whitelist_enable;

    private final String whitelist_isdisable;

    private final String whitelist_disable;
    private final String whitelist_add_isadded;

    private final String whitelist_add_towhitelist;
    private final String whitelist_remove_isremoved;

    private final String whitelist_remove_towhitelist;
    private final String whitelist_list_onwhitelist;

    private final String whitelist_mode_not_found;
    public WhiteListCommand(@NotNull FileConfiguration configuration) {
        this.whitelist_permission = configuration.getString("messages_configuration.whitelist_options.permission");
        this.whitelist_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.permission_message"));
        this.whitelist_usage = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.usage"));
        whitelist_status = configuration.getBoolean("messages_configuration.whitelist_options.status");
        whitelist_list_players_onwhitelist = configuration.getStringList("messages_configuration.whitelist_options.list_added_towhitelist");
        this.whitelist_isenable = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.isenable"));
        this.whitelist_enable = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.enable"));
        this.whitelist_isdisable = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.isdisable"));
        this.whitelist_disable = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.disable"));
        this.whitelist_add_isadded = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.isadded"));
        this.whitelist_add_towhitelist = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.add"));
        this.whitelist_remove_isremoved = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.isremoved"));
        this.whitelist_remove_towhitelist = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.remove"));
        this.whitelist_list_onwhitelist = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.list"));
        this.whitelist_mode_not_found = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.mode_not_found"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(this.whitelist_permission)) return StringMessageHelper
                .create(this.whitelist_permission_message)
                .sendMessage(player);

        if (args.length == 0) return StringMessageHelper
                .create(this.whitelist_usage)
                .sendMessage(player);

        if (args[0].equalsIgnoreCase("on")) {
            if (whitelist_status) return StringMessageHelper
                    .create(this.whitelist_isenable)
                    .sendMessage(player);

            this.setWhitelist_status(true);
            return StringMessageHelper
                    .create(this.whitelist_enable)
                    .sendMessage(player);
        }

        if (args[0].equalsIgnoreCase("off")) {
            if (whitelist_status) return StringMessageHelper
                    .create(this.whitelist_isdisable)
                    .sendMessage(player);

            this.setWhitelist_status(false);
            return StringMessageHelper
                    .create(this.whitelist_disable)
                    .sendMessage(player);
        }

        if (args[0].equalsIgnoreCase("add")) {
            if (args.length == 1) {
                if (whitelist_list_players_onwhitelist.contains(args[1])) return StringMessageHelper
                        .create(this.whitelist_add_isadded)
                        .sendMessage(player);

                whitelist_list_players_onwhitelist.add(args[1]);
                HubPlugin.getInstance().saveConfig();
                return StringMessageHelper
                        .create(this.whitelist_add_towhitelist
                                .replace("{PLAYER}", args[1]))
                        .sendMessage(player);
            }

            if (args[0].equalsIgnoreCase("remove")) {
                if (args.length == 1) {
                    if (!whitelist_list_players_onwhitelist.contains(args[1])) return StringMessageHelper
                            .create(this.whitelist_remove_isremoved)
                            .sendMessage(player);
                    whitelist_list_players_onwhitelist.remove(args[1]);
                    HubPlugin.getInstance().saveConfig();
                    return StringMessageHelper
                            .create(this.whitelist_remove_towhitelist
                                    .replace("{PLAYER}", args[1]))
                            .sendMessage(player);
                }

                return args[0].equalsIgnoreCase("list") ? StringMessageHelper
                        .create(this.whitelist_list_onwhitelist
                                .replace("{LIST}", String.join("", whitelist_list_players_onwhitelist) + ", ") + "")
                        .sendMessage(player) : StringMessageHelper
                        .create(this.whitelist_mode_not_found)
                        .sendMessage(player);
            }
        }
        return true;
    }
    public static List<String> getWhitelist_list_players_onwhitelist() {
        return whitelist_list_players_onwhitelist;
    }

    private void setWhitelist_status(boolean whitelist_status) {
        WhiteListCommand.whitelist_status = whitelist_status;
    }

    public static boolean isWhitelist_status() {
        return whitelist_status;
    }
}

