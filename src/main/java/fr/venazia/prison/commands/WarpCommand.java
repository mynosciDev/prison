package fr.venazia.prison.commands;

import fr.venazia.prison.Main;
import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.warps.Warp;
import fr.venazia.prison.utils.WarpManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class WarpCommand implements CommandExecutor {

    private final WarpManager warpManager;

    public WarpCommand() {
        this.warpManager = new WarpManager();
    }
    public static boolean arlgen = false;
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if(!arlgen) {
                World divers = Bukkit.getWorld("divers");
                World mines = Bukkit.getWorld("mines");
                World pvp = Bukkit.getWorld("pvp");
                warpManager.addWarp("a", new Location(mines, -1070, 23, -1275));
                warpManager.addWarp("b", new Location(mines, -1075, 23, -1195));
                warpManager.addWarp("tuto", new Location(divers,  458, 114, 1198));
                warpManager.addWarp("mines", new Location(mines, -1073, 23, -1275));
                warpManager.addWarp("pvp", new Location(pvp, 84, -9, 3));
                warpManager.addWarp("c", new Location(mines, -1106, 23, -1356));
                warpManager.addWarp("d", new Location(mines, -1140, 23, -1376));
                warpManager.addWarp("e", new Location(mines, -1151, 23, -1259));
                warpManager.addWarp("f", new Location(mines, -1211, 24, -1256));
                warpManager.addWarp("g", new Location(mines, -1211, 23, -1339));
                warpManager.addWarp("h", new Location(mines, -1255, 23, -1194));
                warpManager.addWarp("j", new Location(mines, -1081, 24, -869));
                warpManager.addWarp("i", new Location(mines, -1363, 23, -931));
                warpManager.addWarp("k", new Location(mines, -1196, 25, -874));
                warpManager.addWarp("l", new Location(mines, -1211, 23, -1017));
                arlgen = true;
            }
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
            if(warpName.length() == 1) {
                if(p.hasPermission("admin")){
                    p.teleport(warp.getLocation());
                    p.sendMessage("§4§lAdmin -> TP à " + warpName + " !");
                    return true;
                }
                if (!DataUtils.readValue("mine", p.getUniqueId().toString()).equals(warpName.toUpperCase())) {
                    p.sendTitle("§c", "§4Vous n'êtes pas à la bonne mine.", 10, 70, 20);
                    return true;
                } else {
                    p.teleport(warp.getLocation());
                    return true;
                }
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
