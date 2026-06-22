package com.sergioaguiar.mirageessentials.util;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.item.PokemonItem;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings.PartyCheckLayout;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.mirageessentials.gui.templates.DefaultPartyCheckGooeyTemplate;
import com.sergioaguiar.mirageessentials.gui.templates.MiragePartyCheckGooeyTemplate;
import com.sergioaguiar.mirageessentials.manager.CooldownManager;
import com.sergioaguiar.mirageessentials.parser.PlaceholderResolver;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.helpers.TemplateHelper;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.CustomModelDataComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class GooeyLibsUtils
{
    public static class EmptyPartySlotException extends Exception {}

    public static GooeyPage getPartyCheckPage(ServerPlayerEntity player)
    {
        return GooeyPage.builder()
            .template
            (
                ChatSettings.getPartyCheckLayout().equals(PartyCheckLayout.MIRAGE)
                ? new MiragePartyCheckGooeyTemplate(TemplateHelper.slotsOf(MiragePartyCheckGooeyTemplate.TEMPLATE_ROWS * MiragePartyCheckGooeyTemplate.TEMPLATE_COLUMNS), player)
                : new DefaultPartyCheckGooeyTemplate(TemplateHelper.slotsOf(DefaultPartyCheckGooeyTemplate.TEMPLATE_ROWS * DefaultPartyCheckGooeyTemplate.TEMPLATE_COLUMNS), player)
            )
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
                .with
                (
                    DataComponentTypes.LORE,
                    new LoreComponent
                    (
                        NeoDaycareUtils.isEgg(pokemon)
                            ? PlaceholderResolver.getEggTooltipTextList(pokemon)
                            : PlaceholderResolver.getPokemonTooltipTextList(pokemon, closed.get())
                    )
                )
                .onClick((action) ->
                {
                    if (CooldownManager.isOnCooldown(player))
                    {
                        TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

                        messageBuilder.append
                        (
                            "MirageChat » ",
                            ChatColors.getCommandPrefixColor()
                        );

                        messageBuilder.append
                        (
                            "You are still on cooldown for ",
                            ChatColors.getCommandValueColor()
                        );

                        messageBuilder.append
                        (
                            "%.2f".formatted(CooldownManager.getRemainingTicks(player) / 20.0),
                            ChatColors.getCommandPrefixColor()
                        );

                        messageBuilder.append
                        (
                            " seconds.",
                            ChatColors.getCommandValueColor()
                        );

                        player.sendMessage(messageBuilder.getText());
                    }
                    else
                    {
                        if (isRideShout.get())
                        {
                            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

                            messageBuilder.append
                            (
                                "RideShout » ",
                                ChatColors.getCommandPrefixColor()
                            );

                            messageBuilder.append
                            (
                                "Coming soon!",
                                ChatColors.getCommandValueColor()
                            );

                            player.sendMessage(messageBuilder.getText());
                        }
                        else if (isRibbonShout.get())
                        {
                            TextUtils.CustomTextBuilder messageBuilder = new TextUtils.CustomTextBuilder();

                            messageBuilder.append
                            (
                                "RibbonShout » ",
                                ChatColors.getCommandPrefixColor()
                            );

                            messageBuilder.append
                            (
                                "Coming soon!",
                                ChatColors.getCommandValueColor()
                            );

                            player.sendMessage(messageBuilder.getText());
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

    public static GooeyButton getPlayerHeadButton(ServerPlayerEntity player)
    {
        ItemStack head = new ItemStack(Items.PLAYER_HEAD);
        head.set(DataComponentTypes.PROFILE, new ProfileComponent(player.getGameProfile()));

        return GooeyButton.builder()
            .display(head)
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

    public static ItemStack getCustomModelDataItemStack(String itemId, int customModelData)
    {
        ItemStack stack = new ItemStack(Registries.ITEM.get(Identifier.of(itemId)));

        if (customModelData != 0)
        {
            stack.set(DataComponentTypes.CUSTOM_MODEL_DATA, new CustomModelDataComponent(customModelData));
        }

        return stack;
    }
}
