package com.sergioaguiar.miragechatparser.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class GooeyLibsUtils
{
    public static GooeyPage getPartyCheckPage(ServerPlayerEntity player)
    {
        return GooeyPage.builder()
            .template(getPartyCheckTemplate(player))
            .title("PartyCheck")
            .build();
    }

    private static ChestTemplate getPartyCheckTemplate(ServerPlayerEntity player)
    {
        ca.landonjw.gooeylibs2.api.template.types.ChestTemplate.Builder templateBuilder = ChestTemplate.builder(5);

        for (int i = 0; i < 9; i++)
        {
            if (i % 2 == 0) templateBuilder.set(0, i, getBlackStainedGlassPaneButton());
            else templateBuilder.set(0, i, getRedStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) templateBuilder.set(1, i, getRedStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) templateBuilder.set(1, i, getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) templateBuilder.set(2, i, getBlackStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) templateBuilder.set(2, i, getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) templateBuilder.set(3, i, getRedStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) templateBuilder.set(3, i, getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i % 2 == 0) templateBuilder.set(4, i, getBlackStainedGlassPaneButton());
            else templateBuilder.set(4, i, getRedStainedGlassPaneButton());
        }

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);

        templateBuilder.set(1, 3, getBlackStainedGlassPaneButton());
        templateBuilder.set(1, 4, getBlackStainedGlassPaneButton());
        templateBuilder.set(1, 5, getBlackStainedGlassPaneButton());

        templateBuilder.set(2, 3, getPokemonButton(player, party.get(0), false));
        templateBuilder.set(2, 4, getPokemonButton(player, party.get(1), false));
        templateBuilder.set(2, 5, getPokemonButton(player, party.get(2), false));
        templateBuilder.set(3, 3, getPokemonButton(player, party.get(3), false));
        templateBuilder.set(3, 4, getPokemonButton(player, party.get(4), false));
        templateBuilder.set(3, 5, getPokemonButton(player, party.get(5), false));

        return templateBuilder.build();
    }

    private static GooeyButton getPokemonButton(ServerPlayerEntity player, Pokemon pokemon, boolean closed)
    {
        ca.landonjw.gooeylibs2.api.button.GooeyButton.Builder pokemonButtonBuilder = GooeyButton.builder();

        if (pokemon != null)
        {
            String nickname = (pokemon.getNickname() == null || pokemon.getNickname().getLiteralString() == null) 
                ? pokemon.getSpecies().getName() : pokemon.getNickname().getLiteralString();
            List<ElementalType> types = StreamSupport.stream(pokemon.getTypes().spliterator(), false).collect(Collectors.toUnmodifiableList());

            pokemonButtonBuilder.display(PokemonItem.from(pokemon))
            .with(DataComponentTypes.ITEM_NAME, PlaceholderResolver.getNicknameText(nickname, types))
            .with(DataComponentTypes.LORE, new LoreComponent(PlaceholderResolver.getPokemonTooltipTextList(pokemon, closed)))
            .onClick((action) ->
            {
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
                        .append(PlaceholderResolver.buildPokemonText(pokemon, closed)),
                    false
                );
            });
        }
        else
        {
            pokemonButtonBuilder.display(new ItemStack(Items.BARRIER));
        }
            
        return pokemonButtonBuilder.build();
    }

    private static GooeyButton getBlackStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.BLACK_STAINED_GLASS_PANE))
            .build();
    }

    private static GooeyButton getRedStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.RED_STAINED_GLASS_PANE))
            .build();
    }

    private static GooeyButton getWhiteStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.WHITE_STAINED_GLASS_PANE))
            .build();
    }
}
