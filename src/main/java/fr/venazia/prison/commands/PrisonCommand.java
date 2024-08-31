package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.Messages;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PrisonCommand implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (args.length == 0) {
                Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
                Messages.sendMessage(p, "§6§lPrison §eby §bmynosci");
                if (p.hasPermission("admin")) {
                    Messages.sendMessage(p, "§fFait la commande §c/prison admin §fpour voir les commandes d'administration.");
                } else {
                    Messages.sendMessage(p, "§cVous n'avez pas la permission d'utiliser cette commande.");
                }
                Messages.sendMessage(p, "§x§F§B§0§0§0§0=§x§F§C§2§E§2§E=§x§F§C§5§D§5§D=§x§F§D§8§B§8§B=§x§F§E§B§9§B§9=§x§F§F§E§8§E§8=§x§F§4§F§3§F§C=§x§D§E§D§C§F§7=§x§C§8§C§4§F§1=§x§B§3§A§D§E§B=§x§9§D§9§5§E§6=§x§8§7§7§E§E§0=");
            } else if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("admin")) {
                    if (args.length >= 2 && args[1].equalsIgnoreCase("reset")) {
                        if (args.length == 3) {
                            String a = args[2];
                            Player s2 = Bukkit.getPlayer(a);
                            if (s2 == null) {
                                Messages.sendMessage(p, "&8[&cErreur&8] &eLe joueur doit être en ligne pour réaliser cette action !");
                                return true;
                            } else {
                                UUID uuid = s2.getUniqueId();
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
                                    Main.getINSTANCE().getServer().getConsoleSender().sendMessage("§aCréation d'un fichier de configuration pour le joueur " + s2.getName());
                                    Messages.sendMessage(p, "&8[&aSuccès&8] &2Fichier de configuration créé pour le joueur &6 " + s2.getName() + " !");
                                } catch (IOException error) {
                                    error.printStackTrace();
                                }
                            }
                        } else {
                            Messages.sendMessage(p, "&8[&cErreur&8] &eUtilisation correcte: /prison admin reset <nom_du_joueur>");
                        }
                    }
                }
            }
        }

        return true;
    }

    List<String> arguments = new ArrayList<>();

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender.hasPermission("admin")) {
            if (args.length == 1) {
                arguments.clear();
                arguments.add("admin");
                return arguments;
            }
            if (args.length == 2 && args[0].equalsIgnoreCase("admin")) {
                arguments.clear();
                arguments.add("reset");
                return arguments;
            }
            if (args.length == 3 && args[0].equalsIgnoreCase("admin") && args[1].equalsIgnoreCase("reset")) {
                List<String> playerNames = new ArrayList<>();
                for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                    playerNames.add(onlinePlayer.getName());
                }
                return playerNames;
            }
        }
        return null;
    }
}
