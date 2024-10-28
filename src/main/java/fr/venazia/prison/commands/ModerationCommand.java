package fr.venazia.prison.commands;

import fr.venazia.prison.utils.Messages;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Named;
import revxrsal.commands.annotation.Optional;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

public class ModerationCommand {

    public static HashMap<Player, Boolean> frozenPlayers = new HashMap<>();

    public static HashMap<String, Boolean> disabledCommands = new HashMap<>();


    @Command("disablecommand")
    @Description("MOD - Désactive une commande")
    @CommandPermission("prison.moderation")
    public void disableCommand(BukkitCommandActor actor, @Named("commande") String command) {
        if (actor.isPlayer()) {
            Player p = actor.asPlayer();
            if (disabledCommands.get(command) == null) {
                disabledCommands.put(command, true);
                p.sendMessage("§aLa commande " + command + " a été désactivée.");
            } else if (disabledCommands.get(command)) {
                disabledCommands.remove(command);
                p.sendMessage("§aLa commande " + command + " a été réactivée.");
            }
        }
    }

    private static final String PATCH_FILE_PATH = "plugins/prison/utils/patchs.txt";


    @Command("patch")
    @Description("Vois les patchs du serveur")
    public void patch(BukkitCommandActor a) {
        Player p = a.asPlayer();
        p.sendMessage("§e§oPatchs du serveur:");
        try {
            Path path = Paths.get(PATCH_FILE_PATH);
            List<String> lines = Files.readAllLines(path); // Lire tout le contenu du fichier
            if (lines.isEmpty()) {
                p.sendMessage(ChatColor.RED + "Aucune information de patch trouvée.");
                return;
            }
            for (String line : lines) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', line)); // Remplacer & par § pour les couleurs
            }

        } catch (IOException e) {
            p.sendMessage(ChatColor.RED + "Erreur lors de la lecture du fichier des patchs.");
            e.printStackTrace();
        }
    }

    private static final String PATCHDEV_FILE_PATH = "plugins/prison/utils/patchdev.txt";

    @Command("maintenance")
    @Description("MOD - Active ou désactive la maintenance")
    @CommandPermission("prison.admin")
    public void maintenance(BukkitCommandActor a) {
        Player p = a.asPlayer();
        if (disabledCommands.get("maintenance") == null) {
            disabledCommands.put("maintenance", true);
            for (Player player : p.getServer().getOnlinePlayers()) {
                player.kickPlayer("§c§lLe serveur est actuellement en maintenance.");
            }
            p.sendMessage("§c§lLe serveur est désormais en maintenance.");
        } else {
            disabledCommands.remove("maintenance");
            p.sendMessage("§a§lLe serveur n'est plus en maintenance.");
        }
    }


    @Command("patchdev")
    @Description("Vois les patchs dev du serveur")
    public void patchDev(BukkitCommandActor a){
        Player p = a.asPlayer();
        p.sendMessage("§e§oPatchs §3§lDEV §e§odu serveur:");
        try {
            Path path = Paths.get(PATCHDEV_FILE_PATH);
            List<String> lines = Files.readAllLines(path); // Lire tout le contenu du fichier
            if (lines.isEmpty()) {
                p.sendMessage(ChatColor.RED + "Aucune information de patch trouvée.");
                return;
            }
            for (String line : lines) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', line)); // Remplacer & par § pour les couleurs
            }

        } catch (IOException e) {
            p.sendMessage(ChatColor.RED + "Erreur lors de la lecture du fichier des patchs.");
            e.printStackTrace();
        }
    }

    /**
     * Système contrib
     * @param a
     * @param user
     */

    @Command("contrib")
    @Description("Affiche les contributions d'un joueur ou de vous")
    public void contributions(BukkitCommandActor a, @Named("user") @Optional String user){
        Player p = a.asPlayer();
        String n = user == null ? p.getName() : user;
        if(n.equals("mynosci")){
            p.sendMessage("§4§l§oIl à déjà créé le serveur, c'est déjà pas mal.");
        } else if(n.equals("LaChipsss")){
            p.sendMessage("§f§lContributions de §6LaChipsss§f§l: §b§oConstruction du jump au spawn du prison");
        } else if(n.equals("Dehks01")){
            p.sendMessage("§f§lContributions de §6Dehks01§f§l: §b§o");
        }else {
            p.sendMessage("§c§lAucune contribution trouvée pour " + n);
        }
    }

    @Command("contribgift")
    @Description("ADMIN - Récompense un joueur pour sa contribution")
    @CommandPermission("prison.admin")
    public void contributionsGift(BukkitCommandActor a, @Named("user") String user){
        Player p = a.asPlayer();
        Player p2 = Bukkit.getPlayer(user);
        Messages.sendMessage(p2, "&6&l[&3&l&oContributions&6&l] &a&oMerci pour ta contribution " + p2.getName() + " ! Tu as été récompensé de 50 000$ pour avoir contribué au serveur. Plus il y a de personnes qui contribuent, plus le serveur continue de se développer");
        p2.sendTitle("§a", "§3§l§oVous avez été récompensé pour avoir contribué !");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "money " + p2.getName() + " add 50000");
        p.sendMessage("§aLe joueur " + p2.getName() + " a été récompensé pour sa contribution.");
    }

    @Command("buggift")
    @Description("ADMIN - Récompense un joueur pour avoir signalé un bug")
    @CommandPermission("prison.admin")
    public void bugGift(BukkitCommandActor a, @Named("user") String user){
        Player p = a.asPlayer();
        Player p2 = Bukkit.getPlayer(user);
        Messages.sendMessage(p2, "&6&o&l[&a&o&lSignalements&6&o&l] &a&oMerci pour avoir signalé un bug " + p2.getName() + " ! Tu as été récompensé de 150 000$ pour avoir contribué au serveur. Plus il y a de personnes qui contribuent, plus le serveur continue de se développer");
        p2.sendTitle("§a", "§6§l§oVous avez été récompensé pour avoir signalé un bug !");
        p.sendMessage("§aLe joueur " + p2.getName() + " a été récompensé pour avoir signalé un bug.");
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "money " + p2.getName() + " add 150000");
    }

    // Fin



    @Command("disallowplayer")
    @CommandPermission("prison.moderation")
    public void disallowPlayer(BukkitCommandActor actor, @Named("user") String player) {
        if (actor.isPlayer()) {
            Player p = actor.asPlayer();
            if(disabledCommands.get(player) == null){
                disabledCommands.put(player, true);
                p.sendMessage("§aLe joueur " + player + " est désormais désactivé.");
            } else if(disabledCommands.get(player)){
                disabledCommands.remove(player);
                p.sendMessage("§aLe joueur " + player + " a été réactivé.");
            }
        }
    }


    @Command("lockchat")
    @CommandPermission("prison.moderation")
    public void lockChat(BukkitCommandActor actor) {
        if (actor.isPlayer()) {
            Player p = actor.asPlayer();
            if (disabledCommands.get("chat") == null) {
                disabledCommands.put("chat", true);
                for (Player player : p.getServer().getOnlinePlayers()) {
                    player.sendMessage("§8[§eChat§8] §c§lLe chat a été désactivé par un membre du staff.");
                }
            } else {
                disabledCommands.remove("chat");
                for (Player player : p.getServer().getOnlinePlayers()) {
                    player.sendMessage("§8[§eChat§8] §aLe chat a été réactivé.");
                }
            }
        }
    }


    @Command(
            "fly"
    )
    public void flyCmd(BukkitCommandActor actor) {
        if(actor.isPlayer()){
            Player p = actor.asPlayer();
            if(p.hasPermission("prison.moderation")){
                if(p.getAllowFlight()){
                    p.setAllowFlight(false);
                    p.sendMessage("§aLe vol a été désactivé.");
                } else {
                    p.setAllowFlight(true);
                    p.sendMessage("§aLe vol a été activé.");
                }
            } else {
                p.sendMessage("§cVous n'avez pas la permission d'effectuer cette commande.");
            }
        }
    }


    @Command(
            {"immo", "freeze"}
    )
    public void freezeCmd(BukkitCommandActor actor, @Named("user") String target) {
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