package fr.venazia.prison.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import fr.venazia.prison.utils.Kit;
import org.bukkit.Bukkit;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class KitManager {
    private final Map<String, Kit> kits = new HashMap<>();
    private final JavaPlugin plugin;
    private final Gson gson = new Gson();
    private final File kitsFolder;

    public KitManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.kitsFolder = new File(plugin.getDataFolder(), "kits");
        if (!kitsFolder.exists()) {
            kitsFolder.mkdirs();
        }
    }

    public void createKit(String name, List<ItemStack> items, long cooldown) {
        Kit kit = new Kit(name, items, cooldown);
        kits.put(name, kit);
        saveKitToFile(kit);
    }

    public Kit getKit(String name) {
        return kits.get(name);
    }

    public void loadKits() {
        File[] files = kitsFolder.listFiles((dir, name) -> name.endsWith(".json"));
        if (files != null) {
            for (File file : files) {
                try (FileReader reader = new FileReader(file)) {
                    Type kitType = new TypeToken<Kit>(){}.getType();
                    Kit kit = gson.fromJson(reader, kitType);
                    kits.put(kit.getName(), kit);
                } catch (IOException e) {
                    Bukkit.getLogger().severe("Erreur lors du chargement du kit: " + file.getName());
                    e.printStackTrace();
                }
            }
        }
    }

    private void saveKitToFile(Kit kit) {
        File kitFile = new File(kitsFolder, kit.getName() + ".json");
        try (FileWriter writer = new FileWriter(kitFile)) {
            gson.toJson(kit, writer);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Erreur lors de la sauvegarde du kit: " + kit.getName());
            e.printStackTrace();
        }
    }
}
