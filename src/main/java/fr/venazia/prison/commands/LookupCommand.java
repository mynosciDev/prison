package fr.venazia.prison.commands;

import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class LookupCommand implements CommandExecutor {

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("This command can only be executed by a player.");
            return true;
        }

        Player p = (Player) commandSender;

        if (!p.hasPermission("modo")) {
            Messages.sendMessage(p, "&8[&6Lookup&8] &cVous n'avez pas la permission d'executer cette commande.");
            return true;
        }

        if (strings.length == 0) {
            Messages.sendMessage(p, "&8[&6Lookup&8] &cUtilisation: &6/lookup <joueur>");
            return true;
        }

        Player p2 = Bukkit.getPlayer(strings[0]);
        if (p2 == null) {
            Messages.sendMessage(p, "§8[§6Lookup§8] §cLe joueur n'est pas en ligne ou n'existe pas.");
            return true;
        }

        String mine = DataUtils.readValue("mine", p2.getUniqueId().toString()).toString();
        String money = DataUtils.readValue("money", p2.getUniqueId().toString()).toString();
        String prestige = DataUtils.readValue("prestige", p2.getUniqueId().toString()).toString();
        String blocs = DataUtils.readValue("blocs", p2.getUniqueId().toString()).toString();

        p.sendMessage("§8[§6Lookup§8] §eLookup de §7" + p2.getName());
        p.sendMessage("§8§m----------------------------------------");
        p.sendMessage("§7Nom: §e" + p2.getName());
        p.sendMessage("§7UUID: §e" + p2.getUniqueId());
        if (p.hasPermission("admin")) {
            p.sendMessage("§7IP: §e" + p2.getAddress().getAddress());
        }
        p.sendMessage("§7Mode de jeu: §e" + p2.getGameMode());
        p.sendMessage("§7Position: §e" + p2.getLocation().getBlockX() + " " + p2.getLocation().getBlockY() + " " + p2.getLocation().getBlockZ());
        p.sendMessage("§7Monde: §e" + p2.getWorld().getName());
        p.sendMessage("§7Connecté depuis: §e" + p2.getFirstPlayed());
        p.sendMessage("§7Dernière connexion: §e" + p2.getLastPlayed());
        p.sendMessage("§7Mine: §e" + mine);
        p.sendMessage("§7Argent: §e" + money);
        p.sendMessage("§7Prestige: §e" + prestige);
        p.sendMessage("§7Blocs minés: §e" + blocs);
        p.sendMessage("§8§m----------------------------------------");
        return true;
    }
}
