package dev.noyzys.arhelion.hub.api.helper.message;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * @author: NoyZys on 19:50, 19.10.2019
 **/
public final class StringPlayerMessageHelper {

    @NotNull
    @Contract(pure = true)
    StringPlayerMessageHelper(String message) {
        this.message = message;
    }

    private String message;

    public boolean sendMessage(@NotNull CommandSender commandSender) {
        commandSender.sendMessage(this.message);
        return true;
    }
}
