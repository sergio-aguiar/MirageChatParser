package com.sergioaguiar.miragechatparser.util;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.gui.templates.PartyCheckGooeyTemplate;
import com.sergioaguiar.miragechatparser.manager.CooldownManager;
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
    public static class EmptyPartySlotException extends Exception {}

    public static GooeyPage getPartyCheckPage(ServerPlayerEntity player)
    {
        return GooeyPage.builder()
            .template(new PartyCheckGooeyTemplate(TemplateHelper.slotsOf(PartyCheckGooeyTemplate.TEMPLATE_ROWS * PartyCheckGooeyTemplate.TEMPLATE_COLUMNS), player))
            .title(ChatStrings.getPartyCheckTitleString())
            .build();
    }

    public static GooeyButton getPokemonButton(ServerPlayerEntity player, Pokemon pokemon, Supplier<Boolean> isRideShout, Supplier<Boolean> isRibbonShout, Supplier<Boolean> closed, Supplier<Boolean> self) throws EmptyPartySlotException
    {
        ca.landonjw.gooeylibs2.api.button.GooeyButton.Builder pokemonButtonBuilder = GooeyButton.builder();

        if (pokemon != null)
        {
            String nickname = (pokemon.getNickname() == null || pokemon.getNickname().getLiteralString() == null) 
                ? pokemon.getSpecies().getName() : pokemon.getNickname().getLiteralString();
            List<ElementalType> types = StreamSupport.stream(pokemon.getTypes().spliterator(), false).collect(Collectors.toUnmodifiableList());

            pokemonButtonBuilder.display(PokemonItem.from(pokemon))
                .with(DataComponentTypes.ITEM_NAME, PlaceholderResolver.getNicknameText(nickname, types))
                .with(DataComponentTypes.LORE, new LoreComponent(PlaceholderResolver.getPokemonTooltipTextList(pokemon, closed.get())))
                .onClick((action) ->
                {
                    if (CooldownManager.isOnCooldown(player))
                    {
                        player.sendMessage
                        (
                            Text.literal("MirageChat » ")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                                .append(Text.literal("You are still on cooldown for ")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                                .append(Text.literal("%.2f".formatted(CooldownManager.getRemainingTicks(player) / 20.0))
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor())))
                                .append(Text.literal(" seconds.")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                        );
                    }
                    else
                    {
                        if (isRideShout.get())
                        {
                            player.sendMessage
                            (
                                Text.literal("RideShout » ")
                                        .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                                    .append(Text.literal("Coming soon!")
                                        .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                            );
                        }
                        else if (isRibbonShout.get())
                        {
                            player.sendMessage
                            (
                                Text.literal("RibbonShout » ")
                                        .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                                    .append(Text.literal("Coming soon!")
                                        .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                            );
                        }
                        else ShoutUtils.doPartyShout(player, pokemon, closed.get(), self.get());

                        CooldownManager.setUsed(player);
                    }
                });
        }
        else
        {
            throw new EmptyPartySlotException();
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
