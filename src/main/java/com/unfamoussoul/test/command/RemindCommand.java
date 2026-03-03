package com.unfamoussoul.test.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.unfamoussoul.sapi.api.command.DynamicCommand;
import com.unfamoussoul.test.Test;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.UUID;

public class RemindCommand extends DynamicCommand {

    private final Test module;

    public RemindCommand(Test module) {
        super("remind");
        this.module = module;
        register(remind());
    }

    private LiteralArgumentBuilder<CommandSourceStack> remind() {
        return Commands.literal("remind")
                .executes(context -> {
                    if (!(context.getSource().getSender() instanceof Player player)) return 1;

                    UUID playerId = player.getUniqueId();
                    List<String> phrases = module.getPhrases(playerId);

                    if (phrases.isEmpty()) {
                        player.sendMessage(module.getLocale().message(player, "test.remind.empty"));
                        return 1;
                    }

                    ComponentBuilder<TextComponent, TextComponent.Builder> componentBuilder = Component.text();
                    for (String phrase : phrases) {
                        componentBuilder.append(module.getLocale().message(player, "test.remind.line", Placeholder.parsed("0", phrase)));
                    }

                    player.sendMessage(module.getLocale().message(player, "test.remind.headers", Placeholder.component("0", componentBuilder.build())));
                    return 1;
                });
    }
}