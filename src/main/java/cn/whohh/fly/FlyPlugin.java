package cn.whohh.fly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import cn.whohh.fly.util.ConsoleColor;
import cn.whohh.fly.util.SchedulerUtil;

import java.util.List;
import java.util.Objects;

public class FlyPlugin extends JavaPlugin implements Listener {

    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        reloadCustomConfig();
        getServer().getPluginManager().registerEvents(this, this);
        // 注册Tab补全
        Objects.requireNonNull(getCommand("fly")).setTabCompleter(new FlyTabCompleter());
        String version = this.getDescription().getVersion();
        printFormattedLoadInfo();
    }

    private void printFormattedLoadInfo() {
        String version = this.getDescription().getVersion();
        String formatted = ConsoleColor.convertToANSI(
                "§a╔══════════════════════════════╗\n" +
                        "§a║  §6§lFlyController §a§lv" + version + "        §a║\n" + // 修正版本号空格
                        "§a║  §7作者：§e§lwhohh                 §a║\n" + // 使用全角冒号
                        "§a╚══════════════════════════════╝");

        // 通过Bukkit日志系统输出（保留[时间][INFO][插件名]前缀）
        getLogger().info("\n " + formatted.replace("\n", "\n "));
    }

    public void reloadCustomConfig() {
        reloadConfig();
        config = getConfig();
        config.options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label,
            String[] args) {
        if (!command.getName().equalsIgnoreCase("fly"))
            return false;

        if (args.length == 0) {
            handleSelfToggle(sender);
        } else {
            switch (args[0].toLowerCase()) {
                case "reload" -> handleReload(sender);
                case "all" -> handleAllToggle(sender);
                case "help" -> sendHelp(sender);
                default -> handleOtherPlayer(sender, args[0]);
            }
        }
        return true;
    }

    private void handleSelfToggle(CommandSender sender) {
        if (!(sender instanceof Player player)) {
            sendMessage(sender, "player-only");
            return;
        }

        if (!checkPermission(sender, "toggle"))
            return;
        if (!validateWorld(player))
            return;

        toggleFlight(player, true);
    }

    private void handleOtherPlayer(CommandSender sender, String targetName) {
        if (!checkPermission(sender, "others"))
            return;

        Player target = Bukkit.getPlayer(targetName);
        if (target == null) {
            sendMessage(sender, "player-not-found");
            return;
        }

        boolean newState = !target.getAllowFlight();
        setFlight(target, newState, true);
        sendMessage(sender, newState ? "fly-enabled-other" : "fly-disabled-other", "%player%", target.getName());
    }

    private void handleAllToggle(CommandSender sender) {
        if (!checkPermission(sender, "all"))
            return;

        boolean newState = Bukkit.getOnlinePlayers().stream()
                .filter(p -> validateWorld(p) || p.hasPermission("fly.bypass"))
                .anyMatch(p -> !p.getAllowFlight());
        SchedulerUtil.runTask(this, () -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (validateWorld(p) || p.hasPermission("fly.bypass")) {
                    setFlight(p, newState, false);
                }
            }
        });
        Bukkit.getGlobalRegionScheduler().run(this, task -> {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (validateWorld(p) || p.hasPermission("fly.bypass")) {
                    setFlight(p, newState, false);
                }
            }
        });

        sendMessage(sender, newState ? "all-enabled" : "all-disabled");
    }

    private void handleReload(CommandSender sender) {
        if (!checkPermission(sender, "reload"))
            return;
        reloadCustomConfig();
        sendMessage(sender, "reload-success");
    }

    private void toggleFlight(Player player, boolean sendMessage) {
        boolean newState = !player.getAllowFlight();
        setFlight(player, newState, sendMessage);
    }

    private void setFlight(Player player, boolean state, boolean sendMessage) {
        SchedulerUtil.runAtEntity(player, this, () -> {
            player.setAllowFlight(state);
            if (state)
                player.setFlying(true);

            if (sendMessage) {
                sendMessage(player, state ? "fly-enabled" : "fly-disabled");
            }
        });
    }

    private boolean validateWorld(Player player) {
        List<String> enabledWorlds = config.getStringList("enabled-worlds");
        return enabledWorlds.contains(player.getWorld().getName()) ||
                player.hasPermission("fly.bypass");
    }

    // 玩家加入事件
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (config.getBoolean("join-fly") && player.hasPermission("fly.join")) {
            SchedulerUtil.runDelayed(this, () -> {
                if (validateWorld(player)) {
                    setFlight(player, true, false);
                }
            }, 20L);
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (!config.getBoolean("keep-fly-on-logout")) {
            Player player = event.getPlayer();
            player.getScheduler().run(this, task -> {
                if (player.getAllowFlight()) {
                    setFlight(player, false, false);
                }
            }, null);
        }
    }

    private void sendHelp(CommandSender sender) {
        Component header = Component.text("=== Fly Help ===").color(NamedTextColor.GOLD);
        sender.sendMessage(header);
        sender.sendMessage(Component.text("/fly - Toggle your flight").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("/fly <player> - Toggle others' flight").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("/fly all - Toggle flight for all").color(NamedTextColor.YELLOW));
        sender.sendMessage(Component.text("/fly reload - Reload config").color(NamedTextColor.YELLOW));
    }

    private boolean checkPermission(CommandSender sender, String permission) {
        if (sender.hasPermission("fly." + permission))
            return true;
        sendMessage(sender, "no-permission");
        return false;
    }

    private void sendMessage(CommandSender sender, String key, String... placeholders) {
        String message = config.getString("messages." + key, "");
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        // 使用MiniMessage解析
        MessageUtil.sendMessage(sender, message);
    }
}