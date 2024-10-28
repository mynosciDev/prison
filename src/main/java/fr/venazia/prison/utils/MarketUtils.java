package fr.venazia.prison.utils;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.yaml.snakeyaml.error.Mark;

import javax.xml.crypto.Data;

public class MarketUtils {

    public static void buyItem(Player p, ItemStack item, int prix, int id){
        if (item == null) {
            Messages.sendMessage(p, "&8[&aMarket&8] &cErreur: cette item ne peux pas être acheté. Contactez un staff." + "&cE: &6" + item);
            return;
        } if(prix <= 0){
            Messages.sendMessage(p, "&8[&aMarket&8] &cErreur: le prix doit être supérieur à 0.");
            return;
        } else {

        }
    }


    public static ItemStack getItem(int id){
        if(DataUtils.readMarketValue("item", id) == null){
            Bukkit.broadcastMessage("Item null");
            return new ItemBuilder(Material.BARRIER).setName("§cIl n'y a pas d'items en vente ici !").toItemStack();
        }
        return DataUtils.readMarketValue("item", id);
    }





    public static void sellItem(Player p, ItemStack item, int prix){
        if (item == null || item.getType().isAir()) {
            Messages.sendMessage(p, "&8[&aMarket&8] &cErreur: cette item ne peux pas être vendu. Contactez un staff." + "&cE: &6" + item);
            return;
        } if(prix <= 0){
            Messages.sendMessage(p, "&8[&aMarket&8] &cErreur: le prix doit être supérieur à 0.");
            return;
        } else if(p.getInventory().contains(item)){
            p.getInventory().removeItem(item);
            Object s = DataUtils.readMarketGlobal("slots");
            Integer s2 = Integer.valueOf(s.toString());
            Bukkit.broadcastMessage("s2: " + s2);
            DataUtils.setMarketItem(s2, item, p.getUniqueId(), prix, item.getAmount());
            Messages.sendMessage(p, "&8[&aMarket&8] &aVous avez vendu l'item &6" + item + " &aà &6" + prix + "€");
        } else {
            Messages.sendMessage(p, "&8[&aMarket&8] &cErreur: vous n'avez pas cet item dans votre inventaire.");

        }

    }
}
