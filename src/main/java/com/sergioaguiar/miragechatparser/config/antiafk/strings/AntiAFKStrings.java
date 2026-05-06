package com.sergioaguiar.miragechatparser.config.antiafk.strings;

public class AntiAFKStrings
{
    private static final String DEFAULT_TEXT_OBJECT_PLACEHOLDER_AFK_TEXT = "AFK";
    private static final String DEFAULT_MC_FORMATTING_PLACEHOLDER_AFK_TEXT = "&7[&bAFK&7]";

    private static String textObjectPlaceholderAFKText;
    private static String mcFormattingPlaceholderAFKText;

    public static void setDefaults()
    {
        textObjectPlaceholderAFKText = DEFAULT_TEXT_OBJECT_PLACEHOLDER_AFK_TEXT;
        mcFormattingPlaceholderAFKText = DEFAULT_MC_FORMATTING_PLACEHOLDER_AFK_TEXT;
    }
    
    public static String getTextObjectPlaceholderAFKText() { return textObjectPlaceholderAFKText; }
    public static String getMcFormattingPlaceholderAFKText() { return mcFormattingPlaceholderAFKText; }

    protected static void setTextObjectPlaceholderAFKText(String string) { textObjectPlaceholderAFKText = string; }
    protected static void setMcFormattingPlaceholderAFKText(String string) { mcFormattingPlaceholderAFKText = string; }
}
