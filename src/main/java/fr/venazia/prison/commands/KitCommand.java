package fr.venazia.prison.commands;

import fr.venazia.prison.utils.ItemBuilder;
import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

public class KitCommand implements Listener {
    @Command("kit")
    public void kit(BukkitCommandActor a, @Optional  @Named("args") String[] args) {
        Player p = a.asPlayer();
        boolean isConsole = p == null;
        boolean hasArgs = args != null;
        if (!hasArgs) {
            Inventory inv = Bukkit.createInventory(null, 9 * 6, "§aListe des kits");
            ItemStack placeholderItem = new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).addEnchant(Enchantment.SHARPNESS, 1).toItemStack();
            for (int i = 0; i < 54; i++) {
                if (i < 9 || i >= 45 || i % 9 == 0 || i % 9 == 8) {
                    inv.setItem(i, placeholderItem);
                }
            }
            if (p.hasPermission("prison.kit.venazien")) {
                ItemStack kits1 = new ItemBuilder(Material.PAPER)
                        .setName("§dVenazien")
                        .addLoreLine("§7╔")
                        .addLoreLine("§7│ §fNom: §dVenazien")
                        .addLoreLine("§7│ §fDisponible à partir du grade: §dVenazien")
                        .addLoreLine("§7╚")
                        .addLoreLine("§a")
                        .addLoreLine("§e⯍ §7Faîtes un §aclic gauche§7 pour prévisualiser ce kit")
                        .addLoreLine("§e⯍ §7Faîtes un §6clic droit §7pour récupérer ce kit")
                        .toItemStack();
                inv.setItem(11, kits1);
            }
            if (p.hasPermission("prison.kit.legende")) {
                ItemStack kits2 = new ItemBuilder(Material.PAPER)
                        .setName("§6Légende")
                        .addLoreLine("§7╔")
                        .addLoreLine("§7│ §fNom: §6Légende")
                        .addLoreLine("§7│ §fDisponible à partir du grade: §6Légende")
                        .addLoreLine("§7│ §fCooldown: §c1 jours")
                        .addLoreLine("§7│ §fPrix: §c1 000€")
                        .addLoreLine("§7╚")
                        .addLoreLine("§a")
                        .addLoreLine("§e⯍ §7Faîtes un §aclic gauche§7 pour prévisualiser ce kit")
                        .addLoreLine("§e⯍ §7Faîtes un §6clic droit §7pour récupérer ce kit")
                        .toItemStack();
                inv.setItem(15, kits2);
            }
            if (p.hasPermission("prison.kit.dieu")) {
                ItemStack kits3 = new ItemBuilder(Material.PAPER)
                        .setName("§cDieu")
                        .addLoreLine("§7╔")
                        .addLoreLine("§7│ §fNom: §cDieu")
                        .addLoreLine("§7│ §fDisponible à partir du grade: §cDieu")
                        .addLoreLine("§7│ §fCooldown: §c7 jours")
                        .addLoreLine("§7│ §fPrix: §c5 000€")
                        .addLoreLine("§7╚")
                        .addLoreLine("§a")
                        .addLoreLine("§e⯍ §7Faîtes un §aclic gauche§7 pour prévisualiser ce kit")
                        .addLoreLine("§e⯍ §7Faîtes un §6clic droit §7pour récupérer ce kit")
                        .toItemStack();
                inv.setItem(22, kits3);
            }
            p.openInventory(inv);
        }
    }

    @EventHandler
    public void onInvRightClick(InventoryClickEvent e) {
        if (e.getClickedInventory() == null) return;
        if (e.getView().getTitle().equalsIgnoreCase("§aListe des kits")) {
            e.setCancelled(true);
            if (e.getCurrentItem() == null) return;
            if (e.getCurrentItem().getType() == Material.PAPER) {
                Player p = (Player) e.getWhoClicked();
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§dVenazien")) {
                    p.sendMessage("§aVous avez reçu le kit §dVenazien");
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6Légende")) {
                    p.sendMessage("§aVous avez reçu le kit §6Légende");
                }
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cDieu")) {
                    p.sendMessage("§aVous avez reçu le kit §cDieu");
                }
            }
        }
    }

    @Command("createkit")
    @Description("Permet de créer un kit, avec ou sans cooldown")
    @CommandPermission("prison.kit.create")
    public void createKit(BukkitCommandActor a, @Named("kit") String kit, @Optional @Named("cooldown") int cooldown){
        Player p = a.asPlayer();
        boolean isConsole = p == null;
        boolean hasCooldown = cooldown != 0;
        if (hasCooldown) {
            Inventory inv = p.getInventory();
            if(inv.isEmpty()){
                p.sendMessage("§cVous devez avoir des items dans votre inventaire pour créer un kit");
                return;
            }
            ItemStack[] items = inv.getContents();
            KitManager.saveKit(items)
            return;
            }
            p.sendMessage("§aVous avez créé le kit §6" + kit + " §aavec un cooldown de §6" + cooldown + " jours");
        } else {
            p.sendMessage("§aVous avez créé le kit §6" + kit);
        }

    }
}
