package fr.venazia.prison.commands;

import fr.venazia.prison.utils.ItemBuilder;
import fr.venazia.prison.utils.MarketUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.error.Mark;

public class MarketCommand implements CommandExecutor, Listener {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player p) {
            if(strings.length == 1){
                MarketUtils.sellItem(p, p.getInventory().getItemInMainHand(), Integer.parseInt(strings[0]));
                return true;
            }
            inv(p);
        } else {
            commandSender.sendMessage("You must be a player to execute this command");
        }
        return true;
    }

    private void inv(Player p) {
        Inventory inv = Bukkit.createInventory(null, 54, "§2Marché §8- §7HDV");
        ItemStack item = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).displayname("Test").build();
        for (int i = 0; i < 9; i++) {
            inv.setItem(i, item);
        }
        for (int i = 9; i < 44; i++) {
            try {
                Bukkit.broadcastMessage("i: " + i);
                Bukkit.broadcastMessage("Item: " + MarketUtils.getItem(i));
                inv.setItem(i, MarketUtils.getItem(i));
            } catch (NullPointerException e) {
                inv.setItem(i, new ItemBuilder(Material.BARRIER).displayname("§cIl n'y a pas d'items en vente ici !").build());
            }
            for (int i2 = 45; i < 54; i++) {
                inv.setItem(i2, item);
            }
            p.openInventory(inv);
        }
    }

}
