package fr.venazia.prison.commands;

import fr.venazia.prison.utils.Messages;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class ValueCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if (command.getName().equalsIgnoreCase("valeur")) {
                if (p.getInventory().getItemInMainHand().getType() == Material.AIR) {
                    Messages.sendMessage(p, "&cVous devez tenir un objet en main pour faire ceci.");
                } else {
                    ItemStack itemInHand = p.getInventory().getItemInMainHand();
                    Material itemType = itemInHand.getType();
                    int amount = itemInHand.getAmount();
                    int value;

                    switch (itemType) {
                        case SPONGE:
                            value = 500;
                            break;
                        case DRAGON_EGG:
                            value = 1500;
                            break;
                        case BEACON:
                            value = 1000;
                            break;
                        case PAPER:
                            String displayName = itemInHand.getItemMeta().getDisplayName();
                            if (displayName.equalsIgnoreCase("mynosci")) {
                                value = 10000000;
                                p.sendTitle("§b§k- §a§lOUAIIIIIIIIIS §b§k-", "§6§lCOUPAIIIIN", 20, 100, 20);
                            } else if (displayName.equalsIgnoreCase("Mime4X")) {
                                value = 1000000;
                                p.sendTitle("§3§k- §b§lOUAIIIIIIIIIS §3§k-", "§b§lCOUPAI§9§lIIIN", 20, 100, 20);
                            } else if (displayName.equalsIgnoreCase("Cryptos_YT")) {
                                value = -100000000;
                                p.sendTitle("§4§k- §c§lPAS GENTIL §4§k-", "§c§lUnfriendly", 20, 100, 20);
                            } else {
                                value = 0;
                            }
                            break;
                        default:
                            Messages.sendMessage(p, "&cL'objet que vous tenez en main n'a pas de valeur, ou elle est inférieure à 5.");
                            return true;
                    }

                    if (value != 0) {
                        Messages.sendMessage(p, "&aLa valeur de l'objet que vous tenez en main est de &e" + (value * amount) + "&a.");
                    }
                }

                return true;
            }
        }
        return true;
    }
}

