package dev.noyzys.arhelion.hub.command;

import dev.noyzys.arhelion.hub.api.helper.command.CommandExtender;
import dev.noyzys.arhelion.hub.api.helper.command.CommandExtenderInfo;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @author: NoyZys on 17:18, 21.10.2019
 **/
@CommandExtenderInfo(
        name = "Slot"
)
public class SlotCommand extends CommandExtender {

    private final String slot_permission;
    private final String slot_permission_message;
    private final String slot_usage;
    private static int slot_max;

    private final String slot_it_is_notnumber;
    private final List<String> slot_status;
    public SlotCommand(@NotNull FileConfiguration configuration) {
        this.slot_permission = configuration.getString("messages_configuration.slot_options.permission");
        this.slot_permission_message = StringMessageHelper.colored(configuration.getString("messages_configuration.slot_options.permission_message"));
        this.slot_usage = StringMessageHelper.colored(configuration.getString("messages_configuration.slot_options.usage"));
        slot_max = configuration.getInt("messages_configuration.slot_options.slot_max");
        this.slot_it_is_notnumber = configuration.getString("messages_configuration.slot_options.it_is_notnumber");
        this.slot_status = StringMessageHelper.colored(configuration.getStringList("messages_configuration.slot_options.status_and_set"));
    }

    @Override
    public boolean execute(CommandSender sender, String... args) {
        if (!(sender instanceof Player)) return true;

        final Player player = (Player) sender;
        if (!player.hasPermission(this.slot_permission)) return StringMessageHelper
                .create(this.slot_permission_message)
                .sendMessage(player);

        if (args.length == 0) return StringMessageHelper
                .create(this.slot_usage)
                .sendMessage(player);

        if (!this.isInteger(args[0])) return StringMessageHelper
                .create(this.slot_it_is_notnumber)
                .sendMessage(player);

        int slot = Integer.parseInt(args[0]);
        this.setSlot_max(slot);

        final String message = this.slot_status
                .stream()
                .map(s -> s.replace("{SLOT}", slot_max + "")
                        .replace("{ONLINE}", Bukkit.getOnlinePlayers().size() + ""))
                .collect(Collectors.joining("\n")
                );

        return StringMessageHelper
                .create(message)
                .sendMessage(player);
    }

    private boolean isInteger(@NotNull String string) {
        return Pattern
                .matches("-?[0-9]+", string.subSequence(0, string.length())
                );
    }

    public static int getSlot_max() {
        return slot_max;
    }

    private void setSlot_max(int slot_max) {
        SlotCommand.slot_max = slot_max;
    }
}
