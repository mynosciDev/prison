package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.JSONObject;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;

public class BlocksCommand {

    @Command("bb")
    @Description("Gérer les blocs pour les joueurs")
    public void onCommand(BukkitCommandActor actor, @Named("action") String action, @Named("joueur") String playerName, @Named("quantité") int amount) {
        Player p = actor.requirePlayer();
        if (action.equalsIgnoreCase("add")) {
            Player target = Bukkit.getPlayer(playerName);
            if (target == null) {
                Messages.sendMessage(p, "§cJoueur non trouvé.");
                return;
            }
            String uuid = target.getUniqueId().toString();
            File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
            File playerFile = new File(playersDir, uuid + ".json");
            if (playerFile.exists()) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                    JSONObject jsonObject = new JSONObject(content);
                    int blocs = jsonObject.getInt("blocs");
                    blocs += amount;
                    jsonObject.put("blocs", blocs);
                    Files.write(Paths.get(playerFile.getPath()), jsonObject.toString(4).getBytes());
                    Messages.sendMessage(p, "§aVous avez ajouté §b" + NumberFormat.getInstance().format(amount) + " §ablocs à " + playerName + ".");
                } catch (IOException e) {
                    Messages.sendMessage(p, "§cUne erreur est survenue lors de l'écriture des données du joueur.");
                    e.printStackTrace();
                }
            } else {
                Messages.sendMessage(p, "§cAucune donnée trouvée pour ce joueur.");
            }
        } else {
            Messages.sendMessage(p, "§cUsage: /bb add <joueur> <quantité>");
        }
    }

    @Command("bb")
    @Description("Vérifier les blocs pour le joueur")
    public void onCommand(BukkitCommandActor actor) {
        Player p = actor.requirePlayer();
        String uuid = p.getUniqueId().toString();
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
        File playerFile = new File(playersDir, uuid + ".json");
        if (playerFile.exists()) {
            try {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                JSONObject jsonObject = new JSONObject(content);
                int blocs = jsonObject.getInt("blocs");
                Messages.sendMessage(p, "§bVous avez cassé §a" + blocs + " §bblocs.");
            } catch (IOException e) {
                Messages.sendMessage(p, "§cUne erreur est survenue lors de la lecture des données du joueur.");
                e.printStackTrace();
            }
        } else {
            Messages.sendMessage(p, "§cAucune donnée trouvée pour ce joueur.");
        }
    }
}