package fr.venazia.prison.utils;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import com.bgsoftware.superiorskyblock.api.island.Island;
import fr.venazia.prison.Main;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class Placeholders extends PlaceholderExpansion {
    public static HashMap<Player, Integer> endTravelsRemaining = new HashMap<>();
    public static HashMap<Player, Integer> netherTravelsRemaining = new HashMap<>();

    private final Main plugin;

    public Placeholders(Main plugin) {
        this.plugin = plugin;
    }

    @Override
    public String getAuthor() {
        return "mynosci";
    }

    @Override
    public String getIdentifier() {
        return "prison";
    }

    @Override
    public String getVersion() {
        return "0.1.0";
    }

    @Override
    public boolean persist() {
        return true; // This is required or else PlaceholderAPI will unregister the Expansion on reload
    }

    @Override
    public String onRequest(OfflinePlayer player, String params) {
        if (params.equalsIgnoreCase("state_nether")) {
            Island i = SuperiorSkyblockAPI.getPlayer(player.getPlayer()).getIsland();
            if (i.getIslandLevel().intValue() >= 65) {
                return "§a✔";
            } else {
                return "§c✘";
            }
        }
        if (params.equalsIgnoreCase("state_end")) {
            Island i = SuperiorSkyblockAPI.getPlayer(player.getPlayer()).getIsland();
            if (i.getIslandLevel().intValue() >= 100) {
                return "§a✔";
            } else {
                return "§c✘";
            }
        }
        if (params.equalsIgnoreCase("state_nether_travelsReaming")) {
            Player p = player.getPlayer();
            return "§4" + netherTravelsRemaining.get(p);
        }
        if (params.equalsIgnoreCase("state_end_travelsReaming")) {
            Player p = player.getPlayer();
            return "§b" + endTravelsRemaining.get(p);
        }
        return "§cErreur: Placeholder '" + params + " 'inconnu";
    }
}
