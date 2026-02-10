package com.sergioaguiar.miragechatparser.gui.buttons;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;

import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.ButtonBase;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class ShoutTypeGooeyButton extends ButtonBase
{
    private enum ShoutType
    {
        GENERAL
        (
            ChatStrings::getGeneralShoutTypeNameString,
            ChatStrings::getGeneralShoutTypeDescriptionString,
            ShoutTypeGooeyButton::getGeneralShoutItemStack
        ),
        RIDE
        (
            ChatStrings::getRideShoutTypeNameString,
            ChatStrings::getRideShoutTypeDescriptionString,
            ShoutTypeGooeyButton::getRideShoutItemStack
        ),
        RIBBON
        (
            ChatStrings::getRibbonShoutTypeNameString,
            ChatStrings::getRibbonShoutTypeDescriptionString,
            ShoutTypeGooeyButton::getRibbonShoutItemStack
        );

        private final Supplier<String> nameSupplier;
        private final Supplier<String> descriptionSupplier;
        private final Supplier<ItemStack> itemStackSupplier;

        private ShoutType(Supplier<String> nameSupplier, Supplier<String> descriptionSupplier, Supplier<ItemStack> itemStackSupplier)
        {
            this.nameSupplier = nameSupplier;
            this.descriptionSupplier = descriptionSupplier;
            this.itemStackSupplier = itemStackSupplier;
        }

        public String getName()
        {
            return nameSupplier.get();
        }

        public String getDescription()
        {
            return descriptionSupplier.get();
        }

        public ItemStack getItemStack()
        {
            return itemStackSupplier.get();
        }
    }

    private int currentSelection;

    public ShoutTypeGooeyButton()
    {
        super(ShoutType.GENERAL.getItemStack());
        currentSelection = 0;
    }

    @Override
    public void onClick(@NotNull ButtonAction action)
    {
        incrementShoutTypeButton();
    }

    public String getButtonName()
    {
        return ShoutType.values()[currentSelection].getName();
    }

    public String getButtonDescription()
    {
        return ShoutType.values()[currentSelection].getDescription();
    }

    private void incrementShoutTypeButton()
    {
        currentSelection = (++currentSelection) % ShoutType.values().length;
        setDisplay(ShoutType.values()[currentSelection].getItemStack());
    }

    private static LoreComponent getShoutTypeButtonLore(int buttonIndex)
    {
        List<Text> loreText = List.of
        (
            getButtonLoreLine(ShoutType.GENERAL.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutType.RIDE.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutType.RIBBON.ordinal(), buttonIndex),
            Text.literal("")
                .setStyle(Style.EMPTY.withItalic(false)),
            Text.literal(ChatStrings.getPartyCheckFooterString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckFooterColorColor()).withItalic(false))
        );

        return new LoreComponent(loreText);
    }

    private static Text getButtonLoreLine(int buttonIndex, int lineIndex)
    {
        return Text.literal(ShoutType.values()[buttonIndex].getName())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionNameColor()).withItalic(false).withBold(buttonIndex == lineIndex))
            .append(Text.literal(ChatStrings.getPartyCheckSplitterString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionSplitterColor()).withItalic(false).withBold(buttonIndex == lineIndex)))
            .append(Text.literal(ShoutType.values()[buttonIndex].getDescription())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionDescriptionColor()).withItalic(false).withBold(buttonIndex == lineIndex)));
    }

    private static ItemStack getGeneralShoutItemStack()
    {
        return getShoutItemStack(ShoutType.GENERAL);
    }

    private static ItemStack getRideShoutItemStack()
    {
        return getShoutItemStack(ShoutType.RIDE);
    }

    private static ItemStack getRibbonShoutItemStack()
    {
        return getShoutItemStack(ShoutType.RIBBON);
    }

    private static ItemStack getShoutItemStack(ShoutType type)
    {
        ItemStack stack = new ItemStack
        (
            (type == ShoutType.GENERAL) 
                ? Items.WHITE_CONCRETE 
                : (type == ShoutType.RIDE) 
                    ? Items.ORANGE_CONCRETE 
                    : (type == ShoutType.RIBBON) 
                        ? Items.PINK_CONCRETE 
                        : Items.GRAY_CONCRETE
        );

        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(ChatStrings.getPartyCheckShoutTypeTitleString()).setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckButtonTitleColor()).withItalic(false)));
        stack.set(DataComponentTypes.LORE, getShoutTypeButtonLore(type.ordinal()));

        return stack;
    }

    public boolean isGeneralShout()
    {
        return currentSelection == ShoutType.GENERAL.ordinal();
    }

    public boolean isRideShout()
    {
        return currentSelection == ShoutType.RIDE.ordinal();
    }

    public boolean isRibbonShout()
    {
        return currentSelection == ShoutType.RIBBON.ordinal();
    }
}
