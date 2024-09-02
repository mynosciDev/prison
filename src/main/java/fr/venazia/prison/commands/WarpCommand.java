package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.warps.Warp;
import fr.venazia.prison.utils.WarpManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public class WarpCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public WarpCommand(WarpManager warpManager) {
        this.warpManager = warpManager;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;

            if (strings.length != 1) {
                p.sendMessage("§cUsage: /warp <nom_du_warp>");
                return false;
            }

            String warpName = strings[0];
            Warp warp = warpManager.getWarp(warpName);

            if (warp == null || warp.getLocation() == null) {
                p.sendTitle("§cErreur", "§4Ce warp n'existe pas !", 10, 70, 20);
                return true;
            }

            if (p.hasPermission("admin") || p.hasPermission("dieu") || p.hasPermission("savant") || p.hasPermission("genie")) {
                p.teleport(warp.getLocation());
                p.sendMessage("§aTéléporté à " + warpName + " !");
            } else {
                p.sendTitle("§c»", "§3Téléportation dans 5 secondes...", 10, 70, 20);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        p.teleport(warp.getLocation());
                        p.sendMessage("§aTéléporté à " + warpName + " !");
                    }
                }.runTaskLater(Main.getPlugin(Main.class), 100L); // 100 ticks = 5 secondes
            }
        }
        return true;
    }
}
