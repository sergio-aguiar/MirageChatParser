package com.sergioaguiar.miragechatparser.config;

public class ChatSettings
{
    private static final boolean DEFAULT_SHOW_NICKNAME = true;

    private static boolean showNickname;

    public static void setDefaults()
    {
        showNickname = DEFAULT_SHOW_NICKNAME;
    }

    public static void setShowNickname(boolean enabled)
    {
        showNickname = enabled;
    }

    public static boolean showNickname()
    {
        return showNickname;
    }
}
