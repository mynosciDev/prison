package fr.venazia.prison;

import fr.venazia.prison.commands.PrisonCommand;

import org.bukkit.plugin.PluginLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.DatabaseMetaData;
import java.util.Timer;

public final class Main extends JavaPlugin {

    private static PluginLogger logger;
    private static Main INSTANCE;
    public boolean isDebug = getConfig().getBoolean("debug");
    public static Main getINSTANCE() {
        return INSTANCE;
    }

    @Override
    public void onEnable() {
        logger.info("Démarrage du plugin");
        INSTANCE = this;
        saveDefaultConfig();
        System.out.println((!isDebug) ?"Le mode debug est désactivé" : "Le mode debug est activé");
        regCommands();

    }

    public void regCommands() {
        getCommand("prison").setExecutor(new PrisonCommand());
        getCommand("prison").setTabCompleter(new PrisonCommand());

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
