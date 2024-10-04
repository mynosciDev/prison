package fr.venazia.prison;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.command.WorldEditCommands;
import com.sk89q.worldedit.command.WorldEditCommandsRegistration;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.commands.WorldGuardCommands;
import fr.venazia.prison.commands.*;
import fr.venazia.prison.listeners.PrisonListener;
import fr.venazia.prison.utils.KitManager;
import fr.venazia.prison.utils.Placeholders;
import fr.venazia.prison.utils.WarpManager;
import fr.venazia.prison.warps.Warp;
import net.kyori.adventure.platform.bukkit.BukkitAudiences;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.defaults.HelpCommand;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import revxrsal.commands.Lamp;
import revxrsal.commands.bukkit.BukkitLamp;
import revxrsal.commands.bukkit.actor.BukkitCommandActor;


public final class Main extends JavaPlugin {
    private KitManager kitManager;
    private static PluginLogger logger;
    private static Main INSTANCE;
    public static Main getINSTANCE() {
        return INSTANCE;
    }
    private WarpManager warpManager;
    private BukkitAudiences adventure;

    public @NonNull BukkitAudiences adventure() {
        if(this.adventure == null) {
            throw new IllegalStateException("Tried to access Adventure when the plugin was disabled!");
        }
        return this.adventure;
    }

    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        this.warpManager = new WarpManager();
        logger = new PluginLogger(this);
        logger.info("Démarrage du plugin");
        INSTANCE = this;
        saveDefaultConfig();
        this.kitManager = new KitManager(this);
        kitManager.loadKits();
        regCommands();
        regListeners();
        regOthers();
        Bukkit.broadcastMessage("Prison loading completed !");
        //Reg Tasks

        //new RegionsTask(this).runTaskTimer(this, 0, 20);
    }

    public WarpManager getWarpManager() {
        return warpManager;
    }

    private void regListeners(){
        getServer().getPluginManager().registerEvents(new PrisonListener(), this);
        getServer().getPluginManager().registerEvents(new GodCommand(), this);
        getServer().getPluginManager().registerEvents(new VoyageCommand(), this);
        getServer().getPluginManager().registerEvents(new EventCommand(), this);
    }

    public void regOthers() {
        // Reg Warps
        World divers = Bukkit.getWorld("divers");
        World mines = Bukkit.getWorld("mines");
        World pvp = Bukkit.getWorld("pvp");
        warpManager.addWarp("a", new Location(mines, -1070, 23, -1275));
        warpManager.addWarp("b", new Location(mines, -1075, 23, -1195));
        warpManager.addWarp("tuto", new Location(divers,  458, 114, 1198));
        warpManager.addWarp("mines", new Location(mines, -1073, 23, -1275));
        warpManager.addWarp("pvp", new Location(pvp, 84, -9, 3));
        warpManager.addWarp("c", new Location(mines, -1106, 23, -1356));
        warpManager.addWarp("d", new Location(mines, -1140, 23, -1376));
        warpManager.addWarp("e", new Location(mines, -1151, 23, -1259));
        warpManager.addWarp("f", new Location(mines, -1211, 24, -1256));
        warpManager.addWarp("g", new Location(mines, -1211, 23, -1339));
        warpManager.addWarp("h", new Location(mines, -1255, 23, -1194));
        warpManager.addWarp("j", new Location(mines, -1081, 24, -869));
        warpManager.addWarp("i", new Location(mines, -1363, 23, -931));
        warpManager.addWarp("k", new Location(mines, -1196, 25, -874));
        warpManager.addWarp("l", new Location(mines, -1211, 23, -1017));
        //Reg Placeholders
        new Placeholders(this).register();
    }


    public void regCommands() {
        Lamp<BukkitCommandActor> b = BukkitLamp.builder(this).build();
        b.register(new ModerationCommand(), new BugCommand(), new SellCommands(), new EventCommand());
        getCommand("permile").setExecutor(new UtilsCommand());
        getCommand("acceptquest").setExecutor(new AcceptQuestCommand());
        getCommand("darkmarket").setExecutor(new DarkMarketCommand());
        getCommand("prestige").setExecutor(new PrestigeCommand());
        getCommand("market").setExecutor(new MarketCommand());
        getCommand("voyages").setExecutor(new VoyageCommand());
        getCommand("warp").setExecutor(new WarpCommand(getWarpManager()));
        getCommand("god").setExecutor(new GodCommand());
        kitManager = new KitManager(this);
        getCommand("createkit").setExecutor(new CreateKitCommand(getKitManager()));
        getCommand("kit").setExecutor(new KitCommand(Main.getINSTANCE()));
        getCommand("broadcast").setExecutor(new BroadcastCommand());
        getCommand("gamemode").setExecutor(new GamemodeCommand());
        getCommand("lookup").setExecutor(new LookupCommand());
        getCommand("valeur").setExecutor(new ValueCommand());
        getCommand("jailconteste").setExecutor(new StaffContesteCommand());
        getCommand("prison").setExecutor(new PrisonCommand());
        getCommand("prison").setTabCompleter(new PrisonCommand());
        getCommand("help").setExecutor(new UtilsCommand());
        getCommand("iname").setExecutor(new UtilsCommand());
        getCommand("uptime").setExecutor(new UtilsCommand());
        getCommand("lore").setExecutor(new UtilsCommand());
        getCommand("blocksbroken").setExecutor(new BlocksCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("minereset").setExecutor(new MineResetCommand());
        getCommand("rankup").setExecutor(new RankupCommand());
        getCommand("money").setExecutor(new MoneyCommand());
        getCommand("chance").setExecutor(new ChanceCommand());
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
        }
        logger.info("Arrêt du plugin");
    }
}

