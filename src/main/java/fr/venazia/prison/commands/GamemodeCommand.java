package fr.venazia.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GamemodeCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(!(commandSender instanceof Player)){
            Player p = (Player) commandSender;
            if(!p.hasPermission("admin")) {
                p.sendMessage("§cVous n'avez pas la permission d'executer cette commande.");
                return true;
            }
            if(strings.length == 0){
                p.sendMessage("§cUtilisation: /gamemode <0/1/2/3> [joueur]");
                return true;
            }
            if(strings.length == 1){
                if(strings[0].equalsIgnoreCase("0") || strings[0].equalsIgnoreCase("s") || strings[0].equalsIgnoreCase("survival")){
                    p.setGameMode(org.bukkit.GameMode.SURVIVAL);
                    p.sendMessage("§aVotre mode de jeu a été mis en mode survie.");
                } else if(strings[0].equalsIgnoreCase("1") || strings[0].equalsIgnoreCase("c") || strings[0].equalsIgnoreCase("creative")){
                    p.setGameMode(org.bukkit.GameMode.CREATIVE);
                    p.sendMessage("§aVotre mode de jeu a été mis en mode créatif.");
                } else if(strings[0].equalsIgnoreCase("2") || strings[0].equalsIgnoreCase("adventure") || strings[0].equalsIgnoreCase("a")){
                    p.setGameMode(org.bukkit.GameMode.ADVENTURE);
                    p.sendMessage("§aVotre mode de jeu a été mis en mode aventure.");
                } else if(strings[0].equalsIgnoreCase("3") || strings[0].equalsIgnoreCase("spectator") || strings[0].equalsIgnoreCase("sp")){
                    p.setGameMode(org.bukkit.GameMode.SPECTATOR);
                    p.sendMessage("§aVotre mode de jeu a été mis en mode spectateur.");
                } else {
                    p.sendMessage("§cUtilisation: /gamemode <0/1/2/3> [joueur]");
                }
            }
        }
        return false;
    }
}
