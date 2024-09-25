package fr.venazia.prison.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class AcceptQuestCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0) {
            commandSender.sendMessage("§cUtilisation: /acceptquest <joueur> <id> <true/false>");
            return false;
        }
        String player = strings[0];
        if(strings.length < 2) {
            commandSender.sendMessage("§cUtilisation: /acceptquest <joueur> <id> <true/false>");
            return false;
        }
        Integer id = Integer.valueOf(strings[1]);
        if(strings.length < 3) {
            commandSender.sendMessage("§cUtilisation: /acceptquest <joueur> <id> <true/false>");
            return false;
        }
        String accept = strings[2];
        Player p = Bukkit.getPlayer(player);
        String questName = "";
        if(id == 1){
            questName = "§bUn air d'illégal";
        }
        p.sendTitle("§b§lQuête acceptée", "§7Vous avez accepté la quête " + questName);
        return false;
    }
}
