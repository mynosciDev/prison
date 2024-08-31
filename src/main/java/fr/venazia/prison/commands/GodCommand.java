package fr.venazia.prison.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class GodCommand implements CommandExecutor, Listener {


    private Map<Player, String> g = new HashMap<>();
    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if (commandSender instanceof Player) {
            Player p = (Player) commandSender;
            if(p.hasPermission("prison.sm")){
              if(g.containsKey(p)){
                  p.sendMessage("§cGod mode désactivé !");
                  g.remove(p);
              } else {
                  p.sendMessage("§eGod mode activé !");
                  g.put(p, g.get(p));
              }
            }
        }
        return false;
    }

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player p = (Player) e.getEntity();
            if (g.containsKey(p)) {
                e.setCancelled(true);
            } else {
                return;
            }
        }
    }
}
