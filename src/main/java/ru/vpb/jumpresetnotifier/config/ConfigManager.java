package ru.vpb.jumpresetnotifier.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.client.MinecraftClient;
import ru.vpb.jumpresetnotifier.JumpResetNotifier;

import java.io.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ConfigManager {
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private static final File CFG_FILE = new File(mc.runDirectory, "config/jumpreset-notifier.json");

    private static final ExecutorService executorService = Executors.newSingleThreadExecutor();

    public static void save() {
        executorService.submit(ConfigManager::saveInternal);
    }

    public static void load() {
        executorService.submit(ConfigManager::loadInternal);
    }

    private static void saveInternal() {
        JsonObject jsonObject = write();
        String jsonContent = new GsonBuilder().setPrettyPrinting().create().toJson(jsonObject);

        try (FileWriter fw = new FileWriter(CFG_FILE)){
            fw.write(jsonContent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void loadInternal() {
        try (FileReader fr = new FileReader(CFG_FILE)) {
            JsonObject jsonObject = new Gson().fromJson(fr, JsonObject.class);

            load(jsonObject);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static JsonObject write() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("x", JumpResetNotifier.jrnDraggable.getRelX());
        jsonObject.addProperty("y", JumpResetNotifier.jrnDraggable.getRelY());

        return jsonObject;
    }

    private static void load(JsonObject jsonObject) {
        if (jsonObject.isJsonNull() || !jsonObject.has("x") || !jsonObject.has("y")) return;

        double relX = jsonObject.get("x").getAsDouble(), relY = jsonObject.get("y").getAsDouble();

       JumpResetNotifier.jrnDraggable.setRelX(relX);
       JumpResetNotifier.jrnDraggable.setRelY(relY);

       JumpResetNotifier.jrnDraggable.setTargetX(relX * mc.getWindow().getScaledWidth());
       JumpResetNotifier.jrnDraggable.setTargetY(relY * mc.getWindow().getScaledHeight());
    }
}
