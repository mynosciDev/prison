package fr.venazia.prison;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.command.WorldEditCommands;
import com.sk89q.worldedit.command.WorldEditCommandsRegistration;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.commands.WorldGuardCommands;
import fr.venazia.prison.commands.*;
import fr.venazia.prison.listeners.PrisonListener;
import fr.venazia.prison.utils.*;
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





    @Override
    public void onEnable() {
        this.adventure = BukkitAudiences.create(this);
        logger = new PluginLogger(this);
        logger.info("Démarrage du plugin");
        INSTANCE = this;
        saveDefaultConfig();
        regCommands();
        regListeners();
        this.warpManager = new WarpManager();
        regOthers();
        Bukkit.broadcastMessage("Prison loading completed !");
        //Reg Tasks
        //new RegionsTask(this).runTaskTimer(this, 0, 20);
        TabListUpdater.startTabListUpdater();
    }



    public WarpManager getWarpManager() {
        return warpManager;
    }

    private void regListeners(){
        getServer().getPluginManager().registerEvents(new PrisonListener(), this);
        getServer().getPluginManager().registerEvents(new VoyageCommand(), this);
        getServer().getPluginManager().registerEvents(new EventCommand(), this);
        getServer().getPluginManager().registerEvents(new KitCommand(), this);
    }

    public void regOthers() {
        // Reg Warps
        //Reg Placeholders
        new Placeholders(this).register();
    }





    public void regCommands() {
        Lamp<BukkitCommandActor> b = BukkitLamp.builder(this)
                .exceptionHandler(new BukkitExceptionHandleCommand())
                .build();
        b.register(
                new KitCommand(),
                new ModerationCommand(),
                new SellCommands(),
                new BlocksCommand(),
                new EventCommand(),
                new MarketCommand(),
                new MoneyCommand(),
                new Challenges(),
                new VoyageCommand(),
                new ShopsCommand(),
                new UtilsCommand(),
                new RankupCommand()
        );
    }

    @Override
    public void onDisable() {
        if(this.adventure != null) {
            this.adventure.close();
        }
        logger.info("Arrêt du plugin");
    }
}

