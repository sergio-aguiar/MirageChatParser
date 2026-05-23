package com.sergioaguiar.mirageessentials.gui.templates;

import org.jetbrains.annotations.NotNull;

import com.sergioaguiar.mirageessentials.gui.buttons.ShoutTypeGooeyButton;
import com.sergioaguiar.mirageessentials.gui.buttons.ShoutVisibilityGooeyButton;

import ca.landonjw.gooeylibs2.api.template.slot.TemplateSlotDelegate;
import net.minecraft.server.network.ServerPlayerEntity;

public class DefaultPartyCheckGooeyTemplate extends BasePartyCheckGooeyTemplate
{
    public static final int TEMPLATE_ROWS = 5;
    public static final int TEMPLATE_COLUMNS = 9;

    protected ShoutTypeGooeyButton shoutTypeButton;
    protected ShoutVisibilityGooeyButton shoutVisibilityButton;

    public DefaultPartyCheckGooeyTemplate(@NotNull TemplateSlotDelegate[] slots, ServerPlayerEntity player)
    {
        super(slots, player);
        configureTemplateStructure(player);
    }
}
