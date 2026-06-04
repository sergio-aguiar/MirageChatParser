package com.sergioaguiar.mirageessentials.util;

import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.mirageessentials.config.antiafk.strings.AntiAFKStrings;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;

import eu.pb4.placeholders.api.PlaceholderResult;
import eu.pb4.placeholders.api.Placeholders;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class PlaceholderUtils
{
    public static class AfkPlaceholder
    {
        public static void register()
        {
            Placeholders.register(
                new Identifier("mirageessentials", "afk-textobject"),
                (context, arguments) ->
                {
                    if (!context.hasPlayer()) return PlaceholderResult.invalid("No player!");

                    TextUtils.CustomTextBuilder placeholderBuilder = new TextUtils.CustomTextBuilder();

                    placeholderBuilder.append
                    (
                        "[",
                        AntiAFKColors.getAFKPlaceholderBracketColor()
                    );

                    placeholderBuilder.append
                    (
                        AntiAFKStrings.getTextObjectPlaceholderAFKText(),
                        AntiAFKColors.getAFKPlaceholderTextColor()
                    );

                    placeholderBuilder.append
                    (
                        "]",
                        AntiAFKColors.getAFKPlaceholderBracketColor()
                    );

                    return PlaceholderResult.value(AntiAFKManager.isPlayerAFK(context.player()) ? placeholderBuilder.getText() : Text.literal(""));
                }
            );

            Placeholders.register(
                new Identifier("mirageessentials", "afk-mcformatting"),
                (context, arguments) ->
                {
                    if (!context.hasPlayer()) return PlaceholderResult.invalid("No player!");

                    return PlaceholderResult.value(AntiAFKManager.isPlayerAFK(context.player()) ? AntiAFKStrings.getMcFormattingPlaceholderAFKText() : "");
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
