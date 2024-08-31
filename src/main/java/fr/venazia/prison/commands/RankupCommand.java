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

public class RankupCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender cs, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(cs instanceof Player){
            Player p = (Player) cs;
            try {
                if(DataUtils.readValue("money", p.getUniqueId().toString()) != null){
                    int money = 0;
                    try {
                        money = Integer.parseInt(String.valueOf(DataUtils.readValue("money", p.getUniqueId().toString())));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    if(money >= 1000){
                        DataUtils.writeValue("money", p.getUniqueId().toString(), String.valueOf(money - 1000));
                        Bukkit.broadcastMessage("§b§l⫸ §e " + p.getName() + " §avient de rankup ! §7[§f§lA§7] §f-> §b[§lB§b]");
                        p.sendTitle("\n", "§e§lRankup effectué ! §c(Rip la money)", 20, 100, 20);
                    } else {
                        Messages.sendCentered(p,  "§x§2§F§F§B§F§5§lR§x§3§7§F§C§D§C§lA§x§3§F§F§D§C§3§lN§x§4§7§F§E§A§9§lK§x§4§F§F§F§9§0§lU§x§7§1§B§F§6§C§lP §x§9§3§8§0§4§8§l- §x§B§4§4§0§2§4§lE§x§D§6§0§0§0§0§lR§x§D§D§0§7§0§2§lR§x§E§4§0§F§0§4§lE§x§E§B§1§6§0§6§lU§x§F§2§1§D§0§8§lR");
                        Messages.sendCentered(p, "§cVous n'avez pas assez d'argent pour rankup !");
                        Messages.sendMessage(p, "§cVous avez " + money + "€ §cet vous devez avoir §a1000€ §cpour rankup !");
                    }
                } else {
                    p.sendMessage("§cVous n'avez pas assez d'argent pour rankup !");
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return true;
    }
}
