package fr.venazia.prison.commands;

import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class DarkMarketCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0) {
            commandSender.sendMessage("§cUtilisation: /darkmarket <joueur>");
            return false;
        }
        String player = strings[0];
        Player p = Bukkit.getPlayer(player);
        if(p == null) {
            commandSender.sendMessage("§cLe joueur n'est pas connecté.");
            return false;
        }
        boolean finishQuest = false;
        if(finishQuest){
            commandSender.sendMessage("§cLe joueur a déjà terminé la quête.");
            return false;
        } else {
            Messages.sendMessage(p, "&7&l[&6&lQuête&7&l] &e&lDarkMarket &7&l» &eRendez-vous au &6Port &epour commencer la quête.");
        }
        return false;
    }
}
