package fr.venazia.prison.commands;

import fr.venazia.prison.utils.ItemBuilder;
import fr.venazia.prison.utils.Messages;
import fr.venazia.prison.utils.Placeholders;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;

public class VoyageCommand implements  Listener{



    @Command({"voyage", "voyager"})
    public void voyage(CommandSender sender, @Named("joueur") String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length == 0) {
                Messages.sendMessage(p, "§8[§bVoyageur§8] §cUsage: /voyage <joueur> [<dimension>]");
            } else if (args.length == 1) {
                String a = args[0];
                Player target = Bukkit.getPlayer(a);
                if (target == null) {
                    Messages.sendMessage(p, "§8[§bVoyageur§8] §cLe joueur " + a + " n'est pas connecté.");
                } else {
                    inv(target);
                }
            } else {
                Messages.sendMessage(p, "§8[§bVoyageur§8] §cUsage: /voyage <joueur> [<dimension>]");
            }
        } else {
            sender.sendMessage("§cVous devez être un joueur pour faire ceci.");
        }
    }

    private void inv(Player p) {
        Inventory inventory = Bukkit.createInventory(null, 27, "§bVoyageur §7- §b" + p.getName());
        ItemStack placeholderItem = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE)
                .setName("§7")
                .addEnchant(Enchantment.SHARPNESS, 1).toItemStack();

        int endTravelsRemaining = 5 - Placeholders.endTravelsRemaining.get(p);
        int netherTravelsRemaining = 5 - Placeholders.netherTravelsRemaining.get(p);

        ItemStack endItem = new ItemBuilder(Material.ENDER_PEARL)
                .setName("§#CB2D3E§lN§#D1313D§le§#D7363D§lt§#DD3A3C§lh§#E33E3B§le§#E9433B§lr§#EF473A§l")
                .setLore("§7Cliquez pour vous téléporter à l'§#1488CC§lE§#1982A7§ln§#1F7D83§ld§#24775E§l\n§7Il vous reste §a" + endTravelsRemaining + " §7téléportations restantes. §cCeci se rénitialise toutes le 24h.")
                .toItemStack();

        ItemStack netherItem = new ItemBuilder(Material.NETHERITE_SWORD)
                .setName("§#CB2D3E§lN§#D1313D§le§#D7363D§lt§#DD3A3C§lh§#E33E3B§le§#E9433B§lr§#EF473A§l")
                .setLore("§7Cliquez pour vous téléporter au §#CB2D3E§lN§#D1313D§le§#D7363D§lt§#DD3A3C§lh§#E33E3B§le§#E9433B§lr§#EF473A§l \n§7Il vous reste §a" + netherTravelsRemaining + " §7téléportations restantes. §cCeci se rénitialise toutes le 24h.")
                .toItemStack();

        for (int slot = 0; slot < 10; slot++) {
            inventory.setItem(slot, placeholderItem);
        }
        for(int slot = 17; slot < 27; slot++) {
            inventory.setItem(slot, placeholderItem);
        }

        inventory.setItem(12, netherItem);
        inventory.setItem(15, endItem);

        p.openInventory(inventory);
    }

    @EventHandler
    public void invClick(InventoryClickEvent e){
        if(e.getView().getTitle().contains("§bVoyageur §7- §b" + e.getWhoClicked().getName())){
            int slot = e.getSlot();
            if(slot == 12){
                Player p = e.getWhoClicked().getServer().getPlayer(e.getWhoClicked().getName());
                //check
                if(Placeholders.netherTravelsRemaining.get(e.getWhoClicked()) > 0){
                    //teleport
                    e.getWhoClicked().teleport(Bukkit.getWorld("world_nether").getSpawnLocation());
                    Messages.sendMessage(p, "&8[&bVoyageur&8] &bVous avez été téléporté au &4&lNether &b!");
                    Placeholders.netherTravelsRemaining.put((Player) e.getWhoClicked(), Placeholders.netherTravelsRemaining.get(e.getWhoClicked()) - 1);
                } else {
                    Messages.sendMessage(p, "&8[&bVoyageur&8] &cVous n'avez plus de téléportations pour le &4&lNether &crestantes ! &cRevenez demain !");
                }
            } else if(slot == 15){
                Player p = e.getWhoClicked().getServer().getPlayer(e.getWhoClicked().getName());
                //check
                if(Placeholders.endTravelsRemaining.get(e.getWhoClicked()) > 0){
                    //teleport
                    e.getWhoClicked().teleport(Bukkit.getWorld("world_the_end").getSpawnLocation());
                    Messages.sendMessage(p, "&8[&bVoyageur&8] &bVous avez été téléporté à l'&b&lEnd &b!");
                    Placeholders.endTravelsRemaining.put((Player) e.getWhoClicked(), Placeholders.endTravelsRemaining.get(e.getWhoClicked()) - 1);
                } else {
                    Messages.sendMessage(p, "&8[&bVoyageur&8] &cVous n'avez plus de téléportations pour l'&b&lEnd &crestantes ! &cRevenez demain !");
                }
            }
            e.setCancelled(true);
        }
    }
}
