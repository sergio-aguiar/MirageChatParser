package com.sergioaguiar.miragechatparser.config.settings;

public class ChatSettings
{
    protected static final boolean DEFAULT_PARSE_NON_PLAYER_MESSAGES = false;

    protected static final boolean DEFAULT_SHOW_NICKNAME = true;
    protected static final boolean DEFAULT_SHOW_SPECIES = true;
    protected static final boolean DEFAULT_SHOW_LEVEL = true;
    protected static final boolean DEFAULT_SHOW_TYPES = true;
    protected static final boolean DEFAULT_SHOW_ABILITIES = true;
    protected static final boolean DEFAULT_SHOW_NATURE = true;
    protected static final boolean DEFAULT_SHOW_IVS = true;
    protected static final boolean DEFAULT_SHOW_EVS = true;
    protected static final boolean DEFAULT_SHOW_MOVES = true;
    protected static final boolean DEFAULT_SHOW_GENDER = true;
    protected static final boolean DEFAULT_SHOW_FRIENDSHIP = true;
    protected static final boolean DEFAULT_SHOW_HELD_ITEM = true;
    protected static final boolean DEFAULT_SHOW_BALL = true;
    protected static final boolean DEFAULT_SHOW_SIZE = true;
    protected static final boolean DEFAULT_SHOW_EGG_GROUPS = true;
    protected static final boolean DEFAULT_SHOW_NEUTERED = true;
    protected static final boolean DEFAULT_SHOW_OT = true;

    protected static final boolean DEFAULT_SHOW_FORM_IF_NORMAL = false;
    protected static final boolean DEFAULT_SHOW_NEUTERED_IF_FALSE = true;

    protected static final boolean DEFAULT_BOLD_HYPER_TRAINING_VALUES = false;
    protected static final boolean DEFAULT_ITALIC_HYPER_TRAINING_VALUES = false;

    private static boolean parseNonPlayerMessages;

    private static boolean showNickname;
    private static boolean showSpecies;
    private static boolean showLevel;
    private static boolean showTypes;
    private static boolean showAbilities;
    private static boolean showNature;
    private static boolean showIVs;
    private static boolean showEVs;
    private static boolean showMoves;
    private static boolean showGender;
    private static boolean showFriendship;
    private static boolean showHeldItem;
    private static boolean showBall;
    private static boolean showSize;
    private static boolean showEggGroups;
    private static boolean showNeutered;
    private static boolean showOT;

    private static boolean showFormIfNormal;
    private static boolean showNeuteredIfFalse;

    private static boolean boldHyperTrainingValues;
    private static boolean italicHyperTrainingValues;

    public static void setDefaults()
    {
        parseNonPlayerMessages = DEFAULT_PARSE_NON_PLAYER_MESSAGES;

        showNickname = DEFAULT_SHOW_NICKNAME;
        showSpecies = DEFAULT_SHOW_SPECIES;
        showLevel = DEFAULT_SHOW_LEVEL;
        showTypes = DEFAULT_SHOW_TYPES;
        showAbilities = DEFAULT_SHOW_ABILITIES;
        showNature = DEFAULT_SHOW_NATURE;
        showIVs = DEFAULT_SHOW_IVS;
        showEVs = DEFAULT_SHOW_EVS;
        showMoves = DEFAULT_SHOW_MOVES;
        showGender = DEFAULT_SHOW_GENDER;
        showFriendship = DEFAULT_SHOW_FRIENDSHIP;
        showHeldItem = DEFAULT_SHOW_HELD_ITEM;
        showBall = DEFAULT_SHOW_BALL;
        showSize = DEFAULT_SHOW_SIZE;
        showEggGroups = DEFAULT_SHOW_EGG_GROUPS;
        showNeutered = DEFAULT_SHOW_NEUTERED;
        showOT = DEFAULT_SHOW_OT;

        showFormIfNormal = DEFAULT_SHOW_FORM_IF_NORMAL;
        showNeuteredIfFalse = DEFAULT_SHOW_NEUTERED_IF_FALSE;

        boldHyperTrainingValues = DEFAULT_BOLD_HYPER_TRAINING_VALUES;
        italicHyperTrainingValues = DEFAULT_ITALIC_HYPER_TRAINING_VALUES;
    }

    public static boolean parseNonPlayerMessages() { return parseNonPlayerMessages; }
    public static boolean showNickname() { return showNickname; }
    public static boolean showSpecies() { return showSpecies; }
    public static boolean showLevel() { return showLevel; }
    public static boolean showTypes() { return showTypes; }
    public static boolean showAbilities() { return showAbilities; }
    public static boolean showNature() { return showNature; }
    public static boolean showIVs() { return showIVs; }
    public static boolean showEVs() { return showEVs; }
    public static boolean showMoves() { return showMoves; }
    public static boolean showGender() { return showGender; }
    public static boolean showFriendship() { return showFriendship; }
    public static boolean showHeldItem() { return showHeldItem; }
    public static boolean showBall() { return showBall; }
    public static boolean showSize() { return showSize; }
    public static boolean showEggGroups() { return showEggGroups; }
    public static boolean showNeutered() { return showNeutered; }
    public static boolean showOT() { return showOT; }
    public static boolean showFormIfNormal() { return showFormIfNormal; }
    public static boolean showNeuteredIfFalse() { return showNeuteredIfFalse; }
    public static boolean shouldBoldHyperTrainingValues() { return boldHyperTrainingValues; }
    public static boolean shouldItalicHyperTrainingValues() { return italicHyperTrainingValues; }

    protected static void setParseNonPlayerMessages(boolean enabled) { parseNonPlayerMessages = enabled; }
    protected static void setShowNickname(boolean enabled) { showNickname = enabled; }
    protected static void setShowSpecies(boolean enabled) { showSpecies = enabled; }
    protected static void setShowLevel(boolean enabled) { showLevel = enabled; }
    protected static void setShowTypes(boolean enabled) { showTypes = enabled; }
    protected static void setShowAbilities(boolean enabled) { showAbilities = enabled; }
    protected static void setShowNature(boolean enabled) { showNature = enabled; }
    protected static void setShowIVs(boolean enabled) { showIVs = enabled; }
    protected static void setShowEVs(boolean enabled) { showEVs = enabled; }
    protected static void setShowMoves(boolean enabled) { showMoves = enabled; }
    protected static void setShowGender(boolean enabled) { showGender = enabled; }
    protected static void setShowFriendship(boolean enabled) { showFriendship = enabled; }
    protected static void setShowHeldItem(boolean enabled) { showHeldItem = enabled; }
    protected static void setShowBall(boolean enabled) { showBall = enabled; }
    protected static void setShowSize(boolean enabled) { showSize = enabled; }
    protected static void setShowEggGroups(boolean enabled) { showEggGroups = enabled; }
    protected static void setShowNeutered(boolean enabled) { showNeutered = enabled; }
    protected static void setShowOT(boolean enabled) { showOT = enabled; }
    protected static void setShowFormIfNormal(boolean enabled) { showFormIfNormal = enabled; }
    protected static void setShowNeuteredIfFalse(boolean enabled) { showNeuteredIfFalse = enabled; }
    protected static void setBoldHyperTrainingValues(boolean enabled) { boldHyperTrainingValues = enabled; }
    protected static void setItalicHyperTrainingValues(boolean enabled) { italicHyperTrainingValues = enabled; }
}
