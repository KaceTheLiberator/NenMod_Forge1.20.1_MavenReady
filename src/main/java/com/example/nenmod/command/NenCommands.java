package com.example.nenmod.command;

import com.mojang.brigadier.CommandDispatcher;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import com.example.nenmod.ability.Ability;
import com.example.nenmod.ability.AbilityLoader;
import com.example.nenmod.net.NenNetwork;
import com.example.nenmod.net.TryCraftHatsuC2S;

public class NenCommands {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("nen")
            .then(Commands.literal("abilities").executes(ctx -> {
                int i = 0;
                for (Ability a : AbilityLoader.all()) {
                    ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal(
                        a.id + " — " + a.name + " (" + a.category + ")"
                    ), false);
                    i++;
                }
                return i;
            }))
            .then(Commands.literal("craft").then(Commands.argument("id", net.minecraft.commands.arguments.StringArgumentType.string())
                .executes(ctx -> {
                    String id = net.minecraft.commands.arguments.StringArgumentType.getString(ctx, "id");
                    NenNetwork.CHANNEL.sendToServer(new TryCraftHatsuC2S(id));
                    ctx.getSource().sendSuccess(() -> net.minecraft.network.chat.Component.literal("Attempt crafting: " + id), false);
                    return 1;
                })))
        );
    }
}
