package fr.venazia.prison.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

public class ChanceCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand == null || !itemInHand.hasItemMeta()) {
                p.sendMessage("§cVous devez tenir un outil avec des métadonnées dans votre main pour utiliser cette commande !");
                return true;
            }
            ItemMeta meta = itemInHand.getItemMeta();
            NamespacedKey key = new NamespacedKey("prison", "CHANCE");
            if (meta != null && meta.getPersistentDataContainer().has(key, PersistentDataType.STRING)) {
                p.sendMessage("§eChance de l'item: §b" + meta.getPersistentDataContainer().get(key, PersistentDataType.STRING));
            } else {
                p.sendMessage("§cVotre item ne semble pas avoir l'enchantement §e§lChance§c.");
            }
        }
        return true;
    }
}