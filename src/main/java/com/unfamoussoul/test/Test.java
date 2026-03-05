package com.unfamoussoul.test;

import com.unfamoussoul.sapi.SAPI;
import com.unfamoussoul.sapi.api.serialize.Persistent;
import com.unfamoussoul.sapi.module.SAPIModule;
import com.unfamoussoul.test.command.RememberCommand;
import com.unfamoussoul.test.command.RemindCommand;
import com.unfamoussoul.test.command.TestCommand;
import com.unfamoussoul.test.config.Config;
import com.unfamoussoul.test.listener.EventListener;

import java.util.*;

@SuppressWarnings("unused")
public class Test extends SAPIModule {

    private Config config;

    @Persistent(value = "player_phrases")
    private final Map<UUID, List<String>> playerPhrases = new HashMap<>();

    public Test(SAPI plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        config = new Config(getConfigHandler("config.yml"));
        loadLocale(getDefaultLanguage(), "en_US", "ru_RU");

        initPersistentFields();

        addCommand(new TestCommand());
        addCommand(new RememberCommand(this));
        addCommand(new RemindCommand(this));
        addListener(new EventListener(this));
    }

    @Override
    public void onDisable() {
        getLogger().warning("test+++");
    }

    public void addPhrase(UUID playerId, String phrase) {
        playerPhrases.computeIfAbsent(playerId, k -> new ArrayList<>()).add(phrase);
    }

    public List<String> getPhrases(UUID playerId) {
        return playerPhrases.getOrDefault(playerId, Collections.emptyList());
    }

    public Config getConfig() {
        return config;
    }
}