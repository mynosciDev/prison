package fr.venazia.prison.commands;

import fr.venazia.prison.utils.ItemBuilder;
import fr.venazia.prison.utils.MarketUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.Mark;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;

public class MarketCommand {

    /**
     * Retravaillé le 22/10/2024
     */

    @Command({"market", "hdv", "hdv2", "hoteldevente", "shopjoueur"})
    public void market(CommandSender sender, @Optional @Named("args") String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0 || args[0].equalsIgnoreCase("open") || args[0].equalsIgnoreCase("ouvrir") || args.length == 0) {
                inv(p);
            }
        } else {
            sender.sendMessage("§cVous devez être un joueur pour faire ceci.");
        }
    }

    private void inv(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§2Marché §8- §7HDV");
        ItemStack item = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName("§7")
                .addEnchant(Enchantment.UNBREAKING, 1)
                .toItemStack();
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, item);
        }
        for (int i = 9; i < 44; i++) {
            try {
                Bukkit.broadcastMessage("i: " + i);
                Bukkit.broadcastMessage("Item: " + MarketUtils.getItem(i));
                inv.setItem(i, MarketUtils.getItem(i));
                Bukkit.broadcastMessage(item.toString());
            } catch (NullPointerException e) {
                inv.setItem(i, new ItemBuilder(Material.BARRIER)
                        .setName("§cIl n'y a pas d'items en vente ici !")
                        .toItemStack());
            }
            for (int i2 = 45; i < 54; i++) {
                inv.setItem(i2, item);
            }
            p.openInventory(inv);
        }
    }

}
