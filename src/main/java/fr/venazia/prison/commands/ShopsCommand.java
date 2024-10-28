package fr.venazia.prison.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.venazia.prison.utils.DataUtils;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public class ShopsCommand {
    @Command({"shopile", "shopiles", "shopis"})
    @Description("Permet de modifier le shop de l'île")
    public void shopile(BukkitCommandActor a, @Named("action") @Suggest({"info", "modify", "remove"}) String action) {
        Player p = a.asPlayer();
        boolean v = false;
        Block targetBlock = p.getTargetBlockExact(100);
        if (targetBlock != null && targetBlock.getType() != Material.AIR) {
            if (targetBlock.getType() == Material.OAK_WALL_SIGN || targetBlock.getType() == Material.OAK_SIGN ||
                    targetBlock.getType() == Material.SPRUCE_WALL_SIGN || targetBlock.getType() == Material.SPRUCE_SIGN ||
                    targetBlock.getType() == Material.BIRCH_WALL_SIGN || targetBlock.getType() == Material.BIRCH_SIGN ||
                    targetBlock.getType() == Material.JUNGLE_WALL_SIGN || targetBlock.getType() == Material.JUNGLE_SIGN ||
                    targetBlock.getType() == Material.ACACIA_WALL_SIGN || targetBlock.getType() == Material.ACACIA_SIGN ||
                    targetBlock.getType() == Material.DARK_OAK_WALL_SIGN || targetBlock.getType() == Material.DARK_OAK_SIGN) {
                p.sendTitle("§c", "§5§oCette commande est actuellement en développement", 10, 40, 10);
                v = true;
            }
        }
        if(!v){
            p.sendTitle("§c", "§c§oVous devez regarder un panneau pour utiliser cette commande", 10, 40, 10);
            return;

        }
        switch(action){
            case "info":
                p.sendMessage("§cCette commande est actuellement en développement");
                break;
            case "modify":
                p.sendMessage("§cCette commande est actuellement en développement");
                break;
            case "remove":
                p.sendMessage("§cCette commande est actuellement en développement");
                break;
        }
    }
}