package com.sergioaguiar.miragechatparser.util;

import java.util.ArrayList;

import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ShoutUtils
{
    public static void doPartyShout(ServerPlayerEntity player, Pokemon pokemon, boolean closed, boolean self)
    {
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
