package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.NumberFormat;

public class BlocksCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            if(strings.length == 1) {
                commandSender.sendMessage("§cUsage: /bb add <player> <amount>");
                return true;
            } else if(strings.length == 3) {
                if(strings[0].equalsIgnoreCase("add")) {
                    Player p = (Player) commandSender;
                    String uuid = p.getUniqueId().toString();
                    File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
                    File playerFile = new File(playersDir, uuid + ".json");
                    if (playerFile.exists()) {
                        try {
                            String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                            JSONObject jsonObject = new JSONObject(content);
                            int blocs = jsonObject.getInt("blocs");
                            int amount = Integer.parseInt(strings[2]);
                            blocs += amount;
                            jsonObject.put("blocs", blocs);
                            try {
                                Files.write(Paths.get(playerFile.getPath()), jsonObject.toString(4).getBytes());
                                p.sendMessage("§aVous avez ajouté §b" + NumberFormat.getInstance().format(amount) + " §bblocs à votre total.");
                            } catch (IOException e) {
                                p.sendMessage("§cUne erreur est survenue lors de l'écriture des données du joueur.");
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            p.sendMessage("§cUne erreur est survenue lors de la lecture des données du joueur.");
                            e.printStackTrace();
                        }
                    } else {
                        p.sendMessage("§cAucune donnée trouvée pour ce joueur.");
                    }
                }
                return true;
            }
            Player p = (Player) commandSender;
            String uuid = p.getUniqueId().toString();
            File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
            File playerFile = new File(playersDir, uuid + ".json");
            if (playerFile.exists()) {
                try {
                    String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                    JSONObject jsonObject = new JSONObject(content);
                    int blocs = jsonObject.getInt("blocs");
                    p.sendMessage("§bVous avez cassé §a" + blocs + " §bblocs");
                } catch (IOException e) {
                    p.sendMessage("§cUne erreur est survenue lors de la lecture des données du joueur.");
                    e.printStackTrace();
                }
            } else {
                p.sendMessage("§cAucune donnée trouvée pour ce joueur.");
            }
        }
        return true;
    }
}
