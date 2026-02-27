package com.unfamoussoul.test.config;

import com.unfamoussoul.sapi.api.config.ConfigHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Config {

    public final boolean welcomeMessage;

    public Config(@NotNull ConfigHandler configHandler) {
        FileConfiguration cfg = configHandler.getConfig();

        welcomeMessage = cfg.getBoolean("welcome_message");
    }
}
