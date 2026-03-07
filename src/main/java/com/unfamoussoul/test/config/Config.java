package com.unfamoussoul.test.config;

import com.unfamoussoul.sapi.api.config.ConfigHandler;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

public class Config {

    public final boolean welcomeMessage;

    public final int webPort;

    public final int webThreadsMin;
    public final int webThreadsMax;
    public final int webThreadsQueued;
    public final int webThreadsIdle;
    public final int webThreadsEvict;

    public Config(@NotNull ConfigHandler configHandler) {
        FileConfiguration cfg = configHandler.getConfig();

        welcomeMessage = cfg.getBoolean("welcome_message");

        webPort = cfg.getInt("web.port");

        webThreadsMin = cfg.getInt("web.threads.min");
        webThreadsMax = cfg.getInt("web.threads.max");
        webThreadsQueued = cfg.getInt("web.threads.queued");
        webThreadsIdle = cfg.getInt("web.threads.idle");
        webThreadsEvict = cfg.getInt("web.threads.evict");
    }
}
