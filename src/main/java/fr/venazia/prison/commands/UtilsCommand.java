package fr.venazia.prison.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.venazia.prison.utils.Messages;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            switch (label.toLowerCase()) {
                case "iname":
                    handleInameCommand(p, args);
                    break;
                case "uptime":
                    Messages.sendMessage(p, "&8[&aUptime&8] &7Uptime du &aserveur");
                    break;
                case "help":
                    sendHelpMessage(p);
                    break;
                case "permile":
                    SuperiorSkyblockAPI.getMenus().getMenu("permissions");
                    break;
                case "is":
                    SuperiorSkyblockAPI.getMenus().getMenu("control-panel");
                    break;
                case "lore":
                    handleLoreCommand(p, args);
                    break;
                default:
                    return false;
            }
            return true;
        }
        return false;
    }

    private void handleInameCommand(Player p, String[] args) {
        ItemStack is = p.getInventory().getItemInMainHand();
        if (is != null) {
            if (args.length >= 1) {
                String name = String.join(" ", args).trim();
                name = ChatColor.translateAlternateColorCodes('&', name);

                ItemMeta meta = is.getItemMeta();
                if (meta != null) {
                    meta.setDisplayName(name);
                    is.setItemMeta(meta);
                    Messages.sendMessage(p, "&8[&6Items&8] &eVous avez modifié le nom de l'item en &6" + name);
                } else {
                    Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Impossible de modifier l'item.");
                }
            } else {
                Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Vous devez préciser le nom de l'item.");
            }
        } else {
            Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Vous devez avoir un item dans votre main pour faire ceci.");
        }
    }

    private void handleLoreCommand(Player p, String[] args) {
        if (args.length >= 2) {
            String mode = args[0];
            ItemStack is = p.getInventory().getItemInMainHand();
            if (is != null) {
                ItemMeta meta = is.getItemMeta();
                if (meta != null) {
                    if (mode.equalsIgnoreCase("add")) {
                        String loreItem = String.join(" ", Arrays.copyOfRange(args, 1, args.length)).trim();
                        loreItem = ChatColor.translateAlternateColorCodes('&', loreItem);

                        List<String> loreList = meta.getLore();
                        if (loreList == null) {
                            loreList = new ArrayList<>();
                        }
                        loreList.add(loreItem);
                        meta.setLore(loreList);
                        is.setItemMeta(meta);
                        Messages.sendMessage(p, "&8[&6Items&8] &eLore ajoutée à l'item: &b" + loreItem);
                    } else if (mode.equalsIgnoreCase("remove")) {
                        try {
                            int l = Integer.parseInt(args[1]) - 1;
                            List<String> loreList = meta.getLore();
                            if (loreList != null && l >= 0 && l < loreList.size()) {
                                loreList.remove(l);
                                meta.setLore(loreList);
                                is.setItemMeta(meta);
                                Messages.sendMessage(p, "&8[&6Items&8] &eLore retirée.");
                            } else {
                                Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Numéro de ligne invalide.");
                            }
                        } catch (NumberFormatException e) {
                            Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Vous devez préciser un numéro de ligne valide.");
                        }
                    } else {
                        Messages.sendMessage(p, "&8[&6Items&8] &cUsage: &e/lore <action (add ou remove)> <texte (ou ligne si remove)>");
                    }
                } else {
                    Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Impossible de modifier l'item.");
                }
            } else {
                Messages.sendMessage(p, "&8[&6Items&8] &cErreur: Vous devez avoir un item dans votre main pour faire ceci.");
            }
        } else {
            Messages.sendMessage(p, "&8[&6Items&8] &cUsage: &e/lore <action (add ou remove)> <texte (ou ligne si remove)>");
        }
    }

    private void sendHelpMessage(Player p) {
        Messages.sendMessage(p, "&8&m-----&b&l»&8&m----- &eAide du serveur &f- &6Commandes utiles &8&m-----&b&l»&8&m-----");
        Messages.sendMessage(p, "&e/rankup &f- &bPermet de rankup");
        Messages.sendMessage(p, "&e/prestige &f- &bPermet de prestige");
        Messages.sendMessage(p, "&e/money &f- &bPermet de consulter de la monnaie");
        Messages.sendMessage(p, "&e/bb &f- &bVoir le nombres de blocks que tu as cassé !");
        Messages.sendMessage(p, "&8&m-----&b&l»&8&m----- &eAide du serveur &f- &6Commandes utiles &8&m-----&b&l»&8&m-----");
    }
}