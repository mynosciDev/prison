package fr.venazia.prison.listeners;


import com.bgsoftware.superiorskyblock.api.SuperiorSkyblockAPI;


import com.bgsoftware.superiorskyblock.api.island.Island;
import com.bgsoftware.superiorskyblock.api.wrappers.SuperiorPlayer;
import fr.venazia.prison.commands.ModerationCommand;
import fr.venazia.prison.utils.DataUtils;
import fr.venazia.prison.utils.Placeholders;
import fr.venazia.prison.utils.TabListUpdater;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Container;
import org.bukkit.block.Sign;
import org.bukkit.block.data.type.Chest;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.server.ServerEvent;
import org.bukkit.event.server.TabCompleteEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
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
import java.util.*;

import static java.lang.Thread.enumerate;
import static java.lang.Thread.sleep;

public class PrisonListener implements Listener {

    private final HashMap<UUID, Player> mineA = new HashMap<>();
    private final @Nonnull BossBar bossbar = null;


    @EventHandler
    public void moderationFreeze(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)) {
            e.setCancelled(true);
        }
    }


    @EventHandler
    public void cspy(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        if (!p.hasPermission("prison.moderation")) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("prison.admin")) {
                    if (p.getName().equalsIgnoreCase("Laynox_YT") || p.getName().equalsIgnoreCase("Cryptos_YT") || p.getName().equalsIgnoreCase("Wanixi") || p.getName().equalsIgnoreCase("SynaxxYT")) {
                        pl.sendMessage("§c§l[Surveillance] §c" + p.getName() + " §7: " + e.getMessage());
                    }
                }
            }
        }
    }

    @EventHandler
    public void moderationFreezeDeco(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        if (ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)) {
            Bukkit.broadcastMessage("§cLe joueur " + p.getName() + " a été déconnecté alors qu'il était immobilisé. Il a donc été sanctionné d'un bannissement définitif");
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "ban " + p.getName() + " §cDéconnexion alors qu'il était immobilisé.");
            ModerationCommand.frozenPlayers.put(p, false);
        }
    }

    @EventHandler
    public void breakChance(BlockBreakEvent e) {
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
            int chance = (blocs / 1000) * 10;
            ItemStack itemInHand = p.getInventory().getItemInMainHand();
            if (itemInHand != null && itemInHand.hasItemMeta()) {
                ItemMeta meta = itemInHand.getItemMeta();
                NamespacedKey key = new NamespacedKey(Main.getINSTANCE(), "CHANCE");
                meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, chance);
                itemInHand.setItemMeta(meta);
            }

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

    @EventHandler
    public void moderationFreezeHBChange(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if (ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void moderationFreezeChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        if (ModerationCommand.frozenPlayers.get(p) != null && ModerationCommand.frozenPlayers.get(p)) {
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("prison.moderation")) {
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
    public void chat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();
        String message = e.getMessage();
        e.setCancelled(true);
        if (message.startsWith("#")) {
            if (!p.hasPermission("prison.moderation")) {
                return;
            }
            for (Player pl : Bukkit.getOnlinePlayers()) {
                if (pl.hasPermission("prison.moderation")) {
                    pl.sendMessage("§7[§cStaff§7] §c" + p.getName() + " §8» §f" + message.replaceFirst("#", ""));
                }
            }
            return;
        }
        String prefix = PlaceholderAPI.setPlaceholders(p, "%vault_prefix%");
        prefix = prefix.replace("&", "§");
        for(Player pa : Bukkit.getOnlinePlayers()){
            pa.sendMessage("§7" + prefix + p.getName() + " §8» §f" + message);
        }
    }




    @EventHandler
    public void deleteShop(BlockBreakEvent e) {
        Player p = e.getPlayer();
        Block b = e.getBlock();
        if (b.getType() == Material.OAK_SIGN || b.getType() == Material.OAK_WALL_SIGN || b.getType() == Material.ACACIA_SIGN || b.getType() == Material.BIRCH_SIGN) {
            Sign sign = (Sign) b.getState();
            if (sign.getLine(0).equalsIgnoreCase("[Shop]")) {
                String UUID = SuperiorSkyblockAPI.getPlayer(p).getIsland().getUniqueId().toString();
                String location = b.getLocation().toString();
                if (!Objects.equals(DataUtils.readShop(UUID, location, "owner").toString(), p.getName())) {
                    Bukkit.broadcastMessage(DataUtils.readShop(UUID, location, "owner").toString() + "/= " + p.getName());
                    Messages.sendMessage(p, "&8[&aShops&8] &cVous ne pouvez pas supprimer le shop d'un autre joueur. Si il ne fait plus parti de votre île, contactez un staff");
                    e.setCancelled(true);
                } else {
                    DataUtils.deleteShop(UUID, location);
                    Messages.sendMessage(p, "&8[&aShops&8] &cLe shop a été supprimé avec succès.");
                }
            }
        }
    }

    @EventHandler
    public void isShop(SignChangeEvent e) {
        Player p = e.getPlayer();
        int amount;
        SuperiorPlayer sp = SuperiorSkyblockAPI.getPlayer(p.getUniqueId());
        boolean isInside = SuperiorSkyblockAPI.getPlayer(p.getUniqueId()).isInsideIsland();
        if (isInside) {
            Island i = sp.getIsland();
            if (e.getLine(0).equalsIgnoreCase("[Shop]")) {
                if (e.getLine(1).isEmpty()) {
                    Messages.sendMessage(p, "&8[&aShops&8] &cMerci de spécifier un prix.");
                    e.getBlock().breakNaturally();
                } else {
                    double price = Double.parseDouble(e.getLine(1));
                    if (price < 0) {
                        Messages.sendMessage(p, "&8[&aShops&8] &cLe prix ne peut pas être négatif. Si vous souhaitez mettre gratuitement, mettez 0.");
                        e.getBlock().breakNaturally();
                        return;
                    }
                    String itemId;
                    if (e.getLine(2).isEmpty()) {
                        Messages.sendMessage(p, "&8[&aShops&8] &cMerci de spécifier un objet. Fait &e/iteminfo &cpour obtenir l'ID de l'objet.");
                        e.getBlock().breakNaturally();
                        return;
                    } else {
                        itemId = e.getLine(2);
                        Material material = Material.matchMaterial(itemId);
                        if (material == null) {
                            Messages.sendMessage(p, "&8[&aShops&8] &cCet objet n'existe pas.");
                            e.getBlock().breakNaturally();
                            return;
                        }
                        if (e.getLine(3).isEmpty()) {
                            Messages.sendMessage(p, "&8[&aShops&8] &cMerci de spécifier une quantité.");
                            e.getBlock().breakNaturally();
                            return;
                        } else {
                            try {
                                amount = Integer.parseInt(e.getLine(3));
                            } catch (NumberFormatException ex) {
                                Messages.sendMessage(p, "&8[&aShops&8] &cLa quantité doit être un nombre.");
                                e.getBlock().breakNaturally();
                                return;
                            }
                        }
                    }
                    Block signBlock = e.getBlock();
                    Block chestBlock = findChestNearSign(signBlock);
                    if (chestBlock == null) {
                        Messages.sendMessage(p, "&8[&aShops&8] &cAucun coffre trouvé à proximité du panneau.");
                        e.getBlock().breakNaturally();
                        return;
                    }
                    String chestLocation = chestBlock.getLocation().toString();
                    Messages.sendMessage(p, "&8[&aShops&8] &aLe shop a été créé avec succès !");
                    Messages.sendMessage(p, "&8[&aShops&8] &aFait &e/shopile &aen visant le panneau pour modifier &eles prix &aou &eles objets que tu vends");
                    DataUtils.createShop(sp.getIsland().getUniqueId().toString(), price, itemId, amount, signBlock.getLocation().toString(), p.getName(), chestLocation);
                }
            }
        }
    }

        private Block findChestNearSign (Block signBlock){
            Block[] adjacentBlocks = {
                    signBlock.getRelative(BlockFace.NORTH),
                    signBlock.getRelative(BlockFace.SOUTH),
                    signBlock.getRelative(BlockFace.EAST),
                    signBlock.getRelative(BlockFace.WEST),
                    signBlock.getRelative(BlockFace.UP),
                    signBlock.getRelative(BlockFace.DOWN)
            };

            for (Block block : adjacentBlocks) {
                if (block.getType() == Material.CHEST || block.getType() == Material.TRAPPED_CHEST) {
                    return block;
                }
            }
            return null;
        }

    private Location parseLocationString(String locString) {
        try {
            String[] parts = locString.replace("Location{world=", "").replace("}", "").split(",");
            String worldName = parts[0].split("=")[1].trim();
            double x = Double.parseDouble(parts[1].split("=")[1]);
            double y = Double.parseDouble(parts[2].split("=")[1]);
            double z = Double.parseDouble(parts[3].split("=")[1]);
            float pitch = Float.parseFloat(parts[4].split("=")[1]);
            float yaw = Float.parseFloat(parts[5].split("=")[1]);
            World world = Bukkit.getWorld(worldName);
            if (world == null) {
                Bukkit.getLogger().warning("World not found: " + worldName);
                return null;
            }
            return new Location(world, x, y, z, yaw, pitch);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    @EventHandler
    public void signRightClick(PlayerInteractEvent e) {
        Player p = e.getPlayer();
        if(e.getClickedBlock().getType() == Material.AIR || e.getClickedBlock() == null){
            return;
        }
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.LEFT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.LEFT_CLICK_AIR || e.getAction() == Action.PHYSICAL || e.getAction() == Action.LEFT_CLICK_AIR) {
            Block b = e.getClickedBlock();
            if (b != null && b.getType().equals(Material.OAK_SIGN) || b.getType().equals(Material.OAK_WALL_SIGN) || b.getType().equals(Material.ACACIA_SIGN) || b.getType().equals(Material.BIRCH_SIGN)) {
                Sign sign = (Sign) b.getState();
                if (sign.getLine(0).equalsIgnoreCase("[Shop]")) {
                    String UUID = SuperiorSkyblockAPI.getPlayer(p).getIsland().getUniqueId().toString();
                    String location = p.getTargetBlockExact(100).getLocation().toString();
                    Object owner = DataUtils.readShop(UUID, location, "owner");
                    Object item = DataUtils.readShop(UUID, location, "item");
                    String chestLocString = DataUtils.readShop(UUID, location, "chestLoc").toString();
                    Location loc = parseLocationString(chestLocString);
                    if(item == null){
                        Messages.sendMessage(p, "&8[&aShops&8] &cErreur lors de la lecture de l'item mis en vente. Contacte un administrateur.");
                    }
                    if(loc == null){
                        Messages.sendMessage(p, "&8[&aShops&8] &cErreur lors de la lecture de la localisation du coffre. Contacte un administrateur.");
                    }
                    if (owner == p.getName()) {
                        Messages.sendMessage(p, "&8[&aShops&8] &cVous ne pouvez pas &6vendre/acheter &cà votre propre shop. Si vous pensez qu'il s'agit d'une erreur, contactez un administrateur.");
                    }
                    Container c = (Container) loc.getBlock().getState();
                    if(c == null){
                        Messages.sendMessage(p, "&8[&aShops&8] &cErreur lors de la lecture du coffre. Contacte un administrateur.");
                    }
                    if(c.getInventory().contains(Material.getMaterial(item.toString()))){
                        c.getInventory().removeItem(new ItemStack(Material.getMaterial(item.toString()), 1));
                        p.getInventory().addItem(new ItemStack(Material.getMaterial(item.toString()), 1));
                        Messages.sendMessage(p, "&8[&aShops&8] &aVous avez acheté &e" + item + " &apour &e" + DataUtils.readShop(UUID, location, "prix") + "§a$");
                        Player ownerP = Bukkit.getPlayer(owner.toString());
                        if(ownerP != null){
                            Messages.sendMessage(ownerP, "&7&lVENTE >> &8[&aShops&8] &aVous avez vendu &e" + item + " &aà &e" + p.getName() + " &apour &e" + DataUtils.readShop(UUID, location, "prix") + "§a$");
                        }
                    } else {
                        Messages.sendMessage(p, "&8[&aShops&8] &cLe shop est actuellement en rupture de stock.");
                        if(owner != null){
                            Player ownerP = Bukkit.getPlayer(owner.toString());
                            if(ownerP != null){
                                Messages.sendMessage(ownerP, "&8[&aShops&8] &c&oIl semblerait que votre shop au cordonnées &7" + loc.getBlockX() + " " + loc.getBlockY() + " " + loc.getBlockZ() + " &csoit en rupture de stock.");
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void chestShopProtect(PlayerInteractEvent e){
        Player p = e.getPlayer();
        if(e.getClickedBlock().getType() == Material.AIR){
            return;
        }
        if(p.hasPermission("admin")){
            return;
        }
        if(e.getClickedBlock().getType() == Material.CHEST || e.getClickedBlock().getType() == Material.TRAPPED_CHEST){
            Block b = e.getClickedBlock();
            String UUID = SuperiorSkyblockAPI.getPlayer(p).getIsland().getUniqueId().toString();
            String location = b.getLocation().toString();
            if(DataUtils.readShop(UUID, location, "owner") != null){
                if(!Objects.equals(DataUtils.readShop(UUID, location, "owner").toString(), p.getName())){
                    Messages.sendMessage(p, "&8[&aShops&8] &cVous ne pouvez pas ouvrir le shop d'un autre joueur.");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void signShopProtect(SignChangeEvent e){
        Player p = e.getPlayer();
        if(p.hasPermission("admin")){
            return;
        }
        if(e.getLine(0).equalsIgnoreCase("[Shop]")){
            String UUID = SuperiorSkyblockAPI.getPlayer(p).getIsland().getUniqueId().toString();
            String location = p.getTargetBlockExact(100).getLocation().toString();
            if(DataUtils.readShop(UUID, location, "owner") != null){
                if(!Objects.equals(DataUtils.readShop(UUID, location, "owner").toString(), p.getName())){
                    Messages.sendMessage(p, "&8[&aShops&8] &cVous ne pouvez pas modifier le shop d'un autre joueur.");
                    e.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
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
        if (ModerationCommand.disabledCommands.get("chat") != null) {
            e.setCancelled(true);
            p.sendMessage("§cLe chat est actuellement désactivé par la modération.");
        } else if(p.hasPermission("prison.moderation")){
            e.setCancelled(false);
        }
    }

    @EventHandler
    public void playerDisallow(PlayerPreLoginEvent e) {
        if (ModerationCommand.disabledCommands.get(e.getName()) != null) {
            e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§3§oTu n'es pas autorisé à rejoindre le serveur pour le moment. Réessaie plus tard ou demande à un administrateur.");
            return;
        } else if(ModerationCommand.disabledCommands.get("maintenance") != null){
            e.disallow(PlayerPreLoginEvent.Result.KICK_OTHER, "§6§oLe serveur est actuellement en maintenance. Réessaie plus tard ou demande à un administrateur.");
            return;
        }
    }

    @EventHandler
    public void checkIfCmdIsDisabled(PlayerCommandPreprocessEvent e) {
        Player p = e.getPlayer();
        String cmd = e.getMessage().split(" ")[0].replace("/", "");
        if (ModerationCommand.disabledCommands.get(cmd) != null) {
            if(p.hasPermission("prison.moderation")){
                return;
            }
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
}
