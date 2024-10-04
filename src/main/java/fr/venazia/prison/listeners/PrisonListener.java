package fr.venazia.prison.listeners;


import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;


import com.bgsoftware.superiorskyblock.api.island.Island;
import fr.venazia.prison.commands.ModerationCommand;
import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.utils.Placeholders;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import org.json.*;
import fr.venazia.prison.Main;
import fr.venazia.prison.utils.Messages;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import static fr.venazia.prison.commands.StaffContesteCommand.contest;
import static java.lang.Thread.sleep;

public class PrisonListener implements Listener {

    private final HashMap<UUID, Player> mineA = new HashMap<>();
    private final @Nonnull BossBar bossbar = null;


    @EventHandler
    public void moderationFreeze(PlayerMoveEvent e){
        Player p = e.getPlayer();
        if(ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)){
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void cspy(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if(!p.hasPermission("prison.moderation")){
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("prison.admin")) {
                    pl.sendMessage("§c§l[Surveillance] §c" + p.getName() + " §7: " + e.getMessage());
                }
            }
        }
    }


    @EventHandler
    public void moderationFreezeDeco(PlayerQuitEvent e){
        Player p = e.getPlayer();
        if(ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)){
            Bukkit.broadcastMessage("§cLe joueur " + p.getName() + " a été déconnecté alors qu'il était immobilisé. Il a donc été sanctionné d'un bannissement définitif");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " §cDéconnexion alors qu'il était immobilisé.");
            ModerationCommand.frozenPlayers.put(p, false);
        }
    }

    @EventHandler
    public void moderationFreezeHBChange(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)){
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void moderationFreezeChat(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        if(ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)){
            for(Player pl : Bukkit.getOnlinePlayers()){
                if(pl.hasPermission("prison.moderation")){
                    pl.sendMessage("§c§l[MOD] §c" + p.getName() + " §7: " + e.getMessage());
                }
            }
            e.setCancelled(true);
        }
    }



    public void firstQuest(Player p) throws IOException {
        final Component title = Component.text("§8[§b§l\uD83D\uDFC6§8] §aQuête actuelle: §e§l§nBienvenue !");
        final BossBar fullBar = BossBar.bossBar(title, 1, BossBar.Color.BLUE, BossBar.Overlay.NOTCHED_20);
        Audience a = Main.getINSTANCE().adventure().player(p);
        a.showBossBar(fullBar);
        DataUtils.writeValue("quest", p.getUniqueId().toString(), "1");

    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        String header1 = "§#921BFB§lV§#9136FC§le§#9051FC§ln§#8F6DFD§la§#8E88FE§lz§#8DA3FE§li§#8CBEFF§la §r◆ §d%server_online% §fjoueurs connectés \n §7Serveur OP Prison Français - §b§lplay/mc.venazia.fr \n §7Grade: §r%vault_prefix% \n §7Tokens: §r%azlink_tokens%";
        String footer1 = "§6§l✮ §eAchète des avantages en jeu: §7/f2w \n §6§l✮ §eAchète des avantages via la boutique: §7/boutique";
        String footer = PlaceholderAPI.setPlaceholders(p, footer1);
        String header = PlaceholderAPI.setPlaceholders(p, header1);
        String header2 = ChatColor.translateAlternateColorCodes('&', header);
        String footer2 = ChatColor.translateAlternateColorCodes('&', footer);
        p.setPlayerListHeaderFooter(header2, footer2);
        if (!p.hasPlayedBefore()) {
            try {
                firstQuest(p);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            UUID uuid = p.getUniqueId();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("uuid", uuid.toString());
            jsonObject.put("mine", "A");
            jsonObject.put("blocs", 0);
            jsonObject.put("prestige", 0);
            jsonObject.put("money", 0);
            File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
            if (!playersDir.exists()) {
                playersDir.mkdirs();
            }
            File playerFile = new File(playersDir, uuid + ".json");
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(jsonObject.toString(4));
                file.flush();
                Main.getINSTANCE().getServer().getConsoleSender().sendMessage("§aCréation d'un fichier de configuration pour le joueur " + p.getName());
            } catch (IOException error) {
                error.printStackTrace();
            }
        }
    }


    @EventHandler
    public void checkChatLocked(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (ModerationCommand.disabledCommands.get("chat")) {
            e.setCancelled(true);
            p.sendMessage("§cLe chat est actuellement désactivé par la modération.");
        }
    }

    @EventHandler
    public void checkIfCmdIsDisabled(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().split(" ")[0].replace("/", "");
        if (ModerationCommand.disabledCommands.get(cmd)) {
            e.setCancelled(true);
            p.sendMessage("§cLa commande §e/" + cmd + " §cest actuellement désactivée par la modération.");
        }
    }

    @EventHandler
    public void onSignChange(SignChangeEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        String l1 = e.getLine(0);
        String l2 = e.getLine(1);
        String l3 = e.getLine(2);
        String l4 = e.getLine(3);
        if (!SuperiorSkyblockAPI.getPlayer(uuid).isInsideIsland()) {
            if (!p.hasPermission("admin")) {
                Messages.sendMessage(p, "&cVous devez être dans votre île pour effectuer cette action.");
                e.getBlock().breakNaturally();
                return;
            } else {
                if (l1.equalsIgnoreCase("[Privé]")) {
                    if (l2.isEmpty()) {
                        Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cMerci de spécifier au moins un joueur");
                        e.getBlock().breakNaturally();
                    } else {
                        OfflinePlayer l2p = Bukkit.getOfflinePlayer(l2);
                        if (!l2p.hasPlayedBefore()) {
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cCe joueur n'a jamais rejoint le serveur !");
                        } else {
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l2p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                        }
                    }
                    if (l3.isEmpty()) {
                        Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
                    } else {
                        OfflinePlayer l3p = Bukkit.getOfflinePlayer(l3);
                        if (!l3p.hasPlayedBefore()) {
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cCe joueur n'a jamais rejoint le serveur !");
                        } else {
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l3p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                        }
                    }
                    if (l4.isEmpty()) {
                        Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
                    } else {
                        OfflinePlayer l4p = Bukkit.getOfflinePlayer(l4);
                        if (!l4p.hasPlayedBefore()) {
                            Messages.sendMessage(p, "&cCe joueur n'a jamais rejoint le serveur !");
                        } else {
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l4p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                            Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
                        }
                    }
                }
            }
        }
        if (l1.equalsIgnoreCase("[Privé]")) {
            if (l2.isEmpty()) {
                Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cMerci de spécifier au moins un joueur");
                e.getBlock().breakNaturally();
            } else {
                OfflinePlayer l2p = Bukkit.getOfflinePlayer(l2);
                if (!l2p.hasPlayedBefore()) {
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cCe joueur n'a jamais rejoint le serveur !");
                } else {
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l2p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                }
            }
            if (l3.isEmpty()) {
                Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
            } else {
                OfflinePlayer l3p = Bukkit.getOfflinePlayer(l3);
                if (!l3p.hasPlayedBefore()) {
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cCe joueur n'a jamais rejoint le serveur !");
                } else {
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l3p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                }
            }
            if (l4.isEmpty()) {
                Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
            } else {
                OfflinePlayer l4p = Bukkit.getOfflinePlayer(l4);
                if (!l4p.hasPlayedBefore()) {
                    Messages.sendMessage(p, "&cCe joueur n'a jamais rejoint le serveur !");
                } else {
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &a" + l4p.getName() + " &ea été correctement ajouté à la liste des personnes de confiance.");
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &bBloc protégé avec succès !");
                }
            }
        }
    }


    @EventHandler
    public void onBlockInteract(PlayerInteractEvent e) {
        Block b = e.getClickedBlock();
        Player p = e.getPlayer();
        if (b != null && b.getType().equals(Material.CHEST)) {
            if (!findAndHandleSignAround(b, p)) {
                e.setCancelled(true);
                Bukkit.getScheduler().runTaskLater(Main.getINSTANCE(), p::closeInventory, 1L);
            }
        }
    }

    private boolean findAndHandleSignAround(Block chestBlock, Player p) {
        int[][] offsets = {
                {0, 1, 0}, {0, -1, 0}, {1, 0, 0}, {-1, 0, 0},
                {0, 0, 1}, {0, 0, -1}, {1, 1, 0}, {-1, -1, 0},
                {1, -1, 0}, {-1, 1, 0}, {0, 1, 1}, {0, 1, -1},
                {1, 1, 0}, {1, -1, 0}, {-1, 1, 0}, {-1, -1, 0}
        };

        for (int[] offset : offsets) {
            Block relativeBlock = chestBlock.getRelative(offset[0], offset[1], offset[2]);

            if (relativeBlock.getType() == Material.OAK_SIGN || relativeBlock.getType() == Material.OAK_WALL_SIGN || relativeBlock.getType() == Material.ACACIA_SIGN || relativeBlock.getType() == Material.BIRCH_SIGN) {
                Sign sign = (Sign) relativeBlock.getState();
                if (sign.getLine(0).equalsIgnoreCase("[Privé]")) {
                    for (int i = 1; i < 4; i++) {
                        if (sign.getLine(i).equalsIgnoreCase(p.getName())) {
                            return true;
                        }
                    }
                    Messages.sendMessage(p, "&8[&cAnti-Trahison&8] &cVous ne pouvez pas accéder à ce coffre, car il est verrouillé par la protection Anti-Trahison.");
                    return false;
                }
            }
        }
        return true;

    }

    @EventHandler
    public void enderDragonDeath(EntityDeathEvent e) {
        Player p = e.getEntity().getKiller();
        Entity e2 = e.getEntity();
        if (e.getEntityType() == EntityType.ENDER_DRAGON) {
            if (e.getEntity().getKiller() instanceof Player) {
                p.sendMessage("§6§l✮ §eVous avez tué l'ender dragon !");
                Bukkit.broadcastMessage("§8[§6Evenements§8] §b" + p.getName() + " §7a tué l'ender dragon ! §c§lUN IMMENSE GG à LUI !");
                p.spawnParticle(Particle.FIREWORK, p.getLocation(), 1000);
            }
        }
    }

    @EventHandler
    public void join(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        Placeholders.endTravelsRemaining.put(p, 5);
        Placeholders.netherTravelsRemaining.put(p, 5);
    }


    @EventHandler
    public void portalEnter(PlayerPortalEvent e) {
        Player p = e.getPlayer();
        if (e.getCause() == PlayerTeleportEvent.TeleportCause.NETHER_PORTAL) {
            if (SuperiorSkyblockAPI.getPlayer(p.getUniqueId()).isInsideIsland()) {
                Island i = SuperiorSkyblockAPI.getPlayer(p.getUniqueId()).getIsland();
                if (i.getIslandLevel().intValue() >= 65) {
                    e.setCancelled(false);
                } else {
                    Messages.sendMessage(p, "&8[&bNiveaux&8] &cVous ne pouvez pas accéder au Nether, car votre île n'est pas assez élevée. Vous devez être minimum niveau 65.");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSignChange2(SignChangeEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("sign.color")) {
            for (int i = 0; i < 4; i++) {
                event.setLine(i, ChatColor.translateAlternateColorCodes('&', (Objects.requireNonNull(event.getLine(i)))));
            }
        }
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (contest.containsKey(p)) {
            ;
            Messages.sendCentered(p, "§x§1§E§6§2§F§B§lR§x§2§1§6§7§F§B§lé§x§2§4§6§B§F§B§lp§x§2§7§7§0§F§C§lo§x§2§A§7§4§F§C§ln§x§2§C§7§9§F§C§ls§x§2§F§7§E§F§C§le §x§3§2§8§2§F§D§le§x§3§5§8§7§F§D§ln§x§3§8§8§B§F§D§lr§x§3§B§9§0§F§D§le§x§3§E§9§4§F§E§lg§x§4§1§9§9§F§E§li§x§4§3§9§E§F§E§ls§x§4§6§A§2§F§E§lt§x§4§9§A§7§F§F§lr§x§4§C§A§B§F§F§lé§x§4§F§B§0§F§F§le");
            p.sendMessage("§6Votre réponse: §e" + e.getMessage());
            e.setCancelled(true);
            contest.remove(p);
        }

    }


    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        UUID uuid = p.getUniqueId();
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
        File playerFile = new File(playersDir, uuid + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
            } else {
                jsonObject = new JSONObject();
                jsonObject.put("blocs", 0);
            }
            int blocs = jsonObject.getInt("blocs");
            blocs++;
            jsonObject.put("blocs", blocs);
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(jsonObject.toString(4));
                file.flush();
            } catch (IOException error) {
                error.printStackTrace();
            }

        } catch (IOException | JSONException ex) {
            ex.printStackTrace();
        }
    }
}
