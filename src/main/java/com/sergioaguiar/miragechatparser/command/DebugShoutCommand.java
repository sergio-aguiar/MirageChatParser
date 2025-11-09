package com.sergioaguiar.miragechatparser.command;

import java.util.List;
import java.util.Set;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.util.LuckPermsUtils;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class DebugShoutCommand 
{
    public static void register()
    {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> 
        {
            dispatcher.register(
                CommandManager.literal("debugshout")
                    .requires(source -> LuckPermsUtils.hasPermission(source, "miragechatparser.commands.admin.debugshout"))
                    .then(CommandManager.argument("slot", IntegerArgumentType.integer(1, 6))
                        .executes(DebugShoutCommand::executeDebugShout))
            );
        });
    }

    private static int executeDebugShout(CommandContext<ServerCommandSource> context) throws CommandSyntaxException
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
            ModLogger.error("Failed to execute debugshout: %s".formatted(e.getMessage()));
            return 1;
        }

        int slot = IntegerArgumentType.getInteger(context, "slot");

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = party.get(slot - 1);

        if (pokemon == null)
        {
            player.sendMessage(Text.literal("DebugShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return 1;
        }

        MutableText coloredLine = Text.literal("DebugShout » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
            .append(Text.literal("Only you can see this message!")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));

        Set<String> aspects = pokemon.getAspects();
        Set<String> aspectsForced = pokemon.getForcedAspects();
        List<SpeciesFeature> speciesFeatures = pokemon.getFeatures();
        NbtCompound persistentData = pokemon.getPersistentData();
        Set<String> persistentDataKeys = pokemon.getPersistentData().getKeys();

        coloredLine = coloredLine
            .append(Text.literal("\n\n"))
            .append(Text.literal("Aspect List (%d total):".formatted(aspects.size())).
                setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        for (String aspect : aspects)
        {
            coloredLine = coloredLine
                .append(Text.literal("\n"))
                .append(Text.literal("- ").
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())))
                .append(Text.literal("%s".formatted(aspect)).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
        }

        coloredLine = coloredLine
            .append(Text.literal("\n\n"))
            .append(Text.literal("Forced Aspect List (%d total):".formatted(aspectsForced.size())).
                setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        for (String aspectForced : aspectsForced)
        {
            coloredLine = coloredLine
                .append(Text.literal("\n"))
                .append(Text.literal("- ").
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())))
                .append(Text.literal("%s".formatted(aspectForced)).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
        }

        coloredLine = coloredLine
            .append(Text.literal("\n\n"))
            .append(Text.literal("Species Features List (%d total):".formatted(speciesFeatures.size())).
                setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        for (SpeciesFeature speciesFeature : speciesFeatures)
        {
            coloredLine = coloredLine
                .append(Text.literal("\n"))
                .append(Text.literal("- %s: ".formatted(speciesFeature.getName())).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())))
                .append(Text.literal("%s".formatted(speciesFeature.toString())).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
        }

        coloredLine = coloredLine
            .append(Text.literal("\n\n"))
            .append(Text.literal("Persistent Data List (%d total):".formatted(persistentDataKeys.size())).
                setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())));

        for (String persistentDataKey : persistentDataKeys)
        {
            coloredLine = coloredLine
                .append(Text.literal("\n"))
                .append(Text.literal("- %s: ".formatted(persistentDataKey)).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())))
                .append(Text.literal("%s".formatted(persistentData.get(persistentDataKey).asString())).
                    setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())));
        }

        player.sendMessage(coloredLine);
        return 0;
    }
}
