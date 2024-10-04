package fr.venazia.prison.commands;

import fr.venazia.prison.utils.DataUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class MoneyCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("§cCette commande ne peut être exécutée que par un joueur.");
            return true;
        }

        Player p = (Player) commandSender;
        double money = 0;

        if (strings.length == 0) {
            money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", p.getUniqueId().toString())));
            p.sendMessage("§x§7§8§F§B§0§5§lM§x§7§3§F§C§0§D§lo§x§6§E§F§C§1§6§ln§x§6§9§F§D§1§E§ln§x§6§4§F§D§2§7§la§x§5§F§F§E§2§F§li§x§5§A§F§E§3§8§le §x§5§5§F§F§4§0§l» §eVous possédez actuellement §b" + money + "§e€ !");
            return true;
        }

        if (strings.length == 1) {
            String get = strings[0];
            String moneyString = DataUtils.readValue("money", get).toString();
            if (moneyString != null) {
                money = Double.parseDouble(moneyString);
                p.sendMessage("§x§7§8§F§B§0§5§lM§x§7§3§F§C§0§D§lo§x§6§E§F§C§1§6§ln§x§6§9§F§D§1§E§ln§x§6§4§F§D§2§7§la§x§5§F§F§E§2§F§li§x§5§A§F§E§3§8§le §x§5§5§F§F§4§0§l» §eLe joueur §b" + get + " §epossède actuellement §b" + money + "§e€ !");
            } else {
                p.sendMessage("§cLe joueur §b" + get + " §cn'existe pas !");
            }
            return true;
        }

        if (strings.length == 3) {
            String get = strings[0];
            Player p2 = Bukkit.getPlayer(get);
            if (p2 == null) {
                p.sendMessage("§cLe joueur §b" + get + " §cn'est pas connecté ou n'est jamais venu sur le serveur !");
                return true;
            }

            if (strings[1].equalsIgnoreCase("add")) {
                money = Integer.parseInt(String.valueOf(DataUtils.readValue("money", p2.getUniqueId().toString())));
                double add = Integer.parseInt(strings[2]);
                DataUtils.writeValue("money", p2.getUniqueId().toString(), String.valueOf(money + add));
                p.sendMessage("§x§7§8§F§B§0§5§lM§x§7§3§F§C§0§D§lo§x§6§E§F§C§1§6§ln§x§6§9§F§D§1§E§ln§x§6§4§F§D§2§7§la§x§5§F§F§E§2§F§li§x§5§A§F§E§3§8§le §x§5§5§F§F§4§0§l» §eVous avez ajouté §b" + add + "§e€ à §b" + get + "§e !");
                p2.sendMessage("§x§7§8§F§B§0§5§lM§x§7§3§F§C§0§D§lo§x§6§E§F§C§1§6§ln§x§6§9§F§D§1§E§ln§x§6§4§F§D§2§7§la§x§5§F§F§E§2§F§li§x§5§A§F§E§3§8§le §x§5§5§F§F§4§0§l» §eVous avez reçu §b" + add + "§e€ de §b" + p.getName() + "§e !");
                return true;
            }
        }

        return false;
    }
}
