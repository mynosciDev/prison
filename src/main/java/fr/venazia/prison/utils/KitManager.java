package fr.venazia.prison.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import fr.venazia.prison.utils.Kit;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import fr.venazia.prison.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class KitManager {

    private final Map<String, Kit> kits = new HashMap<>();
    private final JavaPlugin plugin;
    public final Gson gson;
    private final File kitsFolder;

    public KitManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.kitsFolder = new File(plugin.getDataFolder(), "kits");
        if (!kitsFolder.exists()) {
            kitsFolder.mkdirs();
        }

        // Initialize Gson with the custom TypeAdapter for Optional
        this.gson = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<Optional<Object>>(){}.getType(), new OptionalTypeAdapter<>(new Gson().getAdapter(Object.class)))
                .create();
    }

    public void createKitFromPlayer(Player player, String name, long cooldown) {
        List<ItemStack> items = Arrays.stream(player.getInventory().getContents())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
        createKit(name, cooldown, items);
        Bukkit.getLogger().info("Kit '" + name + "' créé avec succès à partir de l'inventaire du joueur " + player.getName());
    }

    public void createKit(String name, long cooldown, List<ItemStack> items) {
        Kit kit = new Kit(name, cooldown, items);
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
        Bukkit.broadcastMessage(kit.toString());
        try (FileWriter writer = new FileWriter(kitFile)) {
            gson.toJson(kit, writer);
        } catch (IOException e) {
            Bukkit.getLogger().severe("Erreur lors de la sauvegarde du kit: " + kit.getName());
            e.printStackTrace();
        }
    }
}
