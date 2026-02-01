package com.sergioaguiar.miragechatparser.command;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.util.GooeyLibsUtils;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import ca.landonjw.gooeylibs2.api.UIManager;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;

public class PartyCheckCommand
{
    public static void register()
    {
        LiteralArgumentBuilder<ServerCommandSource> partyCheck = CommandManager.literal("partycheck")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.partycheck"))
            .executes(PartyCheckCommand::executePartyCheck);

        LiteralArgumentBuilder<ServerCommandSource> pokeCheck = CommandManager.literal("pokecheck")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pokecheck"))
            .executes(PartyCheckCommand::executePartyCheck);

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(partyCheck);
            dispatcher.register(pokeCheck);
        });
    }

    private static int executePartyCheck(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute PartyCheck/PokeCheck: %s".formatted(e.getMessage()));
            return 1;
        }

        UIManager.openUIForcefully(player, GooeyLibsUtils.getPartyCheckPage(player));

        return 0;
    }
}
