package fr.venazia.prison.commands;

import fr.venazia.prison.utils.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateKitCommand implements CommandExecutor {
    public final KitManager kitManager;

    public CreateKitCommand(KitManager kitManager) {
        this.kitManager = kitManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Cette commande doit être exécutée par un joueur.");
            return true;
        }

        Player player = (Player) sender;

        if (args.length < 2) {
            player.sendMessage("Utilisation : /createkit <nom_du_kit> <cooldown>");
            return true;
        }

        String kitName = args[0];
        long cooldown;

        try {
            cooldown = Long.parseLong(args[1]);
        } catch (NumberFormatException e) {
            player.sendMessage("Le cooldown doit être un nombre valide.");
            return true;
        }

        kitManager.createKitFromPlayer(player, kitName, cooldown);
        player.sendMessage("Kit '" + kitName + "' créé avec succès !");
        return true;
    }
}
