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

public class ShoutVisibilityGooeyButton extends ButtonBase
{
    private enum ShoutVisibility
    {
        OPEN
        (
            ChatStrings::getOpenShoutVisibilityNameString,
            ChatStrings::getOpenShoutVisibilityDescriptionString,
            ShoutVisibilityGooeyButton::getOpenShoutItemStack
        ),
        CLOSED
        (
            ChatStrings::getClosedShoutVisibilityNameString,
            ChatStrings::getClosedShoutVisibilityDescriptionString,
            ShoutVisibilityGooeyButton::getClosedShoutItemStack
        ),
        SELF
        (
            ChatStrings::getSelfShoutVisibilityNameString,
            ChatStrings::getSelfShoutVisibilityDescriptionString,
            ShoutVisibilityGooeyButton::getSelfShoutItemStack
        );

        private final Supplier<String> nameSupplier;
        private final Supplier<String> descriptionSupplier;
        private final Supplier<ItemStack> itemStackSupplier;

        private ShoutVisibility(Supplier<String> nameSupplier, Supplier<String> descriptionSupplier, Supplier<ItemStack> itemStackSupplier)
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

    public ShoutVisibilityGooeyButton()
    {
        super(ShoutVisibility.OPEN.getItemStack());
        currentSelection = 0;
    }

    @Override
    public void onClick(@NotNull ButtonAction action)
    {
        incrementShoutVisibilityButton();
    }

    public String getButtonName()
    {
        return ShoutVisibility.values()[currentSelection].getName();
    }

    public String getButtonDescription()
    {
        return ShoutVisibility.values()[currentSelection].getDescription();
    }

    private void incrementShoutVisibilityButton()
    {
        currentSelection = (++currentSelection) % ShoutVisibility.values().length;
        setDisplay(ShoutVisibility.values()[currentSelection].getItemStack());
    }

    private static LoreComponent getShoutVisibilityButtonLore(int buttonIndex)
    {
        List<Text> loreText = List.of
        (
            getButtonLoreLine(ShoutVisibility.OPEN.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutVisibility.CLOSED.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutVisibility.SELF.ordinal(), buttonIndex),
            Text.literal("")
                .setStyle(Style.EMPTY.withItalic(false)),
            Text.literal(ChatStrings.getPartyCheckFooterString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckFooterColorColor()).withItalic(false))
        );

        return new LoreComponent(loreText);
    }

    private static Text getButtonLoreLine(int buttonIndex, int lineIndex)
    {
        return Text.literal(ShoutVisibility.values()[buttonIndex].getName())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionNameColor()).withItalic(false).withBold(buttonIndex == lineIndex))
            .append(Text.literal(ChatStrings.getPartyCheckSplitterString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionSplitterColor()).withItalic(false).withBold(buttonIndex == lineIndex)))
            .append(Text.literal(ShoutVisibility.values()[buttonIndex].getDescription())
                .setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckOptionDescriptionColor()).withItalic(false).withBold(buttonIndex == lineIndex)));
    }

    private static ItemStack getOpenShoutItemStack()
    {
        return getShoutItemStack(ShoutVisibility.OPEN);
    }

    private static ItemStack getClosedShoutItemStack()
    {
        return getShoutItemStack(ShoutVisibility.CLOSED);
    }

    private static ItemStack getSelfShoutItemStack()
    {
        return getShoutItemStack(ShoutVisibility.SELF);
    }

    private static ItemStack getShoutItemStack(ShoutVisibility type)
    {
        ItemStack stack = new ItemStack
        (
            (type == ShoutVisibility.OPEN) 
                ? Items.GREEN_CONCRETE 
                : (type == ShoutVisibility.CLOSED) 
                    ? Items.RED_CONCRETE 
                    : (type == ShoutVisibility.SELF) 
                        ? Items.BLUE_CONCRETE 
                        : Items.GRAY_CONCRETE
        );

        stack.set(DataComponentTypes.CUSTOM_NAME, Text.literal(ChatStrings.getPartyCheckShoutVisibilityTitleString()).setStyle(Style.EMPTY.withColor(ChatColors.getPartyCheckButtonTitleColor()).withItalic(false)));
        stack.set(DataComponentTypes.LORE, getShoutVisibilityButtonLore(type.ordinal()));

        return stack;
    }

    public boolean isOpenShout()
    {
        return currentSelection == ShoutVisibility.OPEN.ordinal();
    }

    public boolean isClosedShout()
    {
        return currentSelection == ShoutVisibility.CLOSED.ordinal();
    }

    public boolean isSelfShout()
    {
        return currentSelection == ShoutVisibility.SELF.ordinal();
    }
}
