package fr.venazia.prison.utils;

import fr.venazia.prison.Main;
import org.json.JSONObject;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public static Object readMineValue(String[] mine, String key) throws IOException {
        File playersDir = new File(Main.getINSTANCE().getDataFolder(), "mines");
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
