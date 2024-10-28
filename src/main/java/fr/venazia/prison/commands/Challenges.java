package fr.venazia.prison.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.menu.ISuperiorMenu;
import com.bgsoftware.superiorskyblock.api.menu.Menu;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

public class Challenges {

    @Command({"challengesid", "cid"})
    public void challenges(BukkitCommandActor a, @Named("ID_CHALLENGE")  String[] id) {
        Player p = a.asPlayer();
        if (!p.hasPermission("admin")) {
            p.sendMessage("§cCette commande n'est pas encore disponible.");
        } else {
            SuperiorPlayer superiorPlayer = SuperiorSkyblockAPI.getPlayer(p.getName());
            Menu<?, ?> menu = SuperiorSkyblockAPI.getMenus().getMenu("missions"); // Adjust the menu name as needed
            if (menu != null) {
                //a
            } else {
                p.sendMessage("§cLe menu des missions n'a pas pu être trouvé.");
            }
        }
    }
}