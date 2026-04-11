package com.sergioaguiar.miragechatparser.config.modules;

public class Modules 
{
    protected static final boolean DEFAULT_ENABLE_CHAT_PARSER_MODULE = true;
    protected static final boolean DEFAULT_ENABLE_ANTI_AFK_MODULE = true;

    private static boolean enableChatParserModule;
    private static boolean enableAntiAFKModule;

    public static void setDefaults()
    {
        enableChatParserModule = DEFAULT_ENABLE_CHAT_PARSER_MODULE;
        enableAntiAFKModule = DEFAULT_ENABLE_ANTI_AFK_MODULE;
    }

    public static boolean shouldEnableChatParserModule() { return enableChatParserModule; }
    public static boolean shouldEnableAntiAFKModule() { return enableAntiAFKModule; }

    protected static void setEnableChatParserModule(boolean enabled) { enableChatParserModule = enabled; }
    protected static void setEnableAntiAFKModule(boolean enabled) { enableAntiAFKModule = enabled; }
}
