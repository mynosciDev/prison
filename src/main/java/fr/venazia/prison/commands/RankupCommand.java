package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.IOException;
import java.util.HashMap;

public class RankupCommand {

    /**
     * Retravaillé le 21 octobre 2024
     */

    @Command("rankup")
    public void rankup(BukkitCommandActor a) {
        Player p = a.asPlayer();
        String m;
        String n;
        Integer price;
        m = (String) DataUtils.readValue("mine", p.getUniqueId().toString());
        if (m == null) {
            Messages.sendMessage(p, "&8[&bMines&8] &cErreur lors de la récupération des données de la mine ! Veuillez contacter un administrateur. NULL_AT_TRY (GetMine)");
            return;
        }
        n = (String) DataUtils.readMineValue(m, "next");
        if (n == null) {
            Messages.sendMessage(p, "&8[&bMines&8] &cErreur lors de la récupération des données de la mine §6" + m + " §c! Veuillez contacter un administrateur. NULL_AT_TRY (GetNext)");
            return;
        }
        price = Integer.valueOf(DataUtils.readMineValue(m, "prix").toString());
        if (price == null) {
            Messages.sendMessage(p, "&8[&bMines&8] &cErreur lors de la récupération des données de la mine ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix)");
            return;
        }
        if (m == null || n == null) {
            Messages.sendMessage(p, "&8[&bMines&8] &cErreur lors de la récupération des données de la mine ! Veuillez contacter un administrateur.");
            return;
        }
        if (price <= 0) {
            Messages.sendMessage(p, "&8[&bMines&8] &cErreur lors de la récupération du prix de la mine ! Veuillez contacter un administrateur.");
        } else {
            if (m.equals("z".toUpperCase())) {
                Messages.sendMessage(p, "&e&oVous avez atteint le dernier grade disponible ! Faites &6/prestige &epour augmenter votre prestige !");
                return;
            }
            if (DataUtils.readValue("money", p.getUniqueId().toString()) != null) {
                double money = 0;
                money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", p.getUniqueId().toString())));
                if (money >= price) {
                    DataUtils.writeValue("mine", p.getUniqueId().toString(), n);
                    DataUtils.writeValue("money", p.getUniqueId().toString(), String.valueOf(money - price));
                    Bukkit.broadcastMessage("§b§l⫸ §e " + p.getName() + " §avient de rankup ! §7[§f§l" + m + "§7] §f-> §b[§l" + n + "§b]");
                    p.sendTitle("\n", "§e§lRankup effectué ! §c(Rip la money)", 20, 100, 20);
                } else {
                    Messages.sendMessage(p, "§cVous n'avez pas assez d'argent pour rankup !");
                    Messages.sendMessage(p, "§cVous avez " + money + "€ §cet vous devez avoir §a " + price + "€ §cpour rankup !");
                }
            }
        }
    }



    private final HashMap<Boolean, Player> PS_COOLDOWNS = new HashMap<>();
    private final HashMap<Boolean, Player> PS_CMDWAIT = new HashMap<>();

    @Command("prestige")
    @Description("Passer au prestige suivant.")
    public void cmd(BukkitCommandActor a) {
        Player p = a.asPlayer();
        Object prestige = null;
        Object next = null;
        Object money = null;
        Object price = null;
        money = DataUtils.readValue("money", p.getUniqueId().toString());
        if (money == null) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération de l'économie pour le joueur &2" + p.getName() + " &cNULL_AT_TRY (GetMoney) ORDRE_GETMONEY");
            return;
        }
        prestige = DataUtils.readValue("prestige", p.getUniqueId().toString());
        if (prestige == null) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrestige) ORDRE_GETPRESTIGE");
            return;
        }
        price = DataUtils.readPrestigeValue(prestige.toString(), "prix");
        if (price == null) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix) ORDRE_GETPRIX");
            return;
        }
        next = DataUtils.readPrestigeValue(prestige.toString(), "next");
        if (next == null) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetNext) ORDRE_GETNEXT");
            return;
        }
        try {
            money = Double.parseDouble(money.toString());
            price = Integer.valueOf(price.toString());
        } catch (NumberFormatException e) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &cErreur lors de la récupération des données de prestige ! Veuillez contacter un administrateur. NULL_AT_TRY (GetPrix) ORDRE_GETPRIX");
            return;
        }
        if (!PS_CMDWAIT.containsValue(p)) {
            Messages.sendMessage(p, "&8[&dPrestiges&8] &e&lATTENTION! &eLorsque vous serez passé prestige, votre argent et votre mine sera rénitialisé à 0. Merci de refaire la commande si vous confirmez cette action.");
        }
        if (money instanceof Number && price instanceof Number) {
            double moneyValue = ((Number) money).doubleValue();
            double priceValue = ((Number) price).doubleValue();

            if (moneyValue >= priceValue) {
                DataUtils.writeValue("prestige", p.getUniqueId().toString(), next.toString());
                DataUtils.writeValue("money", p.getUniqueId().toString(), String.valueOf((int) moneyValue - (int) priceValue));
                DataUtils.writeValue("mine", p.getUniqueId().toString(), "A");
                Messages.sendMessage(p, "&8[&dPrestiges&8] &aVous avez passé prestige avec succès !");
                Bukkit.broadcastMessage("§d§l⫸ §5§lUn Immense GG à §6 " + p.getName() + " §fqui vient de passer §dprestige§f ! §7[§f§lP" + prestige + "§7] §f-> §6[§a§lP" + next + "§6]");
                PS_CMDWAIT.remove(true);
            } else {
                Messages.sendMessage(p, "&8[&dPrestiges&8] &cVous n'avez pas assez d'argent pour passer prestige !");
            }
        }
    }
}