package com.sergioaguiar.miragechatparser.config.antiafk.colors;

import com.mojang.serialization.DataResult;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.text.TextColor;

public class AntiAFKColors
{
    private static final DataResult<TextColor> DEFAULT_AFK_CHECKER_PREFIX_COLOR = TextColor.parse("#2facdd");
    private static final DataResult<TextColor> DEFAULT_AFK_CHECKER_TEXT_COLOR = TextColor.parse("#d1d8eb");
    private static final DataResult<TextColor> DEFAULT_AFK_CHECKER_PLAYER_COLOR = TextColor.parse("#cfe95e");
    private static final DataResult<TextColor> DEFAULT_AFK_CHECKER_GONE_COLOR = TextColor.parse("#2facdd");
    private static final DataResult<TextColor> DEFAULT_AFK_CHECKER_TIME_COLOR = TextColor.parse("#cfe95e");
    private static final DataResult<TextColor> DEFAULT_AFK_CAPCHA_PREFIX_COLOR = TextColor.parse("#b81106");
    private static final DataResult<TextColor> DEFAULT_AFK_CAPCHA_TEXT_COLOR = TextColor.parse("#d1d8eb");
    private static final DataResult<TextColor> DEFAULT_AFK_CAPCHA_QUESTION_COLOR = TextColor.parse("#cfe95e");

    private static TextColor afkCheckerPrefixColor;
    private static TextColor afkCheckerTextColor;
    private static TextColor afkCheckerPlayerColor;
    private static TextColor afkCheckerGoneColor;
    private static TextColor afkCheckerTimeColor;
    private static TextColor afkCapchaPrefixColor;
    private static TextColor afkCapchaTextColor;
    private static TextColor afkCapchaQuestionColor;

    public static void setDefaults()
    {
        try 
        {
            afkCheckerPrefixColor = DEFAULT_AFK_CHECKER_PREFIX_COLOR.getOrThrow();
            afkCheckerTextColor = DEFAULT_AFK_CHECKER_TEXT_COLOR.getOrThrow();
            afkCheckerPlayerColor = DEFAULT_AFK_CHECKER_PLAYER_COLOR.getOrThrow();
            afkCheckerGoneColor = DEFAULT_AFK_CHECKER_GONE_COLOR.getOrThrow();
            afkCheckerTimeColor = DEFAULT_AFK_CHECKER_TIME_COLOR.getOrThrow();
            afkCapchaPrefixColor = DEFAULT_AFK_CAPCHA_PREFIX_COLOR.getOrThrow();
            afkCapchaTextColor = DEFAULT_AFK_CAPCHA_TEXT_COLOR.getOrThrow();
            afkCapchaQuestionColor = DEFAULT_AFK_CAPCHA_QUESTION_COLOR.getOrThrow();
        }
        catch (IllegalStateException e) 
        {
            ModLogger.error("Failed to load default anti-AFK colors: %s".formatted(e.getMessage()), e);
        }
    }

    public static TextColor getAFKCheckerPrefixColor() { return afkCheckerPrefixColor; }
    public static TextColor getAFKCheckerTextColor() { return afkCheckerTextColor; }
    public static TextColor getAFKCheckerPlayerColor() { return afkCheckerPlayerColor; }
    public static TextColor getAFKCheckerGoneColor() { return afkCheckerGoneColor; }
    public static TextColor getAFKCheckerTimeColor() { return afkCheckerTimeColor; }
    public static TextColor getAFKCapchaPrefixColor() { return afkCapchaPrefixColor; }
    public static TextColor getAFKCapchaTextColor() { return afkCapchaTextColor; }
    public static TextColor getAFKCapchaQuestionColor() { return afkCapchaQuestionColor; }

    protected static void setAFKCheckerPrefixColor(TextColor color) { afkCheckerPrefixColor = color; }
    protected static void setAFKCheckerTextColor(TextColor color) { afkCheckerTextColor = color; }
    protected static void setAFKCheckerPlayerColor(TextColor color) { afkCheckerPlayerColor = color; }
    protected static void setAFKCheckerGoneColor(TextColor color) { afkCheckerGoneColor = color; }
    protected static void setAFKCheckerTimeColor(TextColor color) { afkCheckerTimeColor = color; }
    protected static void setAFKCapchaPrefixColor(TextColor color) { afkCapchaPrefixColor = color; }
    protected static void setAFKCapchaTextColor(TextColor color) { afkCapchaTextColor = color; }
    protected static void setAFKCapchaQuestionColor(TextColor color) { afkCapchaQuestionColor = color; }
}
