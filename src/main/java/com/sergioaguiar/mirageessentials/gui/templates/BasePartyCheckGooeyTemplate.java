package com.sergioaguiar.mirageessentials.gui.templates;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.mirageessentials.config.chatparser.textures.GUITextures;
import com.sergioaguiar.mirageessentials.gui.buttons.ShoutTypeGooeyButton;
import com.sergioaguiar.mirageessentials.gui.buttons.ShoutVisibilityGooeyButton;
import com.sergioaguiar.mirageessentials.manager.CooldownManager;
import com.sergioaguiar.mirageessentials.util.GooeyLibsUtils;
import com.sergioaguiar.mirageessentials.util.TextUtils;
import com.sergioaguiar.mirageessentials.util.ShoutUtils;

import ca.landonjw.gooeylibs2.api.button.GooeyButton;
import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.server.network.ServerPlayerEntity;

public class BasePartyCheckGooeyTemplate extends ChestTemplate
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

    protected ShoutTypeGooeyButton shoutTypeButton;
    protected ShoutVisibilityGooeyButton shoutVisibilityButton;

    protected BasePartyCheckGooeyTemplate(@NotNull TemplateSlotDelegate[] slots, ServerPlayerEntity player)
    {
        super(slots);
        configureTemplateStructure(player);
    }

    protected void configureTemplateStructure(ServerPlayerEntity player)
    {
        if (ChatSettings.shouldShowPartyCheckGUIFrameBlocks()) configureWindowFrame();
        configureShoutTypeButton(BUTTON_ROW, SHOUT_TYPE_BUTTON_COLUMN);
        configureShoutVisibilityButton(BUTTON_ROW, SHOUT_VISIBILITY_BUTTON_COLUMN);
        configurePokemonButtons(player);
        configurePartyShoutAllButton(player, false, false, BUTTON_ROW, POKESHOUTALL_BUTTON_COLUMN);
    }

    protected void configureWindowFrame()
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

    protected void configureShoutTypeButton(int row, int collumn)
    {
        shoutTypeButton = new ShoutTypeGooeyButton();

        set
        (
            row, 
            collumn, 
            shoutTypeButton
        );
    }

    protected void configureShoutVisibilityButton(int row, int collumn)
    {
        shoutVisibilityButton = new ShoutVisibilityGooeyButton();

        set
        (
            row,
            collumn,
            shoutVisibilityButton
        );
    }

    protected void configurePartyShoutAllButton(ServerPlayerEntity player, boolean closed, boolean self, int row, int collumn)
    {
        TextUtils.CustomTextBuilder titleBuilder = new TextUtils.CustomTextBuilder();
        titleBuilder.append
        (
            ChatStrings.getPartyCheckPokeShoutAllTitleString(),
            ChatColors.getPartyCheckButtonTitleColor()
        );

        TextUtils.CustomTextBuilder footerBuilder = new TextUtils.CustomTextBuilder();
        footerBuilder.append
        (
            ChatStrings.getPartyCheckPartyShoutAllFooterString(),
            ChatColors.getPartyCheckFooterColorColor()
        );

        set
        (
            row,
            collumn,
            GooeyButton.builder()
                .display(GooeyLibsUtils.getCustomModelDataItemStack(GUITextures.getPartyShoutAllItem(), GUITextures.getPartyShoutAllCustomModelData()))
                .with
                (
                    DataComponentTypes.CUSTOM_NAME,
                    titleBuilder.getText()
                )
                .with
                (
                    DataComponentTypes.LORE,
                    new LoreComponent(List.of(footerBuilder.getText()))
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
                        if (shoutTypeButton.isRideShout()) 
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
                        else if (shoutTypeButton.isRibbonShout()) 
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
                        else ShoutUtils.doPartyShoutAll(player, shoutVisibilityButton.isClosedShout(), shoutVisibilityButton.isOpenShout());

                        CooldownManager.setUsed(player);
                    }                    
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
