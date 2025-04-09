package cn.whohh.fly;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
// import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class MessageUtil {
    private static final MiniMessage miniMessage = MiniMessage.miniMessage();
    private static final LegacyComponentSerializer legacySerializer = 
        LegacyComponentSerializer.builder()
            .hexColors()
            .character('&')
            .build();

    public static void sendMessage(CommandSender sender, String message) {
        // 自动检测消息类型
        if (isMiniMessageFormat(message)) {
            Component component = miniMessage.deserialize(message);
            sender.sendMessage(component);
        } else {
            // 支持传统颜色代码和RGB（&#RRGGBB）
            Component component = legacySerializer.deserialize(message);
            sender.sendMessage(component);
        }
    }

    private static boolean isMiniMessageFormat(String text) {
        // 检测是否有MiniMessage标签特征
        return text.matches(".*<([a-z]+|#[0-9a-fA-F]{6})>.*");
    }
}