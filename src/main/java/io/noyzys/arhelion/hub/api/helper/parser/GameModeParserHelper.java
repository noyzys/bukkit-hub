package dev.noyzys.arhelion.hub.api.helper.parser;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.GameMode;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Optional;

/**
 * @author: NoyZys on 20:54, 19.10.2019
 **/
public final class GameModeParserHelper {

    @NotNull
    @Contract(pure = true)
    private GameModeParserHelper() {
    }

    @NotNull
    private static Optional<GameMode> getGameMode(@NotNull String string) {
        return Arrays
                .stream(GameMode.values())
                .filter(gameMode -> gameMode.name().toLowerCase().contains(string.toLowerCase()))
                .findFirst();
    }

    @NotNull
    public static Optional<GameMode> parseGameMode(@NotNull String string) {
        return StringUtils.isNumeric(string) ?
                Optional.ofNullable(GameMode.getByValue(Integer.parseInt(string))) : getGameMode(string);
    }
}
