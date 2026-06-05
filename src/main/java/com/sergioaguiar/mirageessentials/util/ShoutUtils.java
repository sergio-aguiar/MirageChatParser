package com.sergioaguiar.mirageessentials.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.parser.PlaceholderResolver;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

public class ShoutUtils
{
    public static void doDebugShout(ServerPlayerEntity player, Pokemon pokemon)
    {
        if (pokemon == null)
        {
            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

            messageBuilder.append
            (
                "DebugShout » ",
                ChatColors.getCommandPrefixColor()
            );

            messageBuilder.append
            (
                "You can not shout an empty slot!",
                ChatColors.getCommandValueColor()
            );

            player.sendMessage(messageBuilder.getText(), false);

            return;
        }

        TextUtils.CustomTextBuilder lineBuilder = new TextUtils.CustomTextBuilder();

        lineBuilder.append
        (
            "DebugShout » ",
            ChatColors.getCommandPrefixColor()
        );

        lineBuilder.append
        (
            "Only you can see this message!",
            ChatColors.getCommandValueColor()
        );

        Set<String> aspects = pokemon.getAspects();
        Set<String> aspectsForced = pokemon.getForcedAspects();
        List<SpeciesFeature> speciesFeatures = pokemon.getFeatures();
        NbtCompound persistentData = pokemon.getPersistentData();
        Set<String> persistentDataKeys = pokemon.getPersistentData().getKeys();

        lineBuilder.append
        (
            "\n\n",
            ChatColors.getCommandValueColor()
        );

        lineBuilder.append
        (
            "Aspect List (%d total):".formatted(aspects.size()),
            ChatColors.getCommandPrefixColor()
        );

        for (String aspect : aspects)
        {
            lineBuilder.append
            (
                "\n",
                ChatColors.getCommandValueColor()
            );

            lineBuilder.append
            (
                "- ",
                ChatColors.getCommandPrefixColor()
            );

            lineBuilder.append
            (
                "%s".formatted(aspect),
                ChatColors.getCommandValueColor()
            );
        }

        lineBuilder.append
        (
            "\n\n",
            ChatColors.getCommandValueColor()
        );

        lineBuilder.append
        (
            "Forced Aspect List (%d total):".formatted(aspectsForced.size()),
            ChatColors.getCommandPrefixColor()
        );

        for (String aspectForced : aspectsForced)
        {
            lineBuilder.append
            (
                "\n",
                ChatColors.getCommandValueColor()
            );

            lineBuilder.append
            (
                "- ",
                ChatColors.getCommandPrefixColor()
            );

            lineBuilder.append
            (
                "%s".formatted(aspectForced),
                ChatColors.getCommandValueColor()
            );
        }

        lineBuilder.append
        (
            "\n\n",
            ChatColors.getCommandValueColor()
        );

        lineBuilder.append
        (
            "Species Features List (%d total):".formatted(speciesFeatures.size()),
            ChatColors.getCommandPrefixColor()
        );

        for (SpeciesFeature speciesFeature : speciesFeatures)
        {
            lineBuilder.append
            (
                "\n",
                ChatColors.getCommandValueColor()
            );

            lineBuilder.append
            (
                "- %s: ".formatted(speciesFeature.getName()),
                ChatColors.getCommandPrefixColor()
            );

            lineBuilder.append
            (
                "%s".formatted(speciesFeature.toString()),
                ChatColors.getCommandValueColor()
            );
        }

        lineBuilder.append
        (
            "\n\n",
            ChatColors.getCommandValueColor()
        );

        lineBuilder.append
        (
            "Persistent Data List (%d total):".formatted(persistentDataKeys.size()),
            ChatColors.getCommandPrefixColor()
        );

        for (String persistentDataKey : persistentDataKeys)
        {
            lineBuilder.append
            (
                "\n",
                ChatColors.getCommandValueColor()
            );

            lineBuilder.append
            (
                "- %s: ".formatted(persistentDataKey),
                ChatColors.getCommandPrefixColor()
            );

            lineBuilder.append
            (
                "%s".formatted(persistentData.get(persistentDataKey).asString()),
                ChatColors.getCommandValueColor()
            );
        }

        player.sendMessage(lineBuilder.getText());
    }

