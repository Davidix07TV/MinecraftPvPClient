package com.pvpclient.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ConfigManager {
    public static final ConfigManager INSTANCE = new ConfigManager();

    private static final String FILENAME = "pvpclient_hud_config.json";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private Config config = new Config();

    private ConfigManager() {}

    public Config get() {
        return config;
    }

    public void load() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(FILENAME);
        try {
            if (Files.exists(path)) {
                String txt = Files.readString(path);
                config = gson.fromJson(txt, Config.class);
            } else {
                save();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void save() {
        Path path = FabricLoader.getInstance().getConfigDir().resolve(FILENAME);
        try {
            Files.createDirectories(path.getParent());
            String json = gson.toJson(config);
            Files.writeString(path, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
