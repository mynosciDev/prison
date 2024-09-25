package fr.venazia.prison.commands;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Suggest;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import java.util.Arrays;

public class BugCommand {
    @Command({"bug", "report"})

    public void bug(BukkitCommandActor actor, @Suggest({"Prison", "Core", "Mines"}) String jeu, String[] explication) {
        Player p = actor.asPlayer();
        if(explication[0].length() < 10 || explication[1].length() < 10 || explication[2].length() < 10) {
            p.sendMessage("§c§l[BUG] §7Votre explication est trop courte.");
            return;
        }
        if(jeu.equalsIgnoreCase("Prison") || jeu.equalsIgnoreCase("Mines") || jeu.equalsIgnoreCase("Core")) {
            p.sendMessage("§c§l[BUG] §7Votre bug a été signalé. Ce signalement permet d'améliorer le développement, si il s'avère vrai, vous serez récompensé !");
        } else {
            p.sendMessage("§c§l[BUG] §7Ce jeu n'existe pas.");
            return;
        }
        for(Player pl : p.hasPermission("prison.admin") ? p.getWorld().getPlayers() : p.getServer().getOnlinePlayers()) {
            pl.sendMessage("§c§l[BUG] §7" + p.getName() + " a signalé un bug: §6" + Arrays.toString(explication) + "§7.");
        }
    }
}
