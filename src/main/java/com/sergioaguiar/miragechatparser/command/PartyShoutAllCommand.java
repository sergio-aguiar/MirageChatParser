package com.sergioaguiar.miragechatparser.command;

import java.util.ArrayList;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PartyShoutAllCommand 
{
    public static void register()
    {
        LiteralArgumentBuilder<ServerCommandSource> partyShoutAll = CommandManager.literal("partyshoutall")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.partyshoutall"))
            .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false))
            .then(CommandManager.literal("closed")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, true))
            );

        LiteralArgumentBuilder<ServerCommandSource> pokeShoutAll = CommandManager.literal("pokeshoutall")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pokeshoutall"))
            .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, false))
            .then(CommandManager.literal("closed")
                .executes(context -> PartyShoutAllCommand.executePartyShoutAll(context, true))
            );

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(partyShoutAll);
            dispatcher.register(pokeShoutAll);
        });
    }

    private static int executePartyShoutAll(CommandContext<ServerCommandSource> context, boolean isClosedSheet) throws CommandSyntaxException
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

        ArrayList<Text> pokemonInfos = PlaceholderResolver.getAllPartyPokemon(player, isClosedSheet);

        if (pokemonInfos.isEmpty())
        {
            player.sendMessage(Text.literal("PartyShoutAll » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty party!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return 1;
        }

        MutableText coloredLine = Text.literal("PartyShoutAll » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
            .append(Text.literal(" shouted: ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal("\n"));

        int infoAmount = pokemonInfos.size();
        for (int i = 0; i < infoAmount; i++)
        {
            coloredLine = coloredLine.append(pokemonInfos.get(i));
            if (i == 2) coloredLine = coloredLine.append(Text.literal("\n"));
            else if (i != infoAmount - 1) coloredLine = coloredLine.append(Text.literal(" "));
        }

        player.getServer().getPlayerManager().broadcast(coloredLine, false);
        return 0;
    }
}
