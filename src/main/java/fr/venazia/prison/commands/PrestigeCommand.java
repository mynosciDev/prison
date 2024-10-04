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
import java.util.HashMap;

public class PrestigeCommand implements CommandExecutor {


    private final HashMap<Boolean, Player> PS_COOLDOWNS = new HashMap<>();
    private final HashMap<Boolean, Player> PS_CMDWAIT = new HashMap<>();



    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            Object prestige = null;
            Object next = null;
            Object money = null;
            Object price = null;
            money = DataUtils.readValue("money", p.getUniqueId().toString());
            if (money == null) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération de l'économie pour le joueur &2" + p.getName() + " &cNULL_AT_TRY (GetMoney) ORDRE_GETMONEY");
                return true;
            }
            prestige = DataUtils.readValue("prestige", p.getUniqueId().toString());
            if (prestige == null) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrestige) ORDRE_GETPRESTIGE");
                return true;
            }
            price = DataUtils.readPrestigeValue(prestige.toString(), "prix");
            if (price == null) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix) ORDRE_GETPRIX");
                return true;
            }
            next = DataUtils.readPrestigeValue(prestige.toString(), "next");
            if (next == null) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetNext) ORDRE_GETNEXT");
                return true;
            }
            try {
                money = Integer.valueOf(money.toString());
                price = Integer.valueOf(price.toString());
            } catch (NumberFormatException e) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix) ORDRE_GETPRIX");
                return true;
            }
            if (!PS_CMDWAIT.containsValue(true)) {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &e&lATTENTION! &eLorsque vous serez passé prestige, votre argent et votre mine sera rénitialisé à 0. Merci de refaire la commande si vous confirmez cette action.");
                PS_CMDWAIT.put(true, p);
                return true;
            } else {
                Integer money2;
                Integer price2;
                try {
                    money2 = Integer.parseInt(money.toString());
                    price2 = Integer.parseInt(price.toString());
                } catch (NumberFormatException e) {
                    Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix) ORDRE_GETPRIX");
                    return true;
                }
                if (money2 >= price2) {
                    DataUtils.writeValue("prestige", p.getUniqueId().toString(), next.toString());
                    DataUtils.writeValue("money", p.getUniqueId().toString(), String.valueOf((int) money - (int) price));
                    DataUtils.writeValue("mine", p.getUniqueId().toString(), "A");
                    Messages.sendMessage(p, "&8[&dPrestiges&8] &aVous avez passé prestige avec succès !");
                    Bukkit.broadcastMessage("§d§l⫸ §6 " + p.getName() + " §fvient de passer §dprestige§f ! §7[§f§lP" + prestige + "§7] §f-> §6[§a§lP" + next + "§6]");
                    PS_CMDWAIT.remove(true);
                    return true;
                } else {
                    Messages.sendMessage(p, "&8[&dPrestiges&8] &cVous n'avez pas assez d'argent pour passer prestige !");
                    return true;
                }
            }
        }
        return true;
    }
}