    public static void doPCShout(ServerPlayerEntity player, Pokemon pokemon, int box, int slot, boolean closed, boolean self)
    {
        if (pokemon == null)
        {
            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

            messageBuilder.append
            (
                "PCShout » ",
                ChatColors.getCommandPrefixColor()
            );

            messageBuilder.append
            (
                "You can not shout an empty slot!",
                ChatColors.getCommandValueColor()
            );

            player.sendMessage(messageBuilder.getText(), false);

            return;
        }

        Text message = PlaceholderResolver.getPCPokemonName(player, box, slot, closed);

        TextUtils.CustomTextBuilder shoutBuilder = new TextUtils.CustomTextBuilder();

        shoutBuilder.append
        (
            "PCShout » ",
            ChatColors.getCommandPrefixColor()
        );

        shoutBuilder.append
        (
            "Player ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append
        (
            player.getDisplayName().getString(),
            ChatColors.getCommandPlayerColor()
        );

        shoutBuilder.append
        (
            " shouted: ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append(message);

        if (self) player.sendMessage(shoutBuilder.getText(), false);
        else player.getServer().getPlayerManager().broadcast(shoutBuilder.getText(), false);
    }

    public static void doPartyShout(ServerPlayerEntity player, Pokemon pokemon, boolean closed, boolean self)
    {
        if (pokemon == null)
        {
            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

            messageBuilder.append
            (
                "PartyShout » ",
                ChatColors.getCommandPrefixColor()
            );

            messageBuilder.append
            (
                "You can not shout an empty slot!",
                ChatColors.getCommandValueColor()
            );

            player.sendMessage(messageBuilder.getText(), false);

            return;
        }

        TextUtils.CustomTextBuilder shoutBuilder = new TextUtils.CustomTextBuilder();

        shoutBuilder.append
        (
            "PartyShout » ",
            ChatColors.getCommandPrefixColor()
        );

        shoutBuilder.append
        (
            "Player ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append
        (
            player.getDisplayName().getString(),
            ChatColors.getCommandPlayerColor()
        );

        shoutBuilder.append
        (
            " shouted: ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append(PlaceholderResolver.buildPokemonText(pokemon, closed));

        if (self) player.sendMessage(shoutBuilder.getText(), false);
        else player.getServer().getPlayerManager().broadcast(shoutBuilder.getText(), false);
    }

    public static void doPartyShoutAll(ServerPlayerEntity player, boolean closed, boolean self)
    {
        ArrayList<Text> pokemonInfos = PlaceholderResolver.getAllPartyPokemon(player, closed);

        if (pokemonInfos.isEmpty())
        {
            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

            messageBuilder.append
            (
                "PartyShoutAll » ",
                ChatColors.getCommandPrefixColor()
            );

            messageBuilder.append
            (
                "You can not shout an empty party!",
                ChatColors.getCommandValueColor()
            );

            player.sendMessage(messageBuilder.getText(), false);
            return;
        }
        
        TextUtils.CustomTextBuilder shoutBuilder = new TextUtils.CustomTextBuilder();

        shoutBuilder.append
        (
            "PartyShoutAll » ",
            ChatColors.getCommandPrefixColor()
        );

        shoutBuilder.append
        (
            "Player ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append
        (
            player.getDisplayName().getString(),
            ChatColors.getCommandPlayerColor()
        );

        shoutBuilder.append
        (
            " shouted: ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append
        (
            "\n",
            ChatColors.getCommandValueColor()
        );

        int infoAmount = pokemonInfos.size();
        for (int i = 0; i < infoAmount; i++)
        {
            shoutBuilder.append(pokemonInfos.get(i));
            if (i == 2)
            {
                shoutBuilder.append
                (
                    "\n",
                    ChatColors.getCommandValueColor()
                );
            }
            else if (i != infoAmount - 1)
            {
                shoutBuilder.append
                (
                    " ",
                    ChatColors.getCommandValueColor()
                );
            }
        }

        if (self) player.sendMessage(shoutBuilder.getText(), false);
        else player.getServer().getPlayerManager().broadcast(shoutBuilder.getText(), false);
    }

    public static void doItemShout(ServerPlayerEntity player, int itemIndex)
    {
        ItemStack stack = player.getInventory().getStack(itemIndex - 1);
        doItemShout(player, stack);
    }

    public static void doItemShout(ServerPlayerEntity player, ItemStack stack)
    {
        if (stack == null || stack.isEmpty())
        {
            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

            messageBuilder.append
            (
                "ItemShout » ",
                ChatColors.getCommandPrefixColor()
            );

            messageBuilder.append
            (
                "You can not shout an empty item slot!",
                ChatColors.getCommandValueColor()
            );

            player.sendMessage(messageBuilder.getText(), false);
            return;
        }

        TextUtils.CustomTextBuilder shoutBuilder = new TextUtils.CustomTextBuilder();

        shoutBuilder.append
        (
            "ItemShout » ",
            ChatColors.getCommandPrefixColor()
        );

        shoutBuilder.append
        (
            "Player ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append
        (
            player.getDisplayName().getString(),
            ChatColors.getCommandPlayerColor()
        );

        shoutBuilder.append
        (
            " shouted: ",
            ChatColors.getCommandValueColor()
        );

        shoutBuilder.append(TextUtils.getItemText(stack));
        
        player.getServer().getPlayerManager().broadcast(shoutBuilder.getText(), false);
    }
}
