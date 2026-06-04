package com.sergioaguiar.mirageessentials.gui.buttons;

import java.util.List;
import java.util.function.Supplier;

import org.jetbrains.annotations.NotNull;

import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.mirageessentials.config.chatparser.textures.GUITextures;
import com.sergioaguiar.mirageessentials.util.GooeyLibsUtils;
import com.sergioaguiar.mirageessentials.util.TextUtils;

import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.ButtonBase;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.item.ItemStack;
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
        TextUtils.CustomTextBuilder emptyTextBuilder = new TextUtils.CustomTextBuilder();

        emptyTextBuilder.append
        (
            "",
            ChatColors.getPartyCheckOptionNameColor()
        );

        List<Text> loreText = List.of
        (
            getButtonLoreLine(ShoutVisibility.OPEN.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutVisibility.CLOSED.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutVisibility.SELF.ordinal(), buttonIndex),
            emptyTextBuilder.getText(),
            buildPartyCheckFooterText()
        );

        return new LoreComponent(loreText);
    }

    private static Text buildPartyCheckFooterText()
    {
        TextUtils.CustomTextBuilder textBuilder = new TextUtils.CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getPartyCheckFooterString(),
            ChatColors.getPartyCheckFooterColorColor()
        );

        return textBuilder.getText();
    }

    private static Text getButtonLoreLine(int buttonIndex, int lineIndex)
    {
        TextUtils.CustomTextBuilder textBuilder = new TextUtils.CustomTextBuilder();

        textBuilder.append
        (
            ShoutVisibility.values()[buttonIndex].getName(),
            ChatColors.getPartyCheckOptionNameColor(),
            buttonIndex == lineIndex,
            false
        );

        textBuilder.append
        (
            ChatStrings.getPartyCheckSplitterString(),
            ChatColors.getPartyCheckOptionSplitterColor(),
            buttonIndex == lineIndex,
            false
        );

        textBuilder.append
        (
            ShoutVisibility.values()[buttonIndex].getDescription(),
            ChatColors.getPartyCheckOptionDescriptionColor(),
            buttonIndex == lineIndex,
            false
        );

        return textBuilder.getText();
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
        ItemStack stack = getShoutVisibilityItemStack(type);

        TextUtils.CustomTextBuilder titleBuilder = new TextUtils.CustomTextBuilder();

        titleBuilder.append
        (
            ChatStrings.getPartyCheckShoutVisibilityTitleString(),
            ChatColors.getPartyCheckButtonTitleColor()
        );

        stack.set(DataComponentTypes.CUSTOM_NAME, titleBuilder.getText());
        stack.set(DataComponentTypes.LORE, getShoutVisibilityButtonLore(type.ordinal()));

        return stack;
    }

    private static ItemStack getShoutVisibilityItemStack(ShoutVisibility type)
    {
        String itemId = getShoutVisibilityItem(type);
        int customModelData = getShoutVisibilityCustomModelData(type);

        return GooeyLibsUtils.getCustomModelDataItemStack(itemId, customModelData);
    }

    private static String getShoutVisibilityItem(ShoutVisibility type)
    {
        if (type == ShoutVisibility.OPEN) return GUITextures.getShoutVisibilityOpenItem();
        if (type == ShoutVisibility.CLOSED) return GUITextures.getShoutVisibilityClosedItem();
        if (type == ShoutVisibility.SELF) return GUITextures.getShoutVisibilitySelfItem();
        return "minecraft:gray_concrete";
    }

    private static int getShoutVisibilityCustomModelData(ShoutVisibility type)
    {
        if (type == ShoutVisibility.OPEN) return GUITextures.getShoutVisibilityOpenCustomModelData();
        if (type == ShoutVisibility.CLOSED) return GUITextures.getShoutVisibilityClosedCustomModelData();
        if (type == ShoutVisibility.SELF) return GUITextures.getShoutVisibilitySelfCustomModelData();
        return 0;
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
