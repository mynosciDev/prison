package fr.venazia.prison.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class BroadcastCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0){
            commandSender.sendMessage("§cUtilisation: /broadcast <message>");
        } else {
            StringBuilder message = new StringBuilder();
            for (String string : strings) {
                message.append(string).append(" ");
            }
            if(commandSender instanceof Player) {
                Player p = (Player) commandSender;
                String n = p.getName();
                if(n.equalsIgnoreCase("mynosci")){
                    String message2 = ChatColor.translateAlternateColorCodes('&', String.valueOf(message));
                    commandSender.getServer().broadcastMessage("§8[§6Annonce§8] §7" + message2);
                    return true;
                }
            }
            String message2 = ChatColor.translateAlternateColorCodes('&', String.valueOf(message));
            commandSender.sendMessage("§8[§6Annonce§8] §7" + message2);
        }
        return false;
    }
}
