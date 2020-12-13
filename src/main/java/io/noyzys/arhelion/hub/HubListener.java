package dev.noyzys.arhelion.hub;

import dev.noyzys.arhelion.hub.api.helper.builder.ItemStackBuilderHelper;
import dev.noyzys.arhelion.hub.api.helper.message.StringMessageHelper;
import dev.noyzys.arhelion.hub.command.SlotCommand;
import dev.noyzys.arhelion.hub.command.WhiteListCommand;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.potion.PotionEffect;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: NoyZys on 17:44, 21.10.2019
 **/
class HubListener implements Listener {

    private final HubPlugin plugin;
    private FileConfiguration configuration;

    //<-- player join configuration -->
    private final String listener_playerjoin_joinmessage;
    private final boolean listener_playerjoin_status_joinmessage;
    private final boolean listener_playerjoin_status_send_clear_message;
    private final List<String> listener_playerjoin_joinlistmessage;

    //<-- status title, subtitle packet message -->
    private final boolean listener_playerjoin_joinmessage_titles_status;
    private final String listener_playerjoin_joinmessage_title;
    private final String listener_playerjoin_joinmessage_subtitle;

    //<-- main function player join configuration -->
    private final int listener_playerjoin_setslothelditem;
    private final boolean listener_playerjoin_status_allowflight;
    private final boolean listener_playerjoin_root_join_nickname_status;
    private final List<String> listener_playerjoin_root_join_nickname;
    private final String listener_playerjoin_root_join;

    //<-- player quit configuration -->
    private final String listener_playerquit_quitmessage;
    private final boolean listener_playerquit_status_quitmessage;

    //<-- prefix configuration -->
    private final String listener_prefix_options_prefix;

    //<-- blocking interact configuration -->
    private final String listener_blockinginteract_sendonchat;
    private final String listener_blockinginteract_blockbreak;
    private final String listener_blockinginteract_blockplace;
    private final String listener_blockinginteract_dropitem;
    private final String listener_blockinginteract_unkowncommand;
    private final String listener_blockinginteract_commandscancel;
    private final String listener_blockinginteract_damage;
    private final List<String> listener_blockinginteract_commandsallow;
    private final String listener_blockinginteract_commandsallow_message;

    //<-- item selection mode configuration -->
    private final int listener_itemselectionmode_materialitem;
    private final int listener_itemselectionmode_slot_in_inventory_player;
    private final String listener_itemselectionmode_name;
    private final List<String> listener_itemselectionmode_lore;

    //<-- player join on full server slot, whitelist -->
    private final String listener_asyncprelogin_join_kick_serverisfull;
    private final String listener_playerlogin_join_kick_whitelist_enable;

    HubListener(@NotNull HubPlugin plugin) {
        this.plugin = plugin;
        this.listener_playerjoin_joinmessage = StringMessageHelper.colored(configuration.getString("playerjoin_options.setjoinmessage"));
        this.listener_playerjoin_status_joinmessage = configuration.getBoolean("playerjoin_options.setjoinmessage-status");
        this.listener_playerjoin_status_send_clear_message = configuration.getBoolean("playerjoin_options.sendclearmessage-status");
        this.listener_playerjoin_joinlistmessage = StringMessageHelper.colored(configuration.getStringList("playerjoin_options.sendwelcomemessage"));
        this.listener_playerjoin_joinmessage_title = StringMessageHelper.colored(configuration.getString("playerjoin_options.sendwelcomemessage_title"));
        this.listener_playerjoin_joinmessage_subtitle = StringMessageHelper.colored(configuration.getString("playerjoin_options.sendwelcomemessage_subtitle"));
        this.listener_playerjoin_joinmessage_titles_status = configuration.getBoolean("playerjoin_options.sendwelcomemessage_titles-status");
        this.listener_playerjoin_setslothelditem = configuration.getInt("playerjoin_options.setslothelditem");
        this.listener_playerjoin_status_allowflight = configuration.getBoolean("playerjoin_options.allowfight-status");
        this.listener_playerjoin_root_join_nickname_status = configuration.getBoolean("playerjoin_options.sendrootjoin-status");
        this.listener_playerjoin_root_join_nickname = configuration.getStringList("playerjoin_options.sendrootjoin_nickname");
        this.listener_playerjoin_root_join = StringMessageHelper.colored(configuration.getString("playerjoin_options.sendrootjoin"));
        this.listener_playerquit_quitmessage = StringMessageHelper.colored(configuration.getString("playerquit_options.setquitmessage"));
        this.listener_playerquit_status_quitmessage = configuration.getBoolean("playerquit_options.setquitmessage-status");
        this.listener_prefix_options_prefix = StringMessageHelper.colored(configuration.getString("prefix_options.prefix"));
        this.listener_blockinginteract_sendonchat = StringMessageHelper.colored(configuration.getString("blocking_interact_options.sendonchat"));
        this.listener_blockinginteract_blockbreak = StringMessageHelper.colored(configuration.getString("blocking_interact_options.blockbreak"));
        this.listener_blockinginteract_blockplace = StringMessageHelper.colored(configuration.getString("blocking_interact_options.blockplace"));
        this.listener_blockinginteract_dropitem = StringMessageHelper.colored(configuration.getString("blocking_interact_options.dropitem"));
        this.listener_blockinginteract_unkowncommand = StringMessageHelper.colored(configuration.getString("blocking_interact_options.unkowncommand"));
        this.listener_blockinginteract_commandscancel = StringMessageHelper.colored(configuration.getString("blocking_interact_options.cancelcommands"));
        this.listener_blockinginteract_damage = StringMessageHelper.colored(configuration.getString("blocking_interact_options.damage"));
        this.listener_blockinginteract_commandsallow = StringMessageHelper.colored(configuration.getStringList("blocking_interact_options.commandallow"));
        this.listener_blockinginteract_commandsallow_message = StringMessageHelper.colored(configuration.getString("blocking_interact_options.commandsallow_message"));
        this.listener_itemselectionmode_materialitem = configuration.getInt("item_selection_mode_options.material");
        this.listener_itemselectionmode_slot_in_inventory_player = configuration.getInt("item_selection_mode_options.slot_in_inventory");
        this.listener_itemselectionmode_name = StringMessageHelper.colored(configuration.getString("item_selection_mode.name"));
        this.listener_itemselectionmode_lore = StringMessageHelper.colored(configuration.getStringList("item_selection_mode.lore"));
        this.listener_asyncprelogin_join_kick_serverisfull = StringMessageHelper.colored(configuration.getString("messages_configuration.slot_options.kick_reason_serverisfull"));
        this.listener_playerlogin_join_kick_whitelist_enable = StringMessageHelper.colored(configuration.getString("messages_configuration.whitelist_options.kick_reason_whitelist_enable"));
    }

    @EventHandler
    public void onJoinFunction(PlayerJoinEvent event) {
        final Player player = event.getPlayer();

        this.mainFunctionPlayerJoin(player);
        this.buildItemModeSelection(player);
        this.rootJoinServer(player);

        if (this.listener_playerjoin_status_joinmessage) event.setJoinMessage(this.listener_playerjoin_joinmessage
                .replace("{PLAYER}", player.getName())
        );
        if (this.listener_playerjoin_status_send_clear_message) StringMessageHelper.sendEmptyMessage(player);

        final String messageJoin = this.listener_playerjoin_joinlistmessage
                .stream()
                .map(s -> s = s.replace("{PLAYER}", player.getName()))
                .collect(Collectors.joining("\n")
                );
        StringMessageHelper.create(messageJoin)
                .sendMessage(player);

        if (this.listener_playerjoin_joinmessage_titles_status)
            player.sendTitle(this.listener_playerjoin_joinmessage_title, this.listener_playerjoin_joinmessage_subtitle
                    .replace("{PLAYER}", player.getName())
            );
    }

    @EventHandler
    public void onQuitFunction(PlayerQuitEvent event) {
        final Player player = event.getPlayer();
        if (this.listener_playerquit_status_quitmessage) {
            event.setQuitMessage(this.listener_playerquit_quitmessage
                    .replace("{PLAYER}", player.getName())
            );
        }
    }

    @EventHandler
    public void onFunctionAsyncPlayerChat(AsyncPlayerChatEvent event) {
        final Player player = event.getPlayer();
        if (player.isOp()) {
            event.setFormat(StringMessageHelper.colored(this.listener_prefix_options_prefix
                    .replace("{ADMIN}", player.getName()
                            .replace("{MESSAGE}", event.getMessage())))
            );
            return;
        } else StringMessageHelper
                .create(this.listener_blockinginteract_sendonchat)
                .sendMessage(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctionBlockBreak(BlockBreakEvent event) {
        final Player player = event.getPlayer();
        if (player.isOp()) return;
        else StringMessageHelper
                .create(this.listener_blockinginteract_blockbreak)
                .sendMessage(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctionBlockBreak(BlockPlaceEvent event) {
        final Player player = event.getPlayer();
        if (player.isOp()) return;
        else StringMessageHelper
                .create(this.listener_blockinginteract_blockplace)
                .sendMessage(player);
        event.setBuild(true);
    }

    @EventHandler
    public void onFunctionPlayerDropItem(PlayerDropItemEvent event) {
        final Player player = event.getPlayer();
        if (player.isOp()) return;
        else StringMessageHelper
                .create(this.listener_blockinginteract_dropitem)
                .sendMessage(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctionPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        final Player player = event.getPlayer();
        final String command = event.getMessage().toLowerCase().split(" ")[0];
        if (Bukkit.getServer().getHelpMap().getHelpTopic(event.getMessage()
                .split(" ")[0]) == null) event.setCancelled(true);
        StringMessageHelper
                .create(this.listener_blockinginteract_unkowncommand)
                .sendMessage(player);

        this.listener_blockinginteract_commandsallow
                .stream()
                .filter(s -> command.equalsIgnoreCase("/" + s))
                .forEachOrdered(s -> {
                    event.setCancelled(true);
                    StringMessageHelper
                            .create(this.listener_blockinginteract_commandsallow_message)
                            .sendMessage(player);
                });
        if (player.isOp()) return;
        else StringMessageHelper
                .create(this.listener_blockinginteract_commandscancel)
                .sendMessage(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctioEntityDamageByEntity(EntityDamageByEntityEvent event) {
        final Player player = (Player) event.getDamager();
        if (!(event.getDamager() instanceof Player) || !(event.getEntity() instanceof Player)) return;
        StringMessageHelper
                .create(this.listener_blockinginteract_damage)
                .sendMessage(player);
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctionAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent event) {
        if (this.plugin.getServer()
                .getOnlinePlayers()
                .size() >= SlotCommand.getSlot_max())
            event.disallow(AsyncPlayerPreLoginEvent.Result.KICK_FULL, StringMessageHelper
                    .colored(this.listener_asyncprelogin_join_kick_serverisfull
                            .replace("{N}", "\n"))
            );
    }

    @EventHandler
    public void onFunctionPlayerLogin(PlayerLoginEvent event) {
        final Player player = event.getPlayer();
        if (!WhiteListCommand.getWhitelist_list_players_onwhitelist().contains(player.getName()) && WhiteListCommand.isWhitelist_status()) event.disallow(PlayerLoginEvent.Result.KICK_OTHER, StringMessageHelper
                .colored(this.listener_playerlogin_join_kick_whitelist_enable
                        .replace("{N}", "\n"))
            );
    }

    @EventHandler
    public void onFunctionEntityDamage(EntityDamageEvent event) {
        event.setCancelled(true);
    }

    @EventHandler
    public void onFunctionWeatherChange(WeatherChangeEvent event) {
        if (event.toWeatherState()) return;
        event.setCancelled(true);
        event.getWorld().setWeatherDuration(0);
        event.getWorld().setThundering(false);
    }

    private void mainFunctionPlayerJoin(@NotNull Player player) {
        player.teleport(Bukkit.getWorlds().get(0).getSpawnLocation());
        player.getInventory().setHeldItemSlot(this.listener_playerjoin_setslothelditem);
        player.setAllowFlight(this.listener_playerjoin_status_allowflight);
        player.setExp(Bukkit.getOnlinePlayers().size());
        player.setHealth(20);
        player.setFoodLevel(20);
        player.getInventory().clear();
        player.setGameMode(GameMode.SURVIVAL);
        player.getInventory().setArmorContents(null);
        player.getWorld().setMonsterSpawnLimit(0);
        player.setFlying(false);
        player.getActivePotionEffects()
                .stream()
                .map(PotionEffect::getType)
                .forEachOrdered(player::removePotionEffect);
    }

    private void buildItemModeSelection(@NotNull Player player) {
        //<-- build item mode selection -->
        final ItemStackBuilderHelper icon = new ItemStackBuilderHelper(Material.getMaterial(this.listener_itemselectionmode_materialitem), 1)
                .setName(StringMessageHelper.colored(this.listener_itemselectionmode_name), true)
                .setLore(StringMessageHelper.colored(this.listener_itemselectionmode_lore)
                );
        player.getInventory().setItem(this.listener_itemselectionmode_slot_in_inventory_player, icon.toBuild());
        player.updateInventory();
    }

    private void rootJoinServer(@NotNull Player player) {
        if (this.listener_playerjoin_root_join_nickname_status)
            if (this.listener_playerjoin_root_join_nickname.contains(player.getName())) return;
        Bukkit.getOnlinePlayers().forEach(players -> StringMessageHelper
            .create(this.listener_playerjoin_root_join
            .replace("{ADMIN}", player.getName()))
            .sendMessage(players));
    }
}
