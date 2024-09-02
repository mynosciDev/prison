package fr.venazia.prison.commands;
import fr.venazia.prison.Main;
import fr.venazia.prison.utils.Kit;
import fr.venazia.prison.utils.KitManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class KitCommand implements CommandExecutor {
    public final KitManager kitManager;

    public KitCommand(JavaPlugin plugin) {
        this.kitManager = ((Main) plugin).getKitManager();
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Seuls les joueurs peuvent exécuter cette commande.");
            return true;
        }
        Player player = (Player) sender;
        if (args.length < 1) {
            player.sendMessage("Usage: /kit <nom>");
            return true;
        }
        String kitName = args[0];
        Kit kit = kitManager.getKit(kitName);
        if (kit == null) {
            player.sendMessage("Le kit " + kitName + " n'existe pas.");
            return true;
        }
        if (!kit.canUse(player.getUniqueId())) {
            player.sendMessage("Vous devez attendre avant de pouvoir réutiliser ce kit.");
            return true;
        }
        for (ItemStack item : kit.getItems()) {
            if (item != null) {
                player.getInventory().addItem(item);
            }
        }
        kit.setLastUsed(player.getUniqueId());

        player.sendMessage("Vous avez reçu le kit " + kitName + ".");
        return true;
    }
}
