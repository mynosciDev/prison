package fr.venazia.prison.utils;

import fr.venazia.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class DataUtils {
    /*
      DataUtils class
      Get information about playerData, mines
     */


    public static List<String> getWarps() {
        File warpDir = new File(Main.getINSTANCE().getDataFolder(), "warps");
        File[] files = warpDir.listFiles();
        List<String> warps = new ArrayList<>();
        for (File file : files) {
            if (file.getName().endsWith(".json")) {
                warps.add(file.getName());
            }
        }
        return warps;
    }



    public static Object readValue(String key, String uuid) throws IOException {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
        File playerFile = new File(playersDir, uuid + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                return jsonObject.get(key);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return null;
    }

    public static Object setMarketGlobal(String key, Object value) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "market");
        File playerFile = new File(playersDir, "global.json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
            } else {
                jsonObject = new JSONObject();
            }
            jsonObject.put(key, value);
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(jsonObject.toString(4));
                file.flush();
            } catch (IOException error) {
                throw new IOException(error);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Object readMarketGlobal(String key) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "market");
        File playerFile = new File(playersDir, "global.json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                return jsonObject.get(key);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static ItemStack readMarketValue(String key, Integer v) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "market");
        File playerFile = new File(playersDir, v + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                if (jsonObject.has(key) && !jsonObject.isNull(key) && jsonObject.get(key) instanceof JSONObject) {
                    JSONObject itemJson = jsonObject.getJSONObject(key);
                    return jsonToItemStack(itemJson);
                } else {
                    return new ItemBuilder(Material.BARRIER).displayname("§cIl n'y a pas d'items en vente ici !").build();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static JSONObject itemStackToJson(ItemStack itemStack) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("type", itemStack.getType().name());
        jsonObject.put("amount", itemStack.getAmount());

        ItemMeta meta = itemStack.getItemMeta();
        if (meta != null) {
            JSONObject metaJson = new JSONObject();
            if (meta.hasDisplayName()) {
                metaJson.put("displayName", meta.getDisplayName());
            }
            jsonObject.put("meta", metaJson);
        }
        return jsonObject;
    }

    public static ItemStack jsonToItemStack(JSONObject jsonObject) {
        Material material = Material.getMaterial(jsonObject.getString("type"));
        int amount = jsonObject.getInt("amount");
        ItemStack itemStack = new ItemStack(material, amount);

        if (jsonObject.has("meta")) {
            JSONObject metaJson = jsonObject.getJSONObject("meta");
            ItemMeta meta = itemStack.getItemMeta();
            if (meta != null) {
                if (metaJson.has("displayName")) {
                    meta.setDisplayName(metaJson.getString("displayName"));
                }
                itemStack.setItemMeta(meta);
            }
        }
        return itemStack;
    }




    public static void setMarketItem(Integer v, ItemStack i, UUID uuid, Integer price, Integer a) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "market");
        File playerFile = new File(playersDir, v + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
            } else {
                jsonObject = new JSONObject();
            }
            jsonObject.put("seller", Bukkit.getPlayer(uuid).getName());
            jsonObject.put("item", itemStackToJson(i));
            jsonObject.put("price", price);
            jsonObject.put("amount", a);
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(jsonObject.toString(4));
                file.flush();
            } catch (IOException error) {
                throw new IOException(error);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public static void writeValue(String key, String uuid, String value) throws IOException {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "players");
        File playerFile = new File(playersDir, uuid + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
            } else {
                jsonObject = new JSONObject();
            }
            jsonObject.put(key, value);
            try (FileWriter file = new FileWriter(playerFile)) {
                file.write(jsonObject.toString(4));
                file.flush();
            } catch (IOException error) {
                throw new IOException(error);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
    }



    public static Object readPrestigeValue(String prestige, String key){
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "prestige");
        File playerFile = new File(playersDir, prestige + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                return jsonObject.get(key);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public static Object readMineValue(String mine, String key) throws IOException {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "mine");
        File playerFile = new File(playersDir, mine + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                return jsonObject.get(key);
            }
        } catch (IOException e) {
            throw new IOException(e);
        }
        return null;
    }
}
