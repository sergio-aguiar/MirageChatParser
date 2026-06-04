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
        TextUtils.CustomTextBuilder emptyTextBuilder = new TextUtils.CustomTextBuilder();

        emptyTextBuilder.append
        (
            "",
            ChatColors.getPartyCheckOptionNameColor()
        );

        List<Text> loreText = List.of
        (
            getButtonLoreLine(ShoutType.GENERAL.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutType.RIDE.ordinal(), buttonIndex),
            getButtonLoreLine(ShoutType.RIBBON.ordinal(), buttonIndex),
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
            ShoutType.values()[buttonIndex].getName(),
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
            ShoutType.values()[buttonIndex].getDescription(),
            ChatColors.getPartyCheckOptionDescriptionColor(),
            buttonIndex == lineIndex,
            false
        );

        return textBuilder.getText();
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
        ItemStack stack = getShoutTypeItemStack(type);

        TextUtils.CustomTextBuilder titleBuilder = new TextUtils.CustomTextBuilder();

        titleBuilder.append
        (
            ChatStrings.getPartyCheckShoutTypeTitleString(),
            ChatColors.getPartyCheckButtonTitleColor()
        );

        stack.set(DataComponentTypes.CUSTOM_NAME, titleBuilder.getText());
        stack.set(DataComponentTypes.LORE, getShoutTypeButtonLore(type.ordinal()));

        return stack;
    }

    private static ItemStack getShoutTypeItemStack(ShoutType type)
    {
        String itemId = getShoutTypeItem(type);
        int customModelData = getShoutTypeCustomModelData(type);

        return GooeyLibsUtils.getCustomModelDataItemStack(itemId, customModelData);
    }

    private static String getShoutTypeItem(ShoutType type)
    {
        if (type == ShoutType.GENERAL) return GUITextures.getShoutTypeGeneralItem();
        if (type == ShoutType.RIDE) return GUITextures.getShoutTypeRideItem();
        if (type == ShoutType.RIBBON) return GUITextures.getShoutTypeRibbonItem();
        return "minecraft:gray_concrete";
    }

    private static int getShoutTypeCustomModelData(ShoutType type)
    {
        if (type == ShoutType.GENERAL) return GUITextures.getShoutTypeGeneralCustomModelData();
        if (type == ShoutType.RIDE) return GUITextures.getShoutTypeRideCustomModelData();
        if (type == ShoutType.RIBBON) return GUITextures.getShoutTypeRibbonCustomModelData();
        return 0;
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
