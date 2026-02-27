package com.unfamoussoul.test.listener;

import com.unfamoussoul.test.Test;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class EventListener implements Listener {

    private final Test module;

    public EventListener(Test module) {
        this.module = module;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull PlayerJoinEvent event) {

        if (!module.getConfig().welcomeMessage) return;

        Player player = event.getPlayer();
        Component text = module.getLocale().message(
                player,
                "test.welcome",
                Placeholder.unparsed("0", player.getName())
        );
        player.sendMessage(text);
    }
}
