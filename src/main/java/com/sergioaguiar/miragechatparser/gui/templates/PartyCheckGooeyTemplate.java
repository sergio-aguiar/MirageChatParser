package com.sergioaguiar.miragechatparser.gui.templates;

import org.jetbrains.annotations.NotNull;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.sergioaguiar.miragechatparser.gui.buttons.ShoutTypeGooeyButton;
import com.sergioaguiar.miragechatparser.gui.buttons.ShoutVisibilityGooeyButton;
import com.sergioaguiar.miragechatparser.util.GooeyLibsUtils;

import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import net.minecraft.server.network.ServerPlayerEntity;

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
        set
        (
            BUTTON_ROW, 
            SHOUT_TYPE_BUTTON_COLUMN, 
            new ShoutTypeGooeyButton()
        );
    }

    private void configureShoutVisibilityButton()
    {
        set
        (
            BUTTON_ROW,
            SHOUT_VISIBILITY_BUTTON_COLUMN,
            new ShoutVisibilityGooeyButton()
        );
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
}
