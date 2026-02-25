package com.sergioaguiar.miragechatparser.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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

public class PartyShoutCommand 
{
    public static void register()
    {
        LiteralArgumentBuilder<ServerCommandSource> partyShout = CommandManager.literal("partyshout")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.partyshout"))
            .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 6))
                .executes(context -> PartyShoutCommand.executePartyShout(context, false, false))
                .then(CommandManager.literal("closed")
                    .executes(context -> PartyShoutCommand.executePartyShout(context, true, false))
                )
                .then(CommandManager.literal("self")
                    .executes(context -> PartyShoutCommand.executePartyShout(context, false, true))
                )
            );

        LiteralArgumentBuilder<ServerCommandSource> pokeShout = CommandManager.literal("pokeshout")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pokeshout"))
            .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 6))
                .executes(context -> PartyShoutCommand.executePartyShout(context, false, false))
                .then(CommandManager.literal("closed")
                    .executes(context -> PartyShoutCommand.executePartyShout(context, true, false))
                )
                .then(CommandManager.literal("self")
                    .executes(context -> PartyShoutCommand.executePartyShout(context, false, true))
                )
            );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(partyShout);
            dispatcher.register(pokeShout);
        });
    }

    private static int executePartyShout(CommandContext<ServerCommandSource> context, boolean isClosedSheet, boolean isSelfShout) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute PartyShout/PokeShout: %s".formatted(e.getMessage()));
            return 1;
        }

        int slot = IntegerArgumentType.getInteger(context, "slot");

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = party.get(slot - 1);

        ShoutUtils.doPartyShout(player, pokemon, isClosedSheet, isSelfShout);
        return 0;
    }
}
