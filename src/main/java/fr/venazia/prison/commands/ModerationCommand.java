package fr.venazia.prison.commands;

import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;

import java.util.HashMap;

public class ModerationCommand {

    public static HashMap<Player, Boolean> frozenPlayers = new HashMap<>();

    @Command(
            {"immo", "freeze"}
    )
    public void freezeCmd(BukkitCommandActor actor, String target) {
        if(actor.isPlayer()){
            Player p = actor.asPlayer();
            if(p.hasPermission("prison.moderation")){
                Player targetPlayer = p.getServer().getPlayer(target);
                if(targetPlayer != null){
                    if(frozenPlayers.get(targetPlayer) != null && frozenPlayers.get(targetPlayer)){
                        frozenPlayers.put(targetPlayer, false);
                        targetPlayer.setInvulnerable(false);
                        p.sendMessage("§aLe joueur " + targetPlayer.getName() + " a été débloqué.");
                        return;
                    }
                    frozenPlayers.put(targetPlayer, true);
                    p.sendMessage("§6§oLe joueur " + targetPlayer.getName() + " a été immobilisé.");
                    p.sendMessage("Entrée dans l'hashmap: " + frozenPlayers.get(targetPlayer));
                } else {
                    p.sendMessage("§cLe joueur " + target + " n'est pas connecté.");
                }
            } else {
                p.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            }
        }
    }

    @Command(
            {"clearchat", "cc"}
    )
    public void clearChat(BukkitCommandActor actor) {
        if(actor.isPlayer()){
            Player p = actor.asPlayer();
            if(p.hasPermission("prison.moderation")){
                for(int i = 0; i < 100; i++){
                    p.sendMessage(" ");
                }
                p.sendMessage("§aLe chat a été nettoyé.");
            } else {
                p.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            }
        }
    }

    @Command(
            {"vanish", "v"}
    )
    public void vanish(BukkitCommandActor actor) {
        if(actor.isPlayer()){
            Player p = actor.asPlayer();
            if(p.hasPermission("prison.moderation")){
                if(p.isInvisible()){
                    p.setInvisible(false);
                    p.sendMessage("§aVous êtes désormais visible.");
                } else {
                    p.setInvisible(true);
                    p.sendMessage("§aVous êtes désormais invisible.");
                }
            } else {
                p.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            }
        }
    }

}