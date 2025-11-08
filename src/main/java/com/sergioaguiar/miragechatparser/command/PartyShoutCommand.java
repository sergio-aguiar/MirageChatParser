package com.sergioaguiar.miragechatparser.command;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
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
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PartyShoutCommand 
{
    public static void register()
    {
        LiteralArgumentBuilder<ServerCommandSource> partyShout = CommandManager.literal("partyshout")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.partyshout"))
            .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 6))
                .executes(PartyShoutCommand::executePartyShout));

        LiteralArgumentBuilder<ServerCommandSource> pokeShout = CommandManager.literal("pokeshout")
            .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.pokeshout"))
            .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 6))
                .executes(PartyShoutCommand::executePartyShout));

        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(partyShout);
            dispatcher.register(pokeShout);
        });
    }

    private static int executePartyShout(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute partyshout/pokeshout: %s".formatted(e.getMessage()));
            return 1;
        }

        int slot = IntegerArgumentType.getInteger(context, "slot");

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = party.get(slot - 1);

        if (pokemon == null)
        {
            player.sendMessage(Text.literal("PartyShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return 1;
        }

        Text message = PlaceholderResolver.getPartyPokemonName(player, slot);

        player.getServer().getPlayerManager().broadcast
        (
            Text.literal("PartyShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("Player ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                .append(Text.literal(player.getDisplayName().getString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
                .append(Text.literal(" shouted: ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                .append(message),
            false
        );

        return 0;
    }
}
