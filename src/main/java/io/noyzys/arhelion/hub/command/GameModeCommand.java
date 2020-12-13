package dev.noyzys.arhelion.hub.command;

import dev.noyzys.arhelion.hub.api.helper.command.CommandExtender;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtenderInfo;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import dev.noyzys.arhelion.hub.api.helper.parser.GameModeParserHelper;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Consumer;

/**
 * @author: NoyZys on 20:59, 19.10.2019
 **/
@CommandExtenderInfo(
        name = "GameMode",
        aliases = {
                "gm"
        }
)
public class GameModeCommand extends CommandExtender {

    private final String gamemode_permission;
    private final String gamemode_permission_message;
    private final String gamemode_usage_message;

    private final String gamemode_mode_not_found;
    private final String gamemode_change_mode_forplayer;
    private final String gamemode_player_not_found;
    private final String gamemode_change_players_mode_toadmin;
    private final String gamemode_changed_your_mode_toplayer;
//    private Consumer<Boolean> booleanConsumer;

    public GameModeCommand(@NotNull FileConfiguration configuration) {
        this.gamemode_permission = configuration.getString("messages_configuration.gamemode_options.permission");
        this.gamemode_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.permission_message"));
        this.gamemode_usage_message = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.usage"));

        this.gamemode_mode_not_found = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.mode_not_found"));
        this.gamemode_change_mode_forplayer = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.change_mode_forplayer"));
        this.gamemode_player_not_found = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.player_not_found"));
        this.gamemode_change_players_mode_toadmin = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.change_players_mode_toadmin"));
        this.gamemode_changed_your_mode_toplayer = StringMessageHelper.colored(configuration.getString("messages_configuration.gamemode_options.changed_your_mode_toplayer"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(this.gamemode_permission)) return StringMessageHelper
                .create(this.gamemode_permission_message)
                .sendMessage(player);

        if (args.length < 1 || args.length > 2) return StringMessageHelper
                .create(this.gamemode_usage_message)
                .sendMessage(player);

        final Optional<GameMode> gameMode = GameModeParserHelper
                .parseGameMode(args[0]);
        if (this.getGameModeChecker(args[0]) == null) return StringMessageHelper
                .create(this.gamemode_mode_not_found)
                .sendMessage(player);

        gameMode.ifPresent(gm -> {
            player.setGameMode(gm);
            StringMessageHelper
                    .create(this.gamemode_change_mode_forplayer
                            .replace("{GAMEMODE}", gm.name()))
                    .sendMessage(player);
        });

        if (args.length == 1) return true;
        final Player target = Bukkit.getPlayerExact(args[1]);
        if (target == null) return StringMessageHelper
                .create(this.gamemode_player_not_found)
                .sendMessage(player);

        gameMode.ifPresent(gm -> {
            target.setGameMode(gm);
            StringMessageHelper
                    .create(this.gamemode_change_players_mode_toadmin
                            .replace("{PLAYER}", target.getName())
                            .replace("{GAMEMODE}", gm.name()))
                    .sendMessage(player);
            StringMessageHelper
                    .create(this.gamemode_changed_your_mode_toplayer
                            .replace("{ADMIN}", player.getName())
                            .replace("{GAMEMODE}", gm.name()))
                    .sendMessage(target);
        });
        return true;
    }

    @NotNull
    private GameMode getGameModeChecker(@NotNull String gamemode) {
        switch (gamemode.toLowerCase()) {
            case "0":
                return GameMode.SURVIVAL;

            case "1":
                return GameMode.CREATIVE;

            case "2":
                return GameMode.ADVENTURE;

            case "3":
                return GameMode.SPECTATOR;
        }
        return null;
    }
}


//        final Runnable runnable = () -> StringMessageHelper
//                .create(this.gamemode_mode_not_found)
//                .sendMessage(player);
