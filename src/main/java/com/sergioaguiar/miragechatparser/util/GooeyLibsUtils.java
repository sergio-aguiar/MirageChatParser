package com.sergioaguiar.miragechatparser.util;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.gui.templates.PartyCheckGooeyTemplate;
import com.sergioaguiar.miragechatparser.parser.PlaceholderResolver;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.helpers.TemplateHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
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
            .template(new PartyCheckGooeyTemplate(TemplateHelper.slotsOf(PartyCheckGooeyTemplate.TEMPLATE_ROWS * PartyCheckGooeyTemplate.TEMPLATE_COLUMNS), player))
            .title(ChatStrings.getPartyCheckTitleString())
            .build();
    }

    public static GooeyButton getPokemonButton(ServerPlayerEntity player, Pokemon pokemon, boolean closed)
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

    public static GooeyButton getPartyShoutButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.WHITE_CONCRETE))
            .build();
    }

    public static GooeyButton getBlackStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.BLACK_STAINED_GLASS_PANE))
            .build();
    }

    public static GooeyButton getRedStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.RED_STAINED_GLASS_PANE))
            .build();
    }

    public static GooeyButton getWhiteStainedGlassPaneButton()
    {
        return GooeyButton.builder()
            .display(new ItemStack(Items.WHITE_STAINED_GLASS_PANE))
            .build();
    }
}
