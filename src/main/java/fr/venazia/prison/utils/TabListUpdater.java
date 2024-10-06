package fr.venazia.prison.utils;

import fr.venazia.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import me.clip.placeholderapi.PlaceholderAPI;

public class TabListUpdater {


    public static void startTabListUpdater() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    updateTabList(p);
                }
            }
        }.runTaskTimer(Main.getINSTANCE(), 0L, 20L);
    }

    public static void updateTabList(Player p) {
        String footer1 = Main.getINSTANCE().getConfig().getString("footer");
        String header1 = Main.getINSTANCE().getConfig().getString("header");
        footer1 = ChatColor.translateAlternateColorCodes('&', footer1).replace("\\n", "\n");
        header1 = ChatColor.translateAlternateColorCodes('&', header1).replace("\\n", "\n");
        String footer = PlaceholderAPI.setPlaceholders(p, footer1);
        String header = PlaceholderAPI.setPlaceholders(p, header1);
        String header2 = ChatColor.translateAlternateColorCodes('&', header);
        String footer2 = ChatColor.translateAlternateColorCodes('&', footer);
        p.setPlayerListHeaderFooter(header2, footer2);
    }
}