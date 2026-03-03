package com.unfamoussoul.test.command;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.unfamoussoul.sapi.api.command.DynamicCommand;
import com.unfamoussoul.test.Test;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import org.bukkit.entity.Player;

public class RememberCommand extends DynamicCommand {

    private final Test module;

    public RememberCommand(Test module) {
        super("remember");
        this.module = module;
        register(remember());
    }

    private LiteralArgumentBuilder<CommandSourceStack> remember() {
        return Commands.literal("remember")
                .then(Commands.argument("text", StringArgumentType.greedyString())
                        .executes(context -> {
                            if (!(context.getSource().getSender() instanceof Player player)) return 1;

                            String text = StringArgumentType.getString(context, "text");
                            module.addPhrase(player.getUniqueId(), text);
                            player.sendMessage(module.getLocale().message(player, "test.remember.success", Placeholder.unparsed("0", String.valueOf(text.length()))));

                            return 1;
                        }));
    }
}