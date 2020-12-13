package dev.noyzys.arhelion.hub.api.helper.message;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author: NoyZys on 19:45, 19.10.2019
 **/
public final class StringMessageHelper {

    @NotNull
    @Contract(pure = true)
    private StringMessageHelper() {
    }

    @NotNull
    public static String colored(@NotNull String message) {
        return ChatColor
                .translateAlternateColorCodes('&', message
                        .replace(">>", "»")
                        .replace("<<", "«")
                );
    }

    @NotNull
    public static List<String> colored(@NotNull List<String> messages) {
        return messages
                .stream()
                .map(StringMessageHelper::colored)
                .collect(Collectors.toList()
                );
    }

    @NotNull
    public static String toString(@NotNull Collection<String> messages) {
        return colored(messages
                .toString()
                .replace(", ", "\n")
                .replace("[", "")
                .replace("]", ""));
    }

    @NotNull
    public static StringPlayerMessageHelper create(@NotNull String message) {
        return new StringPlayerMessageHelper(colored(message)
        );
    }

    public static boolean sendMessage(@NotNull Player player, String message){
        player.sendMessage(colored(message));
        return true;
    }

    public static boolean sendMessage(@NotNull CommandSender sender, String message){
        sender.sendMessage(colored(message));
        return true;
    }

    public static void sendEmptyMessage(@NotNull Player player) {
        IntStream.range(0, 100)
                .forEachOrdered(value -> sendMessage(player, " ")
                );
    }
}
