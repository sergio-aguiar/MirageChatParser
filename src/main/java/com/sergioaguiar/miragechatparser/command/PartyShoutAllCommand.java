package com.sergioaguiar.miragechatparser.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;
import com.sergioaguiar.miragechatparser.util.ShoutUtils;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class PartyShoutAllCommand 
{
    public static void register()
    {
        LiteralArgumentBuilder<ServerCommandSource> partyShoutAll = CommandManager.literal("partyshoutall")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.partyshoutall"))
            .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false, false))
            .then(CommandManager.literal("closed")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, true, false))
            )
            .then(CommandManager.literal("self")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false, true))
            );

        LiteralArgumentBuilder<ServerCommandSource> pokeShoutAll = CommandManager.literal("pokeshoutall")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pokeshoutall"))
            .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false, false))
            .then(CommandManager.literal("closed")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, true, false))
            )
            .then(CommandManager.literal("self")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false, true))
            );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(partyShoutAll);
            dispatcher.register(pokeShoutAll);
        });
    }

    private static int executePartyShoutAll(CommandContext<ServerCommandSource> context, boolean isClosedSheet, boolean isSelfShout) throws CommandSyntaxException
    {
        ServerCommandSource source = context.getSource();
        if (!source.isExecutedByPlayer())
        {
            ModLogger.info("Only players can run this command.");
            return 1;
        }

        ServerPlayerEntity player;
        try
        {
            player = source.getPlayerOrThrow();
        }
        catch (Exception e)
        {
            ModLogger.error("Failed to execute PartyShoutAll/PokeShoutAll: %s".formatted(e.getMessage()));
            return 1;
        }

        ShoutUtils.doPartyShoutAll(player, isClosedSheet, isSelfShout);
        return 0;
    }
}
