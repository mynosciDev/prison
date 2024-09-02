package fr.venazia.prison;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.command.WorldEditCommands;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.commands.WorldGuardCommands;
import fr.venazia.prison.commands.*;
import fr.venazia.prison.listeners.PrisonListener;
import fr.venazia.prison.utils.KitManager;
import fr.venazia.prison.utils.WarpManager;
import fr.venazia.prison.warps.Warp;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;



public final class Main extends JavaPlugin {
    private KitManager kitManager;
    private static PluginLogger logger;
    private static Main INSTANCE;
    public boolean isDebug = getConfig().getBoolean("debug");
    public static Main getINSTANCE() {
        return INSTANCE;
    }
    private WarpManager warpManager;

    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public void onEnable() {
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
    }

    public void regOthers() {
        // Reg Warps
        World divers = Bukkit.getWorld("divers");
        World mines = Bukkit.getWorld("mines");
        World pvp = Bukkit.getWorld("pvp");
        warpManager.addWarp("tuto", new Location(divers,  458, 114, 1198));
        warpManager.addWarp("mines", new Location(mines, -1073, 23, -1275));
        warpManager.addWarp("pvp", new Location(pvp, 84, -9, 3));
    }


    public void regCommands() {
        //utils
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
        getCommand("blocksbroken").setExecutor(new BlocksCommand());
        getCommand("spawn").setExecutor(new SpawnCommand());
        getCommand("minereset").setExecutor(new MineResetCommand());
        getCommand("rankup").setExecutor(new RankupCommand());
        getCommand("money").setExecutor(new MoneyCommand());
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}

