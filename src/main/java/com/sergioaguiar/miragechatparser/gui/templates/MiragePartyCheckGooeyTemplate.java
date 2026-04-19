package com.sergioaguiar.miragechatparser.gui.templates;

import org.jetbrains.annotations.NotNull;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.sergioaguiar.miragechatparser.util.GooeyLibsUtils;

import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import net.minecraft.server.network.ServerPlayerEntity;

public class MiragePartyCheckGooeyTemplate extends BasePartyCheckGooeyTemplate
{
    public static final int TEMPLATE_ROWS = 5;
    public static final int TEMPLATE_COLUMNS = 9;

    private static final int BUTTON_ROW = 1;
    private static final int SHOUT_TYPE_BUTTON_COLUMN = 3;
    private static final int SHOUT_VISIBILITY_BUTTON_COLUMN = 4;
    private static final int POKESHOUTALL_BUTTON_COLUMN = 5;

    private static final int POKEMON_ROW = 3;

    private static final int MAX_PARTY_POKEMON = 6;

    public MiragePartyCheckGooeyTemplate(@NotNull TemplateSlotDelegate[] slots, ServerPlayerEntity player)
    {
        super(slots, player);
    }

    @Override
    protected void configureTemplateStructure(ServerPlayerEntity player)
    {
        configureShoutTypeButton(BUTTON_ROW, SHOUT_TYPE_BUTTON_COLUMN);
        configureShoutVisibilityButton(BUTTON_ROW, SHOUT_VISIBILITY_BUTTON_COLUMN);
        configurePokemonButtons(player);
        configurePartyShoutAllButton(player, false, false, BUTTON_ROW, POKESHOUTALL_BUTTON_COLUMN);
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
                    POKEMON_ROW,
                    i < 3 ? i + 1 : i + 2,
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
