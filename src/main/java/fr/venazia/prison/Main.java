package fr.venazia.prison;

import fr.venazia.prison.commands.*;
import fr.venazia.prison.listeners.PrisonListener;
import fr.venazia.prison.utils.KitManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import fr.venazia.prison.listeners.PrisonListener;
import fr.venazia.prison.utils.KitManager;
import org.bukkit.Bukkit;
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



    public KitManager getKitManager() {
        return kitManager;
    }

    @Override
    public void onEnable() {
        logger = new PluginLogger(this);
        logger.info("Démarrage du plugin");
        INSTANCE = this;
        saveDefaultConfig();
        kitManager = new KitManager(this);
        kitManager.loadKits();
        regCommands();
        regListeners();
        Bukkit.broadcastMessage("Prison loading completed !");
        //Reg Tasks
        //new RegionsTask(this).runTaskTimer(this, 0, 20);
    }

    private void regListeners(){
        getServer().getPluginManager().registerEvents(new PrisonListener(), this);
    }

    public void regCommands() {
        //utils
        getCommand("createkit").setExecutor(new CreateKitCommand(Main.getINSTANCE()));
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
