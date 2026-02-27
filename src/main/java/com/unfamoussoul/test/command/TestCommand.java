package com.unfamoussoul.test.command;

import com.unfamoussoul.sapi.api.command.DynamicCommand;
import io.papermc.paper.command.brigadier.Commands;
import org.bukkit.command.CommandSender;

public class TestCommand extends DynamicCommand {

    public TestCommand() {
        super(
                "test",
                Commands.literal("test")
                        .executes(context -> {
                            CommandSender sender = context.getSource().getSender();
                            sender.sendRichMessage(sender.getName());
                            return 1;
                        })
        );
    }
}
