package com.sergioaguiar.miragechatparser.config.strings;

public class ChatStrings
{
    private static final String DEFAULT_TRUE_STRING = "Yes";
    private static final String DEFAULT_FALSE_STRING = "No";

    private static final String DEFAULT_SHINY_ICON_STRING = "★ ";

    private static final String DEFAULT_SPECIES_STRING = "Species: ";
    private static final String DEFAULT_LEVEL_STRING = "Level: ";
    private static final String DEFAULT_TYPE_STRING = "Type: ";
    private static final String DEFAULT_TYPES_STRING = "Types: ";
    private static final String DEFAULT_ABILITY_STRING = "Ability: ";
    private static final String DEFAULT_NATURE_STRING = "Nature: ";
    private static final String DEFAULT_NATURE_MINTED_STRING = "Nature (Mint): ";
    private static final String DEFAULT_IVS_STRING = "IVs: ";
    private static final String DEFAULT_EVS_STRING = "EVs: ";
    private static final String DEFAULT_MOVES_STRING = "Moves: ";
    private static final String DEFAULT_GENDER_STRING = "Gender: ";
    private static final String DEFAULT_FRIENDSHIP_STRING = "Friendship: ";
    private static final String DEFAULT_HELD_ITEM_STRING = "Held Item: ";
    private static final String DEFAULT_CAUGHT_BALL_STRING = "Caught Ball: ";
    private static final String DEFAULT_SIZE_STRING = "Size: ";
    private static final String DEFAULT_EGG_GROUPS_STRING = "Egg Groups: ";
    private static final String DEFAULT_NEUTERED_STRING = "Neutered: ";
    private static final String DEFAULT_ORIGINAL_TRAINER_STRING = "Original Trainer: ";

    private static final String DEFAULT_HEALTH_STRING = "HP";
    private static final String DEFAULT_ATTACK_STRING = "Atk";
    private static final String DEFAULT_DEFENSE_STRING = "Def";
    private static final String DEFAULT_SPECIAL_ATTACK_STRING = "SpA";
    private static final String DEFAULT_SPECIAL_DEFENSE_STRING = "SpD";
    private static final String DEFAULT_SPEED_STRING = "Spe";

    private static final String DEFAULT_HIDDEN_ABILITY_STRING = "HA";
    private static final String DEFAULT_TYPE_SEPARATOR_STRING = "/";
    private static final String DEFAULT_STAT_INCREASE_STRING = "+";
    private static final String DEFAULT_STAT_DECREASE_STRING = "-";
    private static final String DEFAULT_MOVE_SEPARATOR_STRING = ", ";
    private static final String DEFAULT_MALE_ICON_STRING = "♂";
    private static final String DEFAULT_FEMALE_ICON_STRING = "♀";
    private static final String DEFAULT_GENDERLESS_ICON_STRING = "⚲";
    private static final String DEFAULT_EMPTY_HELD_ITEM_STRING = "None";
    private static final String DEFAULT_EGG_GROUPS_SEPARATOR_STRING = ", ";

    private static String trueString;
    private static String falseString;

    private static String shinyIconString;

    private static String speciesString;
    private static String levelString;
    private static String typeString;
    private static String typesString;
    private static String abilityString;
    private static String natureString;
    private static String natureMintedString;
    private static String ivsString;
    private static String evsString;
    private static String movesString;
    private static String genderString;
    private static String friendshipString;
    private static String heldItemString;
    private static String caughtBallString;
    private static String sizeString;
    private static String eggGroupsString;
    private static String neuteredString;
    private static String originalTrainerString;

    private static String healthString;
    private static String attackString;
    private static String defenseString;
    private static String specialAttackString;
    private static String specialDefenseString;
    private static String speedString;

    private static String hiddenAbilityString;
    private static String typeSeparatorString;
    private static String statIncreaseString;
    private static String statDecreaseString;
    private static String moveSeparatorString;
    private static String maleIconString;
    private static String femaleIconString;
    private static String genderlessIconString;
    private static String emptyHeldItemString;
    private static String eggGroupsSeparatorString;

    public static void setDefaults()
    {
        trueString = DEFAULT_TRUE_STRING;
        falseString = DEFAULT_FALSE_STRING;

        shinyIconString = DEFAULT_SHINY_ICON_STRING;

        speciesString = DEFAULT_SPECIES_STRING;
        levelString = DEFAULT_LEVEL_STRING;
        typeString = DEFAULT_TYPE_STRING;
        typesString = DEFAULT_TYPES_STRING;
        abilityString = DEFAULT_ABILITY_STRING;
        natureString = DEFAULT_NATURE_STRING;
        natureMintedString = DEFAULT_NATURE_MINTED_STRING;
        ivsString = DEFAULT_IVS_STRING;
        evsString = DEFAULT_EVS_STRING;
        movesString = DEFAULT_MOVES_STRING;
        genderString = DEFAULT_GENDER_STRING;
        friendshipString = DEFAULT_FRIENDSHIP_STRING;
        heldItemString = DEFAULT_HELD_ITEM_STRING;
        caughtBallString = DEFAULT_CAUGHT_BALL_STRING;
        sizeString = DEFAULT_SIZE_STRING;
        eggGroupsString = DEFAULT_EGG_GROUPS_STRING;
        neuteredString = DEFAULT_NEUTERED_STRING;
        originalTrainerString = DEFAULT_ORIGINAL_TRAINER_STRING;

        healthString = DEFAULT_HEALTH_STRING;
        attackString = DEFAULT_ATTACK_STRING;
        defenseString = DEFAULT_DEFENSE_STRING;
        specialAttackString = DEFAULT_SPECIAL_ATTACK_STRING;
        specialDefenseString = DEFAULT_SPECIAL_DEFENSE_STRING;
        speedString = DEFAULT_SPEED_STRING;

        hiddenAbilityString = DEFAULT_HIDDEN_ABILITY_STRING;
        typeSeparatorString = DEFAULT_TYPE_SEPARATOR_STRING;
        statIncreaseString = DEFAULT_STAT_INCREASE_STRING;
        statDecreaseString = DEFAULT_STAT_DECREASE_STRING;
        moveSeparatorString = DEFAULT_MOVE_SEPARATOR_STRING;
        maleIconString = DEFAULT_MALE_ICON_STRING;
        femaleIconString = DEFAULT_FEMALE_ICON_STRING;
        genderlessIconString = DEFAULT_GENDERLESS_ICON_STRING;
        emptyHeldItemString = DEFAULT_EMPTY_HELD_ITEM_STRING;
        eggGroupsSeparatorString = DEFAULT_EGG_GROUPS_SEPARATOR_STRING;
    }

    public static String getTrueString() { return trueString; }
    public static String getFalseString() { return falseString; }
    public static String getShinyIconString() { return shinyIconString; }
    public static String getSpeciesString() { return speciesString; }
    public static String getLevelString() { return levelString; }
    public static String getTypeString() { return typeString; }
    public static String getTypesString() { return typesString; }
    public static String getAbilityString() { return abilityString; }
    public static String getNatureString() { return natureString; }
    public static String getNatureMintedString() { return natureMintedString; }
    public static String getIVsString() { return ivsString; }
    public static String getEVsString() { return evsString; }
    public static String getMovesString() { return movesString; }
    public static String getGenderString() { return genderString; }
    public static String getFriendshipString() { return friendshipString; }
    public static String getHeldItemString() { return heldItemString; }
    public static String getCaughtBallString() { return caughtBallString; }
    public static String getSizeString() { return sizeString; }
    public static String getEggGroupsString() { return eggGroupsString; }
    public static String getNeuteredString() { return neuteredString; }
    public static String getOriginalTrainerString() { return originalTrainerString; }
    public static String getHealthString() { return healthString; }
    public static String getAttackString() { return attackString; }
    public static String getDefenseString() { return defenseString; }
    public static String getSpecialAttackString() { return specialAttackString; }
    public static String getSpecialDefenseString() { return specialDefenseString; }
    public static String getSpeedString() { return speedString; }
    public static String getHiddenAbilityString() { return hiddenAbilityString; }
    public static String getTypeSeparatorString() { return typeSeparatorString; }
    public static String getStatIncreaseString() { return statIncreaseString; }
    public static String getStatDecreaseString() { return statDecreaseString; }
    public static String getMoveSeparatorString() { return moveSeparatorString; }
    public static String getMaleIconString() { return maleIconString; }
    public static String getFemaleIconString() { return femaleIconString; }
    public static String getGenderlessIconString() { return genderlessIconString; }
    public static String getEmptyHeldItemString() { return emptyHeldItemString; }
    public static String getEggGroupsSeparatorString() { return eggGroupsSeparatorString; }

    protected static void setTrueString(String string) { trueString = string; }
    protected static void setFalseString(String string) { falseString = string; }
    protected static void setShinyIconString(String string) { shinyIconString = string; }
    protected static void setSpeciesString(String string) { speciesString = string; }
    protected static void setLevelString(String string) { levelString = string; }
    protected static void setTypeString(String string) { typeString = string; }
    protected static void setTypesString(String string) { typesString = string; }
    protected static void setAbilityString(String string) { abilityString = string; }
    protected static void setNatureString(String string) { natureString = string; }
    protected static void setNatureMintedString(String string) { natureMintedString = string; }
    protected static void setIVsString(String string) { ivsString = string; }
    protected static void setEVsString(String string) { evsString = string; }
    protected static void setMovesString(String string) { movesString = string; }
    protected static void setGenderString(String string) { genderString = string; }
    protected static void setFriendshipString(String string) { friendshipString = string; }
    protected static void setHeldItemString(String string) { heldItemString = string; }
    protected static void setCaughtBallString(String string) { caughtBallString = string; }
    protected static void setSizeString(String string) { sizeString = string; }
    protected static void setEggGroupsString(String string) { eggGroupsString = string; }
    protected static void setNeuteredString(String string) { neuteredString = string; }
    protected static void setOriginalTrainerString(String string) { originalTrainerString = string; }
    protected static void setHealthString(String string) { healthString = string; }
    protected static void setAttackString(String string) { attackString = string; }
    protected static void setDefenseString(String string) { defenseString = string; }
    protected static void setSpecialAttackString(String string) { specialAttackString = string; }
    protected static void setSpecialDefenseString(String string) { specialDefenseString = string; }
    protected static void setSpeedString(String string) { speedString = string; }
    protected static void setHiddenAbilityString(String string) { hiddenAbilityString = string; }
    protected static void setTypeSeparatorString(String string) { typeSeparatorString = string; }
    protected static void setStatIncreaseString(String string) { statIncreaseString = string; }
    protected static void setStatDecreaseString(String string) { statDecreaseString = string; }
    protected static void setMoveSeparatorString(String string) { moveSeparatorString = string; }
    protected static void setMaleIconString(String string) { maleIconString = string; }
    protected static void setFemaleIconString(String string) { femaleIconString = string; }
    protected static void setGenderlessIconString(String string) { genderlessIconString = string; }
    protected static void setEmptyHeldItemString(String string) { emptyHeldItemString = string; }
    protected static void setEggGroupsSeparatorString(String string) { eggGroupsSeparatorString = string; }
}
