package fr.venazia.prison.utils;

import fr.venazia.prison.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.json.JSONArray;
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


    public static void createShop(String UUID, double prix, String item, int amount, String loc, String name, String loc2) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "island");
        File playerFile = new File(playersDir, UUID + ".json");
        if (playersDir.mkdirs()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                if (content.trim().isEmpty()) {
                    jsonObject = new JSONObject();
                } else {
                    jsonObject = new JSONObject(content);
                }
            } else {
                jsonObject = new JSONObject();
            }
            JSONObject shop = new JSONObject();
            shop.put("prix", prix);
            shop.put("item", item);
            shop.put("amount", amount);
            shop.put("loc", loc);
            shop.put("owner", name);
            shop.put("chestLoc", loc2);
            if (!jsonObject.has("shops")) {
                jsonObject.put("shops", new JSONArray());
            }
            jsonObject.getJSONArray("shops").put(shop);
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
    public static Object readShop(String UUID, String loc, String value) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "island");
        File playerFile = new File(playersDir, UUID + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                if (jsonObject.has("shops")) {
                    JSONArray shops = jsonObject.getJSONArray("shops");
                    for (int i = 0; i < shops.length(); i++) {
                        JSONObject shop = shops.getJSONObject(i);
                        if (shop.getString("loc").equals(loc)) {
                            if (!shop.has(value) || shop.isNull(value)) {
                                Main.getINSTANCE().getLogger().warning("Une erreur est survenue lors de la lecture de la valeur " + value + " du shop de l'île " + UUID + " à la location " + loc + "::: Valeur nulle");
                                return null;
                            }
                            return shop.get(value);
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


    public static void setShop(String UUID, String loc, String value, String newValue) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "island");
        File playerFile = new File(playersDir, UUID + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                if (jsonObject.has("shops")) {
                    JSONArray shops = jsonObject.getJSONArray("shops");
                    for (int i = 0; i < shops.length(); i++) {
                        JSONObject shop = shops.getJSONObject(i);
                        if (shop.getString("loc").equals(loc)) {
                            shop.put(value, newValue);
                            try (FileWriter file = new FileWriter(playerFile)) {
                                file.write(jsonObject.toString(4));
                                file.flush();
                            } catch (IOException error) {
                                throw new IOException(error);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Object readValue(String key, String uuid)  {
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
            try {
                throw new IOException(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
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
                    return new ItemBuilder(Material.BARRIER).setName("§cIl n'y a pas d'items en vente ici !").toItemStack();
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


    public static void writeValue(String key, String uuid, String value) {
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
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

    public static Object readMineValue(String mine, String key) {
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
            try {
                throw new IOException(e);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }
        return null;
    }

    public static void deleteShop(String uuid, String location) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "island");
        File playerFile = new File(playersDir, uuid + ".json");
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                jsonObject = new JSONObject(content);
                if (jsonObject.has("shops")) {
                    JSONArray shops = jsonObject.getJSONArray("shops");
                    for (int i = 0; i < shops.length(); i++) {
                        JSONObject shop = shops.getJSONObject(i);
                        if (shop.getString("loc").equals(location)) {
                            shops.remove(i);
                            try (FileWriter file = new FileWriter(playerFile)) {
                                file.write(jsonObject.toString(4));
                                file.flush();
                            } catch (IOException error) {
                                throw new IOException(error);
                            }
                        }
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void createMine(String mine, int price, String next) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "mine");
        File playerFile = new File(playersDir, mine + ".json");
        if (playersDir.mkdirs()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                if (content.trim().isEmpty()) {
                    jsonObject = new JSONObject();
                } else {
                    jsonObject = new JSONObject(content);
                }
            } else {
                jsonObject = new JSONObject();
            }
            jsonObject.put("prix", price);
            jsonObject.put("next", next);
            jsonObject.put("name", mine);
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

    public static void deleteMine(String mine) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "mine");
        File playerFile = new File(playersDir, mine + ".json");
        playerFile.delete();
    }

    public static void createPrestige(String prestige, Integer price, String next) {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "prestige");
        File playerFile = new File(playersDir, prestige + ".json");
        if (playersDir.mkdirs()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (!playerFile.exists()) {
            try {
                playerFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            JSONObject jsonObject;
            if (playerFile.exists()) {
                String content = new String(Files.readAllBytes(Paths.get(playerFile.getPath())));
                if (content.trim().isEmpty()) {
                    jsonObject = new JSONObject();
                } else {
                    jsonObject = new JSONObject(content);
                }
            } else {
                jsonObject = new JSONObject();
            }
            jsonObject.put("prix", price);
            jsonObject.put("next", next);
            jsonObject.put("name", prestige);
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
}
