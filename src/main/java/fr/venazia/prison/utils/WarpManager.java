package fr.venazia.prison.utils;

import fr.venazia.prison.warps.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class WarpManager {
    private final Map<String, Warp> warps = new HashMap<>();

    public void addWarp(String name, Location location) {
        warps.put(name, new Warp(name, location));
        for(Player p : Bukkit.getOnlinePlayers()) {
            p.sendMessage("§8[§aWarp§8] §aLe warp §6" + name + " §aa été enregistré.");
        }
    }

    public Warp getWarp(String name) {
        return warps.get(name);
    }
}
