package cn.whohh.fly.util;

import org.bukkit.ChatColor;

public class ConsoleColor {
    // 将Minecraft颜色代码转换为ANSI
    public static String convertToANSI(String message) {
        // 仅在支持ANSI的控制台启用颜色
        if (isANSISupported()) {
            return message.replace("§", "\033[")
                .replace("0", "0m")
                .replace("1", "1m")
                .replace("2", "2m")
                .replace("3", "3m")
                .replace("4", "4m")
                .replace("5", "5m")
                .replace("6", "6m")
                .replace("7", "7m")
                .replace("8", "8m")
                .replace("9", "9m")
                .replace("a", "32m")  // 绿色
                .replace("b", "36m")  // 青色
                .replace("c", "31m")  // 红色
                .replace("d", "35m")  // 品红
                .replace("e", "33m")  // 黄色
                .replace("f", "37m")  // 白色
                .replace("k", "8m")   // 随机（无法转换，忽略）
                .replace("l", "1m")   // 粗体
                .replace("m", "9m")   // 删除线
                .replace("n", "4m")   // 下划线
                .replace("o", "3m")   // 斜体
                .replace("r", "0m");  // 重置
        } else {
            return ChatColor.stripColor(message); // 移除颜色代码
        }
    }

    // 检测是否支持ANSI颜色
    private static boolean isANSISupported() {
        // 检查环境变量和操作系统
        return System.console() != null 
            && !System.getProperty("os.name").startsWith("Windows")
            && !System.getProperty("java.specification.vendor").contains("android");
    }
}