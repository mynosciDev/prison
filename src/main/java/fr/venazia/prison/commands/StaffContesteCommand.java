package fr.venazia.prison.commands;

import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class StaffContesteCommand implements CommandExecutor, Listener {

    public static HashMap<Player, Integer> contest = new HashMap<>();

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(strings.length == 0){
            commandSender.sendMessage("§aSpécification du joueur voulant contester requise");
        } else {
            String a = strings[0];
            Player p = Bukkit.getPlayer(a);
            if(p == null){
                commandSender.sendMessage("§cErreur: Joueur introuvable");
            } else {
                Messages.sendCentered(p, "§x§F§B§0§0§0§0&lC§x§F§C§1§1§0§6&lO§x§F§C§2§2§0§C&lN§x§F§D§3§4§1§2&lT§x§F§D§4§5§1§7&lE§x§F§E§5§6§1§D&lS§x§F§E§6§7§2§3&lT§x§F§F§7§8§2§9&lA§x§F§6§7§E§2§9&lT§x§E§8§7§E§2§7&lI§x§D§A§7§E§2§4&lO§x§C§C§7§E§2§2&lN §x§B§D§7§D§2§0&lD§x§A§F§7§D§1§D&lE §x§A§1§7§D§1§B&lS§x§9§7§8§3§1§F&lA§x§9§2§9§4§3§0&lN§x§8§E§A§6§4§1&lC§x§8§A§B§8§5§3&lT§x§8§6§C§9§6§4&lI§x§8§1§D§B§7§5&lO§x§7§D§E§C§8§7&lN§x§7§9§F§E§9§8&lS");
                Messages.sendCentered(p, "&fContestation de sanction - &eBanni par &6SA (AntiCheat)");
                Messages.sendMessage(p, "§7§m----------------------------------------");
                Messages.sendMessage(p, "§7§lInformations:");
                Messages.sendMessage(p, "§7§l- §eJoueur: §6" + p.getName());
                Messages.sendMessage(p, "§7§l- §eRaison: §6AntiCheat");
                Messages.sendMessage(p, "§7§m----------------------------------------");
                Messages.sendMessage(p, "§7§lContestation:");
                contest.put(p, 1);
                Messages.sendMessage(p, "&2&l» &fEntrez la raison de votre contestation");
            }
        }
        return false;
    }

}
