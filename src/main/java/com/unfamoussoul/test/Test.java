package com.unfamoussoul.test;

import com.unfamoussoul.sapi.SAPI;
import com.unfamoussoul.sapi.module.SAPIModule;
import com.unfamoussoul.test.command.TestCommand;
import com.unfamoussoul.test.config.Config;
import com.unfamoussoul.test.listener.EventListener;

@SuppressWarnings("unused")
public class Test extends SAPIModule {

    private Config config;

    public Test(SAPI plugin) {
        super(plugin);
    }

    @Override
    public void onEnable() {
        setVersion(1);
        setKey("key");

        config = new Config(getConfigHandler("config.yml"));
        loadLocale(plugin.getDefaultLanguage(), "en_US", "ru_RU");

        addCommand(new TestCommand());
        addListener(new EventListener(this));
    }

    @Override
    public void onDisable() {
        plugin.getLogger().warning("test+++");
    }

    public Config getConfig() {
        return config;
    }
}