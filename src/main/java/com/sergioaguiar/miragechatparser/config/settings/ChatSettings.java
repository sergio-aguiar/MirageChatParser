package com.sergioaguiar.miragechatparser.config.settings;

public class ChatSettings
{
    protected static final boolean DEFAULT_PARSE_NON_PLAYER_MESSAGES = false;

    protected static final boolean DEFAULT_SHOW_NICKNAME = true;
    protected static final boolean DEFAULT_SHOW_FORM_IF_NORMAL = false;
    protected static final boolean DEFAULT_SHOW_NEUTERED_IF_FALSE = true;
    protected static final boolean DEFAULT_SHOW_EGG_GROUPS = true;

    private static boolean parseNonPlayerMessages;

    private static boolean showNickname;
    private static boolean showFormIfNormal;
    private static boolean showNeuteredIfFalse;
    private static boolean showEggGroups;

    public static void setDefaults()
    {
        parseNonPlayerMessages = DEFAULT_PARSE_NON_PLAYER_MESSAGES;

        showNickname = DEFAULT_SHOW_NICKNAME;
        showFormIfNormal = DEFAULT_SHOW_FORM_IF_NORMAL;
        showNeuteredIfFalse = DEFAULT_SHOW_NEUTERED_IF_FALSE;
        showEggGroups = DEFAULT_SHOW_EGG_GROUPS;
    }

    public static boolean parseNonPlayerMessages() { return parseNonPlayerMessages; }
    public static boolean showNickname() { return showNickname; }
    public static boolean showFormIfNormal() { return showFormIfNormal; }
    public static boolean showNeuteredIfFalse() { return showNeuteredIfFalse; }
    public static boolean showEggGroups() { return showEggGroups; }

    protected static void setParseNonPlayerMessages(boolean enabled) { parseNonPlayerMessages = enabled; }
    protected static void setShowNickname(boolean enabled) { showNickname = enabled; }
    protected static void setShowFormIfNormal(boolean enabled) { showFormIfNormal = enabled; }
    protected static void setShowNeuteredIfFalse(boolean enabled) { showNeuteredIfFalse = enabled; }
    protected static void setShowEggGroups(boolean enabled) { showEggGroups = enabled; }
}
