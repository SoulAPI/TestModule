package com.unfamoussoul.test.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.unfamoussoul.sapi.api.command.DynamicCommand;
import com.unfamoussoul.sapi.module.ModuleAPI;
import com.unfamoussoul.sapi.module.SAPIModule;
import com.unfamoussoul.test.Test;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;

import java.util.Map;

public class TestCommand extends DynamicCommand {

    private final Test module;

    public TestCommand(Test module) {
        super("test");
        this.module = module;
        register(test());
    }

    private LiteralArgumentBuilder<CommandSourceStack> test() {
        return Commands.literal("test")
                .executes(context -> {
                    if (!(context.getSource().getSender() instanceof Player player)) return 1;

                    SAPIModule textAPI = module.getModule("TextAPI");
                    if (textAPI == null) {
                        Component text = module.getLocale().message(
                                player,
                                "test.command.textapi_not_loaded"
                        );
                        player.sendMessage(text);
                        return 1;
                    }

                    ModuleAPI moduleAPI = textAPI.getAPI();

                    Object builder = moduleAPI.call(
                            "createBuilder",
                            Object.class,
                            Map.of(
                                    "text", player.getName()
                            ));
                    builder = moduleAPI.call(
                            "type", Map.of(
                                    "builder", builder,
                                    "type", "text_display"
                            ));

                    moduleAPI.call(
                            "playPlayer", Map.of(
                                    "builder", builder,
                                    "player", player
                            ));

                    return 1;
                });
    }
}
