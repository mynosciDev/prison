package fr.venazia.prison.commands;

import fr.venazia.prison.utils.Messages;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class PrisonCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 0) {
                Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
                Messages.sendMessage(p, "§6§lPrison §eby §bmynosci");
                if (p.hasPermission("admin")) {
                    Messages.sendMessage(p, "§fFait la commande §c/prison admin §fpour voir les commandes d'administration.");
                } else {
                    Messages.sendMessage(p, "§cVous n'avez pas la permission d'utiliser cette commande.");
                }
                    Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
                }
            }
        return true;
    }

    List<String> arguments = new ArrayList<>();


    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if(commandSender.hasPermission("admin")) {
            arguments.add("admin");
        }

        return arguments;
    }
}
