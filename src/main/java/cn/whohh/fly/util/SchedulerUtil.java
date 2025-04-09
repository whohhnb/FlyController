package cn.whohh.fly.util;

import org.bukkit.Bukkit;
// import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public class SchedulerUtil {
    public static void runTask(Plugin plugin, Runnable task) {
        if (CoreDetector.isFolia()) {
            Bukkit.getGlobalRegionScheduler().run(plugin, t -> task.run());
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }

    public static void runDelayed(Plugin plugin, Runnable task, long delayTicks) {
        if (CoreDetector.isFolia()) {
            Bukkit.getGlobalRegionScheduler().runDelayed(plugin, t -> task.run(), delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
        }
    }

    public static void runAtEntity(Entity entity, Plugin plugin, Runnable task) {
        if (CoreDetector.isFolia()) {
            entity.getScheduler().run(plugin, t -> task.run(), null);
        } else {
            Bukkit.getScheduler().runTask(plugin, task);
        }
    }
}