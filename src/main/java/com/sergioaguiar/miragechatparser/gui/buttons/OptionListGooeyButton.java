package com.sergioaguiar.miragechatparser.gui.buttons;

import org.jetbrains.annotations.NotNull;

import com.sergioaguiar.miragechatparser.gui.IShoutOption;

import ca.landonjw.gooeylibs2.api.button.ButtonBase;
import net.minecraft.item.ItemStack;

public class OptionListGooeyButton<T extends IShoutOption> extends ButtonBase
{
    private T listItem;

    protected OptionListGooeyButton(@NotNull ItemStack display, T listItem)
    {
        super(display);
        this.listItem = listItem;
    }

    public String getButtonName()
    {
        return listItem.getName();
    }

    public String getButtonDescription()
    {
        return listItem.getDescription();
    }
}
