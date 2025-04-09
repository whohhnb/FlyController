package cn.whohh.fly;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
// import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class FlyTabCompleter implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        List<String> completions = new ArrayList<>();
        
        if (args.length == 1) {
            completions.add("help");
            if (sender.hasPermission("fly.reload")) completions.add("reload");
            if (sender.hasPermission("fly.all")) completions.add("all");
            if (sender.hasPermission("fly.others")) {
                Bukkit.getOnlinePlayers().forEach(p -> completions.add(p.getName()));
            }
        }
        return completions;
    }
}