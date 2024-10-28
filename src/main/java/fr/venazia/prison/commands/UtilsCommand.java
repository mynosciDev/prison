package fr.venazia.prison.commands;

import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;
import fr.venazia.prison.Main;
import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.utils.Messages;
import fr.venazia.prison.utils.WarpManager;
import fr.venazia.prison.warps.Warp;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UtilsCommand {
    /**
     * Commandes utilitaires
     * Retravaillé le 21 octobre 2024
     */

    @Command("iname")
    @CommandPermission("prison.event.organize")
    @Description("Admin - Changer le nom d'un item")
    public void iname(BukkitCommandActor a, @Named("name") String name) {
        Player p = a.asPlayer();
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (itemInHand == null) {
            Messages.sendMessage(p, "&8[&6Items&8] &cVous devez tenir un item dans votre main pour utiliser cette commande.");
        } else {
            ItemMeta meta = itemInHand.getItemMeta();
            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', name));
            itemInHand.setItemMeta(meta);
            Messages.sendMessage(p, "&8[&6Items&8] &aLe nom de l'item a été changé en &e" + name + "&a.");
        }
    }

    @Command("ilore")
    @CommandPermission("prison.event.organize")
    @Description("Admin - Changer la lore d'un item")
    public void ilore(BukkitCommandActor a, @Named("lore") String[] lore) {
        Player p = a.asPlayer();
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (itemInHand == null) {
            Messages.sendMessage(p, "&8[&6Items&8] &cVous devez tenir un item dans votre main pour utiliser cette commande.");
        } else {
            ItemMeta meta = itemInHand.getItemMeta();
            List<String> loreList = new ArrayList<>();
            Arrays.stream(lore).forEach(l -> loreList.add(ChatColor.translateAlternateColorCodes('&', l)));
            meta.setLore(loreList);
            itemInHand.setItemMeta(meta);
            Messages.sendMessage(p, "&8[&6Items&8] &aLa lore de l'item a été changée.");
        }
    }

    @Command("clearlore")
    @CommandPermission("prison.event.organize")
    @Description("Admin - Supprimer la lore d'un item")
    public void clearlore(BukkitCommandActor a) {
        Player p = a.asPlayer();
        ItemStack itemInHand = p.getInventory().getItemInMainHand();
        if (itemInHand == null) {
            Messages.sendMessage(p, "&8[&6Items&8] &cVous devez tenir un item dans votre main pour utiliser cette commande.");
        } else {
            ItemMeta meta = itemInHand.getItemMeta();
            meta.setLore(null);
            itemInHand.setItemMeta(meta);
            Messages.sendMessage(p, "&8[&6Items&8] &aLa lore de l'item a été supprimée.");
        }
    }

    private static final int ENTRIES_PER_PAGE = 7;

    @Command("createmine")
    @CommandPermission("admin")
    @Description("ADMIN - Créer une mine avec une commande pour plus d'optimisation :')")
    public void createMine(BukkitCommandActor a, @Named("mine") String mine, @Named("price") Integer price, @Named("next") String next) {
        Player p = a.asPlayer();
        if (DataUtils.readMineValue(mine, "name") != null) {
            p.sendMessage("§cCette mine existe déjà.");
            return;
        }
        DataUtils.createMine(mine, price, next);
        p.sendMessage("§aLa mine §e" + mine + " §aa été créée avec succès.");
        p.sendMessage("Prix: §e" + price + " §aProchaine mine: §e" + next);
    }

    @Command("deletemine")
    @CommandPermission("admin")
    @Description("ADMIN - Supprimer une mine avec une commande pour plus d'optimisation :')")
    public void deleteMine(BukkitCommandActor a, @Named("mine") String mine) {
        Player p = a.asPlayer();
        if (DataUtils.readMineValue(mine, "name") == null) {
            p.sendMessage("§cCette mine n'existe pas.");
            return;
        }
        DataUtils.deleteMine(mine);
        p.sendMessage("§aLa mine §e" + mine + " §aa été supprimée avec succès.");
    }

    @Command("getmine")
    @CommandPermission("admin")
    @Description("ADMIN - Obtenir les informations d'une mine avec une commande pour plus d'optimisation :')")
    public void getMine(BukkitCommandActor a, @Named("mine") String mine) {
        Player p = a.asPlayer();
        if (DataUtils.readMineValue(mine, "name") == null) {
            p.sendMessage("§cCette mine n'existe pas.");
            return;
        }
        p.sendMessage("§aInformations de la mine §e" + mine + "§a:");
        p.sendMessage("Prix: §e" + DataUtils.readMineValue(mine, "prix") + " §aProchaine mine: §e" + DataUtils.readMineValue(mine, "next"));
    }

    // Prestiges
    @Command("createprestige")
    @CommandPermission("admin")
    @Description("ADMIN - Créer un prestige avec une commande pour plus d'optimisation :')")
    public void createPrestige(BukkitCommandActor a, @Named("prestige") String prestige, @Named("price") Integer price, @Named("next") String next) {
        Player p = a.asPlayer();
        if (DataUtils.readPrestigeValue(prestige, "name") != null) {
            p.sendMessage("§cCe prestige existe déjà.");
            return;
        }
        DataUtils.createPrestige(prestige, price, next);
        p.sendMessage("§aLe prestige §e" + prestige + " §aa été créé avec succès.");
        p.sendMessage("Prix: §e" + price + " §aProchain prestige: §e" + next);
    }

    @Command("spawn")
    public void spawn(BukkitCommandActor a, @Optional @Named("player") String target) {
        Player p = a.asPlayer();
        if (p.hasPermission("prison.moderateur")) {
            if (target == null) {
                p.teleport(Bukkit.getWorld("world").getSpawnLocation());
            } else {
                Player targetPlayer = Bukkit.getPlayer(target);
                if (targetPlayer != null) {
                    targetPlayer.teleport(Bukkit.getWorld("world").getSpawnLocation());
                    Messages.sendMessage(p, "&8[&6Téléportation&8] &aVous avez téléporté &e" + targetPlayer.getName() + " &aau spawn.");
                    Messages.sendMessage(targetPlayer, "&eVous venez d'être téléporté au spawn.");
                } else {
                    Messages.sendMessage(p, "&8[&6Téléportation&8] &cLe joueur &e" + target + " &cn'est pas connecté.");
                }
            }
        }
    }

    private final WarpManager warpManager = Main.getINSTANCE().getWarpManager();

    @Command("warp")
    public void warp(BukkitCommandActor actor, String warpName) {
        Player p = actor.requirePlayer();
        Warp warp = warpManager.getWarp(warpName);

        if (warp == null || warp.getLocation() == null) {
            p.sendTitle("§cErreur", "§4Ce warp n'existe pas !", 10, 70, 20);
            return;
        }

        if (warpName.length() == 1) {
            if (p.hasPermission("admin")) {
                p.teleport(warp.getLocation());
                p.sendMessage("§4§lAdmin -> TP à " + warpName + " !");
                return;
            }
            if (!DataUtils.readValue("mine", p.getUniqueId().toString()).equals(warpName.toUpperCase())) {
                p.sendTitle("§c", "§4Vous n'êtes pas à la bonne mine.", 10, 70, 20);
                return;
            } else {
                p.teleport(warp.getLocation());
                return;
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

    @Command("help")
    @Description("Afficher le menu d'aide")
    public void sendHelpMenu(BukkitCommandActor a) {
        Player p = a.asPlayer();
        Messages.sendMessage(p, "&8&m----------------------------------------");
        Messages.sendMessage(p, "&6&l➜ &7Liste des commandes disponibles:");
        Messages.sendMessage(p, "&6/rankup &8- &7Passer à la mine suivante.");
        Messages.sendMessage(p, "&6/prestige &8- &7Passer au prestige suivant.");
        Messages.sendMessage(p, "&6/is &8- &7Permet de se téléporter sur son île.");
        Messages.sendMessage(p, "&6/is create &8- &7Créer une île.");
        if (p.hasPermission("prison.event.organize")) {
            Messages.sendMessage(p, "&6/iname <name> &8- &7Changer le nom d'un item.");
            Messages.sendMessage(p, "&6/ilore <lore> &8- &7Changer la lore d'un item.");
            Messages.sendMessage(p, "&6/clearlore &8- &7Supprimer la lore d'un item.");
            Messages.sendMessage(p, "&6/confevent <type> &8- &7Configurer un événement.");
        }
        if (p.hasPermission("admin")) {
            Messages.sendMessage(p, "&6/createmine <mine> <price> <next> &8- &7Créer une mine.");
            Messages.sendMessage(p, "&6/deletemine <mine> &8- &7Supprimer une mine.");
            Messages.sendMessage(p, "&6/getmine <mine> &8- &7Obtenir les informations d'une mine.");
            Messages.sendMessage(p, "&6/createprestige <prestige> <price> <next> &8- &7Créer un prestige.");
            Messages.sendMessage(p, "&6/lookup <player> &8- &7Voir les informations d'un joueur.");
            Messages.sendMessage(p, "&6/ban <player> [<duree>] <reason> &8- &7Bannir un joueur.");
            Messages.sendMessage(p, "&6/unban <player> &8- &7Débannir un joueur.");
            Messages.sendMessage(p, "&6/mute <player> [<duree>] <reason> &8- &7Muter un joueur.");
            Messages.sendMessage(p, "&6/unmute <player> &8- &7Démuter un joueur.");
            Messages.sendMessage(p, "&6/tp <player> &8- &7Se téléporter à un joueur.");
            Messages.sendMessage(p, "&6/crate key give <player> <box> <quantité> &8- &7Demander à se téléporter à un joueur.");
        }
        Messages.sendMessage(p, "&8&m----------------------------------------");
    }

    @Command("lookup")
    @Description("Voir les informations d'un joueur")
    @CommandPermission("modo")
    public void lookup(BukkitCommandActor a, String p2){
        Player p = a.asPlayer();
        if(p2.contains(" ")){
            a.asPlayer().sendMessage("§cLe nom du joueur ne doit pas contenir d'espace");
            return;
        } else {
            Player target = Bukkit.getPlayer(p2);
            if(target == null){
                a.asPlayer().sendMessage("§cLe joueur n'est pas en ligne ou n'existe pas.");
                return;
            }
            String mine = DataUtils.readValue("mine", target.getUniqueId().toString()).toString();
            String money = DataUtils.readValue("money", target.getUniqueId().toString()).toString();
            String prestige = DataUtils.readValue("prestige", target.getUniqueId().toString()).toString();
            String blocs = DataUtils.readValue("blocs", target.getUniqueId().toString()).toString();
            a.asPlayer().sendMessage("§8[§6Lookup§8] §eLookup de §7" + target.getName());
            a.asPlayer().sendMessage("§8§m----------------------------------------");
            a.asPlayer().sendMessage("§7Nom: §e" + target.getName());
            a.asPlayer().sendMessage("§7UUID: §e" + target.getUniqueId());
            if(a.asPlayer().hasPermission("admin")){
                a.asPlayer().sendMessage("§7IP: §e" + target.getAddress().getAddress());
            }
            a.asPlayer().sendMessage("§7Mode de jeu: §e" + target.getGameMode());
            a.asPlayer().sendMessage("§7Position: §e" + target.getLocation().getBlockX() + " " + target.getLocation().getBlockY() + " " + target.getLocation().getBlockZ());
            a.asPlayer().sendMessage("§7Monde: §e" + target.getWorld().getName());
            a.asPlayer().sendMessage("§7Connecté depuis: §e" + target.getFirstPlayed());
            a.asPlayer().sendMessage("§7Dernière connexion: §e" + target.getLastPlayed());
            a.asPlayer().sendMessage("§7Mine: §e" + mine);
            a.asPlayer().sendMessage("§7Argent: §e" + money);
            a.asPlayer().sendMessage("§7Prestige: §e" + prestige);
            a.asPlayer().sendMessage("§7Blocs minés: §e" + blocs);
            a.asPlayer().sendMessage("§8§m----------------------------------------");
        }
    }

    @Command({"bug", "report"})
    public void bug(BukkitCommandActor actor, @Named("explications") String[] explication) {
        Player p = actor.asPlayer();
        String[] explications = explication;
        if(explications.length == 0) {
            p.sendMessage("§c§l[BUG] §7Vous devez expliquer le bug.");
            return;
        }
        p.sendTitle("§c§l[BUG]", "§7Merci pour votre signalement", 10, 40, 10);
        for(Player pl : p.hasPermission("prison.admin") ? p.getWorld().getPlayers() : p.getServer().getOnlinePlayers()) {
            pl.sendMessage("§c§l[BUG] §7" + p.getName() + " a signalé un bug: §6" + Arrays.toString(explication) + "§7.");
        }
    }
}