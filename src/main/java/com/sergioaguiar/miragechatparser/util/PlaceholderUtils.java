package com.sergioaguiar.miragechatparser.util;

import com.sergioaguiar.miragechatparser.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlaceholderUtils
{
    public static class AfkPlaceholder
    {
        public static void register()
        {
            Placeholders.register(
                new Identifier("mirageessentials", "afk"),
                (context, arguments) ->
                {
                    if (!context.hasPlayer()) return PlaceholderResult.invalid("No player!");

                    MutableText placeholder = Text.literal("").setStyle(Style.EMPTY);

                    placeholder = placeholder
                        .append(Text.literal("[")
                            .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKPlaceholderBracketColor())));

                    placeholder = placeholder
                        .append(Text.literal("AFK")
                            .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKPlaceholderTextColor())));

                    placeholder = placeholder
                        .append(Text.literal("]")
                            .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKPlaceholderBracketColor())));

                    return PlaceholderResult.value(AntiAFKManager.isPlayerAFK(context.player()) ? placeholder : Text.literal(""));
                }
            );
        }
    }

    private static final String MOD_ID_STRING = "placeholder-api";

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }    
}
