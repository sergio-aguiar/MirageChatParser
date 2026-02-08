package com.sergioaguiar.miragechatparser.gui.templates;

import java.util.ArrayList;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings.ShoutType;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings.ShoutVisibility;
import com.sergioaguiar.miragechatparser.gui.buttons.OptionListGooeyButton;
import com.sergioaguiar.miragechatparser.util.GooeyLibsUtils;

import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PartyCheckGooeyTemplate extends ChestTemplate
{
    public static final int TEMPLATE_ROWS = 5;
    public static final int TEMPLATE_COLUMNS = 9;

    private static final int BUTTON_ROW = 1;
    private static final int SHOUT_TYPE_BUTTON_COLUMN = 3;
    private static final int SHOUT_VISIBILITY_BUTTON_COLUMN = 4;

    private static final int TOP_POKEMON_ROW = 2;
    private static final int BOTTOM_POKEMON_ROW = 3;
    private static final int LEFT_POKEMON_COLUMN = 3;
    private static final int MIDDLE_POKEMON_COLUMN = 4;
    private static final int RIGHT_POKEMON_COLUMN = 5;

    private ArrayList<OptionListGooeyButton<ShoutType>> shoutTypeButtons;
    private ArrayList<OptionListGooeyButton<ShoutVisibility>> shoutVisibilityButtons;

    private int activeShoutTypeButton;
    private int activeShoutVisibilityButton;

    public PartyCheckGooeyTemplate(@NotNull TemplateSlotDelegate[] slots, ServerPlayerEntity player)
    {
        super(slots);
        configureTemplateStructure(player);
    }

    private void configureTemplateStructure(ServerPlayerEntity player)
    {
        configureWindowFrame();
        configureShoutTypeButton();
        configureShoutVisibilityButton();
        configurePokemonButtons(player);
    }

    private void configureWindowFrame()
    {
        for (int i = 0; i < 9; i++)
        {
            if (i % 2 == 0) set(0, i, GooeyLibsUtils.getBlackStainedGlassPaneButton());
            else set(0, i, GooeyLibsUtils.getRedStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) set(1, i, GooeyLibsUtils.getRedStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) set(1, i, GooeyLibsUtils.getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) set(2, i, GooeyLibsUtils.getBlackStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) set(2, i, GooeyLibsUtils.getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i == 0 || i == 8) set(3, i, GooeyLibsUtils.getRedStainedGlassPaneButton());
            else if (i == 1 || i == 2 || i == 6 || i == 7) set(3, i, GooeyLibsUtils.getWhiteStainedGlassPaneButton());
        }

        for (int i = 0; i < 9; i++)
        {
            if (i % 2 == 0) set(4, i, GooeyLibsUtils.getBlackStainedGlassPaneButton());
            else set(4, i, GooeyLibsUtils.getRedStainedGlassPaneButton());
        }
    }

    private void configureShoutTypeButton()
    {
        shoutTypeButtons = new ArrayList<>();
        activeShoutTypeButton = 0;

        shoutTypeButtons.add
        (
            new OptionListGooeyButton<ShoutType>
            (
                new ItemStack(Items.WHITE_CONCRETE),
                (action) ->
                {
                    incrementShoutTypeButton();
                },
                ShoutType.GENERAL
            )
        );

        shoutTypeButtons.add
        (
            new OptionListGooeyButton<ShoutType>
            (
                new ItemStack(Items.ORANGE_CONCRETE),
                (action) ->
                {
                    incrementShoutTypeButton();
                },
                ShoutType.RIDE
            )
        );

        shoutTypeButtons.add
        (
            new OptionListGooeyButton<ShoutType>
            (
                new ItemStack(Items.PINK_CONCRETE),
                (action) ->
                {
                    incrementShoutTypeButton();
                },
                ShoutType.RIBBON
            )
        );

        set(BUTTON_ROW, SHOUT_TYPE_BUTTON_COLUMN, shoutTypeButtons.get(activeShoutTypeButton));
    }

    private void configureShoutVisibilityButton()
    {
        shoutVisibilityButtons = new ArrayList<>();
        activeShoutVisibilityButton = 0;

        shoutVisibilityButtons.add
        (
            new OptionListGooeyButton<ShoutVisibility>
            (
                new ItemStack(Items.GREEN_CONCRETE),
                (action) ->
                {
                    incrementShoutVisibilityButton();
                },
                ShoutVisibility.OPEN
            )
        );

        shoutVisibilityButtons.add
        (
            new OptionListGooeyButton<ShoutVisibility>
            (
                new ItemStack(Items.RED_CONCRETE),
                (action) ->
                {
                    incrementShoutVisibilityButton();
                },
                ShoutVisibility.CLOSED
            )
        );

        shoutVisibilityButtons.add
        (
            new OptionListGooeyButton<ShoutVisibility>
            (
                new ItemStack(Items.BLUE_CONCRETE),
                (action) ->
                {
                    incrementShoutVisibilityButton();
                },
                ShoutVisibility.SELF
            )
        );

        set(BUTTON_ROW, SHOUT_VISIBILITY_BUTTON_COLUMN, shoutVisibilityButtons.get(activeShoutVisibilityButton));
    }

    private void incrementShoutTypeButton()
    {
        set(BUTTON_ROW, SHOUT_TYPE_BUTTON_COLUMN, shoutTypeButtons.get((++activeShoutTypeButton) % shoutTypeButtons.size()));
    }

    private void incrementShoutVisibilityButton()
    {
        set(BUTTON_ROW, SHOUT_VISIBILITY_BUTTON_COLUMN, shoutVisibilityButtons.get((++activeShoutVisibilityButton) % shoutVisibilityButtons.size()));
    }

    private void configurePokemonButtons(ServerPlayerEntity player)
    {
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);

        set(TOP_POKEMON_ROW, LEFT_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(0), false));
        set(TOP_POKEMON_ROW, MIDDLE_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(1), false));
        set(TOP_POKEMON_ROW, RIGHT_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(2), false));
        set(BOTTOM_POKEMON_ROW, LEFT_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(3), false));
        set(BOTTOM_POKEMON_ROW, MIDDLE_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(4), false));
        set(BOTTOM_POKEMON_ROW, RIGHT_POKEMON_COLUMN, GooeyLibsUtils.getPokemonButton(player, party.get(5), false));
    }

    private LoreComponent getShoutTypeButtonLore(int buttonIndex)
    {
        List<Text> loreText = List.of
        (
            Text.literal("Shout Type:")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()).withItalic(false)),
            Text.literal("%s - %s".formatted(shoutTypeButtons.get(0).getButtonName(), shoutTypeButtons.get(0).getButtonDescription()))
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()).withItalic(false).withBold(buttonIndex == 0)),
            Text.literal("%s - %s".formatted(shoutTypeButtons.get(1).getButtonName(), shoutTypeButtons.get(0).getButtonDescription()))
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()).withItalic(false).withBold(buttonIndex == 1)),
            Text.literal("%s - %s".formatted(shoutTypeButtons.get(2).getButtonName(), shoutTypeButtons.get(0).getButtonDescription()))
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()).withItalic(false).withBold(buttonIndex == 2)),
            Text.literal("")
                .setStyle(Style.EMPTY.withItalic(false)),
            Text.literal("Click to cycle through the available options.")
                .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()).withItalic(false))
        );

        return new LoreComponent(loreText);
    }
}
