package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

public class CreateKitCommand implements CommandExecutor {
    private final KitManager kitManager;

    public CreateKitCommand(JavaPlugin plugin) {
        this.kitManager = ((Main) plugin).getKitManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Usage: /createkit <nom> <cooldown en secondes>");
            return true;
        }

        String kitName = args[0];
        long cooldown = Long.parseLong(args[1]) * 1000;

        ItemStack[] items = player.getInventory().getContents();
        kitManager.createKit(kitName, Arrays.asList(items), cooldown);

        player.sendMessage("Le kit " + kitName + " a été créé avec succès.");
        return true;
    }
}
