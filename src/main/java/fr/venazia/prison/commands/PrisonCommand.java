package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;

public class PrisonCommand {

    @Command("prison")
    @Description("Main command for Prison plugin")
    public void prison(BukkitCommandActor actor) {
        Player p = actor.requirePlayer();
        Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
        Messages.sendMessage(p, "§6§lPrison §eby §bmynosci");
        if (p.hasPermission("admin")) {
            Messages.sendMessage(p, "§fFait la commande §c/prison admin §fpour voir les commandes d'administration.");
        } else {
            Messages.sendMessage(p, "§cVous n'avez pas la permission d'utiliser cette commande.");
        }
        Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
    }

    @Command("resetd")
    @revxrsal.commands.bukkit.annotation.CommandPermission("admin")
    @Description("Reset a player's data")
    public void resetPlayer(BukkitCommandActor actor, @Named("player") String playerName) {
        Player p = actor.requirePlayer();
        Player target = Bukkit.getPlayer(playerName);
        if (target == null) {
            Messages.sendMessage(p, "&8[&cErreur&8] &eLe joueur doit être en ligne pour réaliser cette action !");
            return;
        }
        UUID uuid = target.getUniqueId();
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uuid.toString());
        jsonObject.put("mine", "A");
        jsonObject.put("blocs", 0);
        jsonObject.put("prestige", 0);
        jsonObject.put("money", 0);
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
        if (!playersDir.exists()) {
            playersDir.mkdirs();
        }
        File playerFile = new File(playersDir, uuid.toString() + ".json");
        try (FileWriter file = new FileWriter(playerFile)) {
            file.write(jsonObject.toString(4));
            file.flush();
            Main.getINSTANCE().getServer().getConsoleSender().sendMessage("§aCréation d'un fichier de configuration pour le joueur " + target.getName());
            Messages.sendMessage(p, "&8[&aSuccès&8] &2Fichier de configuration créé pour le joueur &6 " + target.getName() + " !");
        } catch (IOException error) {
            error.printStackTrace();
        }
    }
}