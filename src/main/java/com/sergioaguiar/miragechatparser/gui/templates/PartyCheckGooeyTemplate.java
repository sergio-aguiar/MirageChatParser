package com.sergioaguiar.miragechatparser.gui.templates;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.gui.buttons.ShoutTypeGooeyButton;
import com.sergioaguiar.miragechatparser.gui.buttons.ShoutVisibilityGooeyButton;
import com.sergioaguiar.miragechatparser.util.GooeyLibsUtils;
import com.sergioaguiar.miragechatparser.util.ShoutUtils;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PartyCheckGooeyTemplate extends ChestTemplate
{
    public static final int TEMPLATE_ROWS = 5;
    public static final int TEMPLATE_COLUMNS = 9;

    private static final int BUTTON_ROW = 1;
    private static final int SHOUT_TYPE_BUTTON_COLUMN = 3;
    private static final int SHOUT_VISIBILITY_BUTTON_COLUMN = 4;
    private static final int POKESHOUTALL_BUTTON_COLUMN = 5;

    private static final int TOP_POKEMON_ROW = 2;
    private static final int BOTTOM_POKEMON_ROW = 3;
    private static final int LEFT_POKEMON_COLUMN = 3;
    private static final int MIDDLE_POKEMON_COLUMN = 4;
    private static final int RIGHT_POKEMON_COLUMN = 5;

    private static final int MAX_PARTY_POKEMON = 6;
    private static final int POKEMON_PER_ROW = 3;

    private ShoutTypeGooeyButton shoutTypeButton;
    private ShoutVisibilityGooeyButton shoutVisibilityButton;

    public PartyCheckGooeyTemplate(@NotNull TemplateSlotDelegate[] slots, ServerPlayerEntity player)
    {
        super(slots);
        configureTemplateStructure(player);
    }

    private void configureTemplateStructure(ServerPlayerEntity player)
    {
        if (ChatSettings.shouldShowPartyCheckGUIFrameBlocks()) configureWindowFrame();
        configureShoutTypeButton();
        configureShoutVisibilityButton();
        configurePokemonButtons(player);
        configurePartyShoutAllButton(player, false, false);
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
        shoutTypeButton = new ShoutTypeGooeyButton();

        set
        (
            BUTTON_ROW, 
            SHOUT_TYPE_BUTTON_COLUMN, 
            shoutTypeButton
        );
    }

    private void configureShoutVisibilityButton()
    {
        shoutVisibilityButton = new ShoutVisibilityGooeyButton();

        set
        (
            BUTTON_ROW,
            SHOUT_VISIBILITY_BUTTON_COLUMN,
            shoutVisibilityButton
        );
    }

    private void configurePartyShoutAllButton(ServerPlayerEntity player, boolean closed, boolean self)
    {
        set
        (
            BUTTON_ROW,
            POKESHOUTALL_BUTTON_COLUMN,
            GooeyButton.builder()
                .display(new ItemStack(Items.YELLOW_CONCRETE))
                .with
                (
                    DataComponentTypes.CUSTOM_NAME, 
                    Text.literal(ChatStrings.getPartyCheckPokeShoutAllTitleString()).setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckButtonTitleColor()).withItalic(false))
                )
                .with
                (
                    DataComponentTypes.LORE,
                    new LoreComponent(List.of(Text.literal(ChatStrings.getPartyCheckPartyShoutAllFooterString()).setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckFooterColorColor()).withItalic(false))))
                )
                .onClick((action) ->
                {
                    if (shoutTypeButton.isRideShout()) 
                    {
                        player.sendMessage
                        (
                            Text.literal("RideShout » ")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                                .append(Text.literal("Coming soon!")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                        );
                    }
                    else if (shoutTypeButton.isRibbonShout()) 
                    {
                        player.sendMessage
                        (
                            Text.literal("RibbonShout » ")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandPrefixColor()))
                                .append(Text.literal("Coming soon!")
                                    .setStyle(Style.EMPTY.withColor(ChatColors.getCommandValueColor())))
                        );
                    }
                    else ShoutUtils.doPartyShoutAll(player, shoutVisibilityButton.isClosedShout(), shoutVisibilityButton.isOpenShout());
                    
                })
                .build()
        );
    }

    private void configurePokemonButtons(ServerPlayerEntity player)
    {
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);

        for (int i = 0; i < MAX_PARTY_POKEMON; i++)
        {
            try
            {
                set
                (
                    i < POKEMON_PER_ROW ? TOP_POKEMON_ROW : BOTTOM_POKEMON_ROW,
                    i % POKEMON_PER_ROW == 0 ? LEFT_POKEMON_COLUMN : i % POKEMON_PER_ROW == 1 ? MIDDLE_POKEMON_COLUMN : RIGHT_POKEMON_COLUMN,
                    GooeyLibsUtils.getPokemonButton(player, party.get(i), shoutTypeButton::isRideShout, shoutTypeButton::isRibbonShout, shoutVisibilityButton::isClosedShout, shoutVisibilityButton::isOpenShout)
                );
            }
            catch (GooeyLibsUtils.EmptyPartySlotException e)
            {
                continue;
            }
        }
    }
}
