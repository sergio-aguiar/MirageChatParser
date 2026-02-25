package com.sergioaguiar.miragechatparser.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;

import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ShoutUtils
{
    public static void doDebugShout(ServerPlayerEntity player, Pokemon pokemon)
    {
        if (pokemon == null)
        {
            player.sendMessage(Text.literal("DebugShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return;
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
    }

    public static void doPCShout(ServerPlayerEntity player, Pokemon pokemon, int box, int slot, boolean closed, boolean self)
    {
        if (pokemon == null)
        {
            player.sendMessage(Text.literal("PCShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return;
        }

        Text message = PlaceholderResolver.getPCPokemonName(player, box, slot, closed);

        MutableText shoutText = Text.literal("PCShout » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
            .append(Text.literal(" shouted: ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(message);

        if (self) player.sendMessage(shoutText, false);
        else player.getServer().getPlayerManager().broadcast(shoutText, false);
    }

    public static void doPartyShout(ServerPlayerEntity player, Pokemon pokemon, boolean closed, boolean self)
    {
        if (pokemon == null)
        {
            player.sendMessage(Text.literal("PartyShout » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty slot!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return;
        }

        Text shoutText = Text.literal("PartyShout » ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPlayerColor())))
            .append(Text.literal(" shouted: ")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
            .append(PlaceholderResolver.buildPokemonText(pokemon, closed));

        if (self) player.sendMessage(shoutText, false);
        else player.getServer().getPlayerManager().broadcast(shoutText, false);
    }

    public static void doPartyShoutAll(ServerPlayerEntity player, boolean closed, boolean self)
    {
        ArrayList<Text> pokemonInfos = PlaceholderResolver.getAllPartyPokemon(player, closed);

        if (pokemonInfos.isEmpty())
        {
            player.sendMessage(Text.literal("PartyShoutAll » ")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                .append(Text.literal("You can not shout an empty party!")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor()))), 
                false);

            return;
        }
        
        MutableText shoutText = Text.literal("PartyShoutAll » ")
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
            shoutText = shoutText.append(pokemonInfos.get(i));
            if (i == 2) shoutText = shoutText.append(Text.literal("\n"));
            else if (i != infoAmount - 1) shoutText = shoutText.append(Text.literal(" "));
        }

        if (self) player.sendMessage(shoutText, false);
        else player.getServer().getPlayerManager().broadcast(shoutText, false);
    }
}
