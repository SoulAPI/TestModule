package com.unfamoussoul.test.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.unfamoussoul.sapi.api.command.DynamicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;

public class TestCommand extends DynamicCommand {

    public TestCommand() {
        super("test");
        register(test());
    }

    private LiteralArgumentBuilder<CommandSourceStack> test() {
        return Commands.literal("test")
                .executes(context -> {
                    CommandSender sender = context.getSource().getSender();
                    sender.sendRichMessage(sender.getName());
                    return 1;
                });
    }
}
