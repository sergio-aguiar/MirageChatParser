package com.sergioaguiar.miragechatparser.config;

public class ChatSettings
{
    public static final boolean DEFAULT_PARSE_NON_PLAYER_MESSAGES = false;

    public static final boolean DEFAULT_SHOW_NICKNAME = true;

    private static boolean parseNonPlayerMessages;

    private static boolean showNickname;

    public static void setDefaults()
    {
        parseNonPlayerMessages = DEFAULT_PARSE_NON_PLAYER_MESSAGES;

        showNickname = DEFAULT_SHOW_NICKNAME;
    }

    public static void setParseNonPlayerMessages(boolean enabled)
    {
        parseNonPlayerMessages = enabled;
    }

    public static void setShowNickname(boolean enabled)
    {
        showNickname = enabled;
    }

    public static boolean parseNonPlayerMessages()
    {
        return parseNonPlayerMessages;
    }

    public static boolean showNickname()
    {
        return showNickname;
    }
}
