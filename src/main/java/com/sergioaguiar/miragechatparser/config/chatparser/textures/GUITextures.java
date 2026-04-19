package com.sergioaguiar.miragechatparser.config.chatparser.textures;

public class GUITextures
{
    protected static final String DEFAULT_SHOUT_TYPE_GENERAL_ITEM = "minecraft:white_concrete";
    protected static final int DEFAULT_SHOUT_TYPE_GENERAL_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_SHOUT_TYPE_RIDE_ITEM = "minecraft:orange_concrete";
    protected static final int DEFAULT_SHOUT_TYPE_RIDE_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_SHOUT_TYPE_RIBBON_ITEM = "minecraft:pink_concrete";
    protected static final int DEFAULT_SHOUT_TYPE_RIBBON_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_SHOUT_VISIBILITY_OPEN_ITEM = "minecraft:green_concrete";
    protected static final int DEFAULT_SHOUT_VISIBILITY_OPEN_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_SHOUT_VISIBILITY_CLOSED_ITEM = "minecraft:red_concrete";
    protected static final int DEFAULT_SHOUT_VISIBILITY_CLOSED_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_SHOUT_VISIBILITY_SELF_ITEM = "minecraft:blue_concrete";
    protected static final int DEFAULT_SHOUT_VISIBILITY_SELF_CUSTOM_MODEL_DATA = 0;

    protected static final String DEFAULT_PARTY_SHOUT_ALL_ITEM = "minecraft:yellow_concrete";
    protected static final int DEFAULT_PARTY_SHOUT_ALL_CUSTOM_MODEL_DATA = 0;

    private static String shoutTypeGeneralItem;
    private static int shoutTypeGeneralCustomModelData;

    private static String shoutTypeRideItem;
    private static int shoutTypeRideCustomModelData;

    private static String shoutTypeRibbonItem;
    private static int shoutTypeRibbonCustomModelData;

    private static String shoutVisibilityOpenItem;
    private static int shoutVisibilityOpenCustomModelData;

    private static String shoutVisibilityClosedItem;
    private static int shoutVisibilityClosedCustomModelData;

    private static String shoutVisibilitySelfItem;
    private static int shoutVisibilitySelfCustomModelData;

    private static String partyShoutAllItem;
    private static int partyShoutAllCustomModelData;

    public static void setDefaults()
    {
        shoutTypeGeneralItem = DEFAULT_SHOUT_TYPE_GENERAL_ITEM;
        shoutTypeGeneralCustomModelData = DEFAULT_SHOUT_TYPE_GENERAL_CUSTOM_MODEL_DATA;

        shoutTypeRideItem = DEFAULT_SHOUT_TYPE_RIDE_ITEM;
        shoutTypeRideCustomModelData = DEFAULT_SHOUT_TYPE_RIDE_CUSTOM_MODEL_DATA;

        shoutTypeRibbonItem = DEFAULT_SHOUT_TYPE_RIBBON_ITEM;
        shoutTypeRibbonCustomModelData = DEFAULT_SHOUT_TYPE_RIBBON_CUSTOM_MODEL_DATA;

        shoutVisibilityOpenItem = DEFAULT_SHOUT_VISIBILITY_OPEN_ITEM;
        shoutVisibilityOpenCustomModelData = DEFAULT_SHOUT_VISIBILITY_OPEN_CUSTOM_MODEL_DATA;

        shoutVisibilityClosedItem = DEFAULT_SHOUT_VISIBILITY_CLOSED_ITEM;
        shoutVisibilityClosedCustomModelData = DEFAULT_SHOUT_VISIBILITY_CLOSED_CUSTOM_MODEL_DATA;

        shoutVisibilitySelfItem = DEFAULT_SHOUT_VISIBILITY_SELF_ITEM;
        shoutVisibilitySelfCustomModelData = DEFAULT_SHOUT_VISIBILITY_SELF_CUSTOM_MODEL_DATA;

        partyShoutAllItem = DEFAULT_PARTY_SHOUT_ALL_ITEM;
        partyShoutAllCustomModelData = DEFAULT_PARTY_SHOUT_ALL_CUSTOM_MODEL_DATA;
    }

    public static String getShoutTypeGeneralItem() { return shoutTypeGeneralItem; }
    public static int getShoutTypeGeneralCustomModelData() { return shoutTypeGeneralCustomModelData; }

    public static String getShoutTypeRideItem() { return shoutTypeRideItem; }
    public static int getShoutTypeRideCustomModelData() { return shoutTypeRideCustomModelData; }

    public static String getShoutTypeRibbonItem() { return shoutTypeRibbonItem; }
    public static int getShoutTypeRibbonCustomModelData() { return shoutTypeRibbonCustomModelData; }

    public static String getShoutVisibilityOpenItem() { return shoutVisibilityOpenItem; }
    public static int getShoutVisibilityOpenCustomModelData() { return shoutVisibilityOpenCustomModelData; }

    public static String getShoutVisibilityClosedItem() { return shoutVisibilityClosedItem; }
    public static int getShoutVisibilityClosedCustomModelData() { return shoutVisibilityClosedCustomModelData; }

    public static String getShoutVisibilitySelfItem() { return shoutVisibilitySelfItem; }
    public static int getShoutVisibilitySelfCustomModelData() { return shoutVisibilitySelfCustomModelData; }

    public static String getPartyShoutAllItem() { return partyShoutAllItem; }
    public static int getPartyShoutAllCustomModelData() { return partyShoutAllCustomModelData; }

    protected static void setShoutTypeGeneralItem(String item) { shoutTypeGeneralItem = item; }
    protected static void setShoutTypeGeneralCustomModelData(int data) { shoutTypeGeneralCustomModelData = data; }

    protected static void setShoutTypeRideItem(String item) { shoutTypeRideItem = item; }
    protected static void setShoutTypeRideCustomModelData(int data) { shoutTypeRideCustomModelData = data; }

    protected static void setShoutTypeRibbonItem(String item) { shoutTypeRibbonItem = item; }
    protected static void setShoutTypeRibbonCustomModelData(int data) { shoutTypeRibbonCustomModelData = data; }

    protected static void setShoutVisibilityOpenItem(String item) { shoutVisibilityOpenItem = item; }
    protected static void setShoutVisibilityOpenCustomModelData(int data) { shoutVisibilityOpenCustomModelData = data; }

    protected static void setShoutVisibilityClosedItem(String item) { shoutVisibilityClosedItem = item; }
    protected static void setShoutVisibilityClosedCustomModelData(int data) { shoutVisibilityClosedCustomModelData = data; }

    protected static void setShoutVisibilitySelfItem(String item) { shoutVisibilitySelfItem = item; }
    protected static void setShoutVisibilitySelfCustomModelData(int data) { shoutVisibilitySelfCustomModelData = data; }

    protected static void setPartyShoutAllItem(String item) { partyShoutAllItem = item; }
    protected static void setPartyShoutAllCustomModelData(int data) { partyShoutAllCustomModelData = data; }
}