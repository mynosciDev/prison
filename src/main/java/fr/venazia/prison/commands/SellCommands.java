package fr.venazia.prison.commands;

import fr.venazia.prison.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SellCommands {

    private static final Map<Material, Double> ITEM_PRICES = new HashMap<>();

    static {
        ITEM_PRICES.put(Material.GOLD_ORE, 150.0);
        ITEM_PRICES.put(Material.EMERALD_ORE, 6500.0);
        ITEM_PRICES.put(Material.IRON_ORE, 70.0);
        ITEM_PRICES.put(Material.STONE, 15.0);
        ITEM_PRICES.put(Material.COBBLESTONE, 10.0);
        ITEM_PRICES.put(Material.DIAMOND_ORE, 850.0);
        ITEM_PRICES.put(Material.COAL_ORE, 30.0);
        ITEM_PRICES.put(Material.REDSTONE_ORE, 50.0);
        ITEM_PRICES.put(Material.LAPIS_ORE, 40.0);
        ITEM_PRICES.put(Material.NETHER_QUARTZ_ORE, 25.0);
        ITEM_PRICES.put(Material.GOLD_INGOT, 200.0);
        ITEM_PRICES.put(Material.EMERALD, 7000.0);
        ITEM_PRICES.put(Material.IRON_INGOT, 80.0);
        ITEM_PRICES.put(Material.DIAMOND, 900.0);
        ITEM_PRICES.put(Material.COAL, 35.0);
        ITEM_PRICES.put(Material.REDSTONE, 55.0);
        ITEM_PRICES.put(Material.LAPIS_LAZULI, 45.0);
        ITEM_PRICES.put(Material.QUARTZ, 30.0);
        ITEM_PRICES.put(Material.STONE_BRICKS, 20.0);
        ITEM_PRICES.put(Material.CRACKED_STONE_BRICKS, 10.0);
    }


    @Command({"sell", "vendre"})


    public void sell(BukkitCommandActor a) {
        Player p = a.asPlayer();
        double totalValue = 0.0;
        for (ItemStack item : p.getInventory().getContents()) {
            if (item != null && ITEM_PRICES.containsKey(item.getType())) {
                double itemPrice = ITEM_PRICES.get(item.getType());
                totalValue += itemPrice * item.getAmount();
                p.getInventory().remove(item);
            }
        }

        double money = 0;
        money = Double.parseDouble(String.valueOf(DataUtils.readValue("money", p.getUniqueId().toString())));
        DataUtils.writeValue("money", p.getUniqueId().toString(), String.valueOf(money + totalValue));
        if(totalValue == 0) {
            p.sendMessage("§4[➜] §cVous n'avez rien à vendre");
        }
        p.sendMessage("§e[➜] §a§l§oVous avez correctement vendu vos objets pour un total de §6" + totalValue + "€§a§l§o.");
    }

    }

