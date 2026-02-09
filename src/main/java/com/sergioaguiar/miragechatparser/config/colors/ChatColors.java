package com.sergioaguiar.miragechatparser.config.colors;

import com.mojang.serialization.DataResult;
import com.sergioaguiar.miragechatparser.util.ModLogger;

import net.minecraft.text.TextColor;

public class ChatColors 
{
    public enum TypeColor 
    {
        NORMAL("#A8A77A"),
        FIRE("#EE8130"),
        WATER("#6390F0"),
        ELECTRIC("#F7D02C"),
        GRASS("#7AC74C"),
        ICE("#96D9D6"),
        FIGHTING("#C22E28"),
        POISON("#A33EA1"),
        GROUND("#E2BF65"),
        FLYING("#A98FF3"),
        PSYCHIC("#F95587"),
        BUG("#A6B91A"),
        ROCK("#B6A136"),
        GHOST("#735797"),
        DRAGON("#6F35FC"),
        DARK("#705746"),
        STEEL("#B7B7CE"),
        FAIRY("#D685AD");

        private static final TextColor DEFAULT_COLOR = TextColor.fromRgb(0xFFFFFF);

        private final TextColor color;

        TypeColor(String hex) 
        {
            TextColor parsed;
            try
            {
                parsed = TextColor.parse(hex).getOrThrow();
            } 
            catch (Exception e)
            {
                ModLogger.error("Failed while loading type colors: %s".formatted(e.getMessage()));
                parsed = TextColor.fromRgb(0xFFFFFF);
            }
            this.color = parsed;
        }

        public TextColor getColor()
        {
            return color;
        }

        public static TextColor fromTypeName(String name)
        {
            if (name == null) return TextColor.fromRgb(0xFFFFFF);
            try
            {
                return valueOf(name.trim().toUpperCase()).getColor();
            }
            catch (IllegalArgumentException e)
            {
                ModLogger.error("Failed while trying to get type color for name '%s': %s".formatted(name, e.getMessage()));
                return DEFAULT_COLOR;
            }
        }
    }

    private static final DataResult<TextColor> DEFAULT_COMMAND_PREFIX_COLOR = TextColor.parse("#2facdd");
    private static final DataResult<TextColor> DEFAULT_COMMAND_VALUE_COLOR = TextColor.parse("#d1d8eb");
    private static final DataResult<TextColor> DEFAULT_COMMAND_PLAYER_COLOR = TextColor.parse("#cfe95e");

    private static final DataResult<TextColor> DEFAULT_HOVERABLE_BRACKET_COLOR = TextColor.parse("#0a9120");
    private static final DataResult<TextColor> DEFAULT_HOVERABLE_SHINY_BRACKET_COLOR = TextColor.parse("#e7e436");
    private static final DataResult<TextColor> DEFAULT_HOVERABLE_ERROR_BRACKET_COLOR = TextColor.parse("#c00303");
    private static final DataResult<TextColor> DEFAULT_HOVERABLE_TEXT_COLOR = TextColor.parse("#21bb3a");
    private static final DataResult<TextColor> DEFAULT_HOVERABLE_SHINY_TEXT_COLOR = TextColor.parse("#b6b30b");
    private static final DataResult<TextColor> DEFAULT_HOVERABLE_ERROR_TEXT_COLOR = TextColor.parse("#e21e1e");

    private static final DataResult<TextColor> DEFAULT_TOOLTIP_LABEL_COLOR = TextColor.parse("#3463e6");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_VALUE_COLOR = TextColor.parse("#d1d8eb");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_FORM_COLOR = TextColor.parse("#e6e354");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_CURRENT_EXP_COLOR = TextColor.parse("#6ec95c");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_TARGET_EXP_COLOR = TextColor.parse("#37af51");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_HIDDEN_ABILITY_COLOR = TextColor.parse("#31d6e2");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_STAT_UP_COLOR = TextColor.parse("#60d651");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_STAT_DOWN_COLOR = TextColor.parse("#d14040");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_HEALTH_COLOR = TextColor.parse("#4f9c45");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_ATTACK_COLOR = TextColor.parse("#b33f3f");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_DEFENSE_COLOR = TextColor.parse("#d1842c");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_SPATTACK_COLOR = TextColor.parse("#d140ca");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_SPDEFENSE_COLOR = TextColor.parse("#ddda36");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_SPEED_COLOR = TextColor.parse("#3bd8dd");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_MALE_COLOR = TextColor.parse("#0984f7");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_FEMALE_COLOR = TextColor.parse("#e16ef0");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_GENDERLESS_COLOR = TextColor.parse("#bdbdbd");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_TRUE_COLOR = TextColor.parse("#40d440");
    private static final DataResult<TextColor> DEFAULT_TOOLTIP_FALSE_COLOR = TextColor.parse("#d12828");

    private static final DataResult<TextColor> DEFAULT_PARTYCHECK_BUTTON_TITLE_COLOR = TextColor.parse("#1f22b8");
    private static final DataResult<TextColor> DEFAULT_PARTYCHECK_OPTION_NAME_COLOR = TextColor.parse("#3463e6");
    private static final DataResult<TextColor> DEFAULT_PARTYCHECK_OPTION_SPLITTER_COLOR = TextColor.parse("#3463e6");
    private static final DataResult<TextColor> DEFAULT_PARTYCHECK_OPTION_DESCRIPTION_COLOR = TextColor.parse("#d1d8eb");
    private static final DataResult<TextColor> DEFAULT_PARTYCHECK_FOOTER_COLOR = TextColor.parse("#d1d8eb");

    private static TextColor typeColorNormal;
    private static TextColor typeColorFire;
    private static TextColor typeColorWater;
    private static TextColor typeColorElectric;
    private static TextColor typeColorGrass;
    private static TextColor typeColorIce;
    private static TextColor typeColorFighting;
    private static TextColor typeColorPoison;
    private static TextColor typeColorGround;
    private static TextColor typeColorFlying;
    private static TextColor typeColorPsychic;
    private static TextColor typeColorBug;
    private static TextColor typeColorRock;
    private static TextColor typeColorGhost;
    private static TextColor typeColorDragon;
    private static TextColor typeColorDark;
    private static TextColor typeColorSteel;
    private static TextColor typeColorFairy;

    private static TextColor commandPrefixColor;
    private static TextColor commandValueColor;
    private static TextColor commandPlayerColor;

    private static TextColor hoverableBracketColor;
    private static TextColor hoverableBracketShinyColor;
    private static TextColor hoverableBracketErrorColor;
    private static TextColor hoverableTextColor;
    private static TextColor hoverableTextShinyColor;
    private static TextColor hoverableTextErrorColor;

    private static TextColor tooltipLabelColor;
    private static TextColor tooltipValueColor;
    private static TextColor tooltipFormColor;
    private static TextColor tooltipCurrentExperienceColor;
    private static TextColor tooltipTargetExperienceColor;
    private static TextColor tooltipHiddenAbilityColor;
    private static TextColor tooltipStatUpColor;
    private static TextColor tooltipStatDownColor;
    private static TextColor tooltipHealthColor;
    private static TextColor tooltipAttackColor;
    private static TextColor tooltipDefenseColor;
    private static TextColor tooltipSpAttackColor;
    private static TextColor tooltipSpDefenseColor;
    private static TextColor tooltipSpeedColor;
    private static TextColor tooltipMaleColor;
    private static TextColor tooltipFemaleColor;
    private static TextColor tooltipGenderlessColor;
    private static TextColor tooltipTrueColor;
    private static TextColor tooltipFalseColor;

    private static TextColor partyCheckButtonTitleColor;
    private static TextColor partyCheckOptionNameColor;
    private static TextColor partyCheckOptionSplitterColor;
    private static TextColor partyCheckOptionDescriptionColor;
    private static TextColor partyCheckFooterColor;

    public static void setDefaults()
    {
        typeColorNormal = TypeColor.NORMAL.getColor();
        typeColorFire = TypeColor.FIRE.getColor();
        typeColorWater = TypeColor.WATER.getColor();
        typeColorElectric = TypeColor.ELECTRIC.getColor();
        typeColorGrass = TypeColor.GRASS.getColor();
        typeColorIce = TypeColor.ICE.getColor();
        typeColorFighting = TypeColor.FIGHTING.getColor();
        typeColorPoison = TypeColor.POISON.getColor();
        typeColorGround = TypeColor.GROUND.getColor();
        typeColorFlying = TypeColor.FLYING.getColor();
        typeColorPsychic = TypeColor.PSYCHIC.getColor();
        typeColorBug = TypeColor.BUG.getColor();
        typeColorRock = TypeColor.ROCK.getColor();
        typeColorGhost = TypeColor.GHOST.getColor();
        typeColorDragon = TypeColor.DRAGON.getColor();
        typeColorDark = TypeColor.DARK.getColor();
        typeColorSteel = TypeColor.STEEL.getColor();
        typeColorFairy = TypeColor.FAIRY.getColor();

        try 
        {
            commandPrefixColor = DEFAULT_COMMAND_PREFIX_COLOR.getOrThrow();
            commandValueColor = DEFAULT_COMMAND_VALUE_COLOR.getOrThrow();
            commandPlayerColor = DEFAULT_COMMAND_PLAYER_COLOR.getOrThrow();

            hoverableBracketColor = DEFAULT_HOVERABLE_BRACKET_COLOR.getOrThrow();
            hoverableBracketShinyColor = DEFAULT_HOVERABLE_SHINY_BRACKET_COLOR.getOrThrow();
            hoverableBracketErrorColor = DEFAULT_HOVERABLE_ERROR_BRACKET_COLOR.getOrThrow();
            hoverableTextColor = DEFAULT_HOVERABLE_TEXT_COLOR.getOrThrow();
            hoverableTextShinyColor = DEFAULT_HOVERABLE_SHINY_TEXT_COLOR.getOrThrow();
            hoverableTextErrorColor = DEFAULT_HOVERABLE_ERROR_TEXT_COLOR.getOrThrow();

            tooltipLabelColor = DEFAULT_TOOLTIP_LABEL_COLOR.getOrThrow();
            tooltipValueColor = DEFAULT_TOOLTIP_VALUE_COLOR.getOrThrow();
            tooltipFormColor = DEFAULT_TOOLTIP_FORM_COLOR.getOrThrow();
            tooltipCurrentExperienceColor = DEFAULT_TOOLTIP_CURRENT_EXP_COLOR.getOrThrow();
            tooltipTargetExperienceColor = DEFAULT_TOOLTIP_TARGET_EXP_COLOR.getOrThrow();
            tooltipHiddenAbilityColor = DEFAULT_TOOLTIP_HIDDEN_ABILITY_COLOR.getOrThrow();
            tooltipStatUpColor = DEFAULT_TOOLTIP_STAT_UP_COLOR.getOrThrow();
            tooltipStatDownColor = DEFAULT_TOOLTIP_STAT_DOWN_COLOR.getOrThrow();
            tooltipHealthColor = DEFAULT_TOOLTIP_HEALTH_COLOR.getOrThrow();
            tooltipAttackColor = DEFAULT_TOOLTIP_ATTACK_COLOR.getOrThrow();
            tooltipDefenseColor = DEFAULT_TOOLTIP_DEFENSE_COLOR.getOrThrow();
            tooltipSpAttackColor = DEFAULT_TOOLTIP_SPATTACK_COLOR.getOrThrow();
            tooltipSpDefenseColor = DEFAULT_TOOLTIP_SPDEFENSE_COLOR.getOrThrow();
            tooltipSpeedColor = DEFAULT_TOOLTIP_SPEED_COLOR.getOrThrow();
            tooltipMaleColor = DEFAULT_TOOLTIP_MALE_COLOR.getOrThrow();
            tooltipFemaleColor = DEFAULT_TOOLTIP_FEMALE_COLOR.getOrThrow();
            tooltipGenderlessColor = DEFAULT_TOOLTIP_GENDERLESS_COLOR.getOrThrow();
            tooltipTrueColor = DEFAULT_TOOLTIP_TRUE_COLOR.getOrThrow();
            tooltipFalseColor = DEFAULT_TOOLTIP_FALSE_COLOR.getOrThrow();

            partyCheckButtonTitleColor = DEFAULT_PARTYCHECK_BUTTON_TITLE_COLOR.getOrThrow();
            partyCheckOptionNameColor = DEFAULT_PARTYCHECK_OPTION_NAME_COLOR.getOrThrow();
            partyCheckOptionSplitterColor = DEFAULT_PARTYCHECK_OPTION_SPLITTER_COLOR.getOrThrow();
            partyCheckOptionDescriptionColor = DEFAULT_PARTYCHECK_OPTION_DESCRIPTION_COLOR.getOrThrow();
            partyCheckFooterColor = DEFAULT_PARTYCHECK_FOOTER_COLOR.getOrThrow();
        }
        catch (IllegalStateException e) 
        {
            ModLogger.error("Failed to load default chat colors: %s".formatted(e.getMessage()), e);
        }
    }

    public static TextColor getTypeColorNormal() { return typeColorNormal; }
    public static TextColor getTypeColorFire() { return typeColorFire; }
    public static TextColor getTypeColorWater() { return typeColorWater; }
    public static TextColor getTypeColorElectric() { return typeColorElectric; }
    public static TextColor getTypeColorGrass() { return typeColorGrass; }
    public static TextColor getTypeColorIce() { return typeColorIce; }
    public static TextColor getTypeColorFighting() { return typeColorFighting; }
    public static TextColor getTypeColorPoison() { return typeColorPoison; }
    public static TextColor getTypeColorGround() { return typeColorGround; }
    public static TextColor getTypeColorFlying() { return typeColorFlying; }
    public static TextColor getTypeColorPsychic() { return typeColorPsychic; }
    public static TextColor getTypeColorBug() { return typeColorBug; }
    public static TextColor getTypeColorRock() { return typeColorRock; }
    public static TextColor getTypeColorGhost() { return typeColorGhost; }
    public static TextColor getTypeColorDragon() { return typeColorDragon; }
    public static TextColor getTypeColorDark() { return typeColorDark; }
    public static TextColor getTypeColorSteel() { return typeColorSteel; }
    public static TextColor getTypeColorFairy() { return typeColorFairy; }
    public static TextColor getCommandPrefixColor() { return commandPrefixColor; }
    public static TextColor getCommandValueColor() { return commandValueColor; }
    public static TextColor getCommandPlayerColor() { return commandPlayerColor; }
    public static TextColor getHoverableBracketColor() { return hoverableBracketColor; }
    public static TextColor getHoverableBracketShinyColor() { return hoverableBracketShinyColor; }
    public static TextColor getHoverableBracketErrorColor() { return hoverableBracketErrorColor; }
    public static TextColor getHoverableTextColor() { return hoverableTextColor; }
    public static TextColor getHoverableTextShinyColor() { return hoverableTextShinyColor; }
    public static TextColor getHoverableTextErrorColor() { return hoverableTextErrorColor; }
    public static TextColor getTooltipLabelColor() { return tooltipLabelColor; }
    public static TextColor getTooltipValueColor() { return tooltipValueColor; }
    public static TextColor getTooltipFormColor() { return tooltipFormColor; }
    public static TextColor getTooltipCurrentExperienceColor() { return tooltipCurrentExperienceColor; }
    public static TextColor getTooltipTargetExperienceColor() { return tooltipTargetExperienceColor; }
    public static TextColor getTooltipHiddenAbilityColor() { return tooltipHiddenAbilityColor; }
    public static TextColor getTooltipStatUpColor() { return tooltipStatUpColor; }
    public static TextColor getTooltipStatDownColor() { return tooltipStatDownColor; }
    public static TextColor getTooltipHealthColor() { return tooltipHealthColor; }
    public static TextColor getTooltipAttackColor() { return tooltipAttackColor; }
    public static TextColor getTooltipDefenseColor() { return tooltipDefenseColor; }
    public static TextColor getTooltipSpAttackColor() { return tooltipSpAttackColor; }
    public static TextColor getTooltipSpDefenseColor() { return tooltipSpDefenseColor; }
    public static TextColor getTooltipSpeedColor() { return tooltipSpeedColor; }
    public static TextColor getTooltipMaleColor() { return tooltipMaleColor; }
    public static TextColor getTooltipFemaleColor() { return tooltipFemaleColor; }
    public static TextColor getTooltipGenderlessColor() { return tooltipGenderlessColor; }
    public static TextColor getTooltipTrueColor() { return tooltipTrueColor; }
    public static TextColor getTooltipFalseColor() { return tooltipFalseColor; }
    public static TextColor getPartyCheckButtonTitleColor() { return partyCheckButtonTitleColor; }
    public static TextColor getPartyCheckOptionNameColor() { return partyCheckOptionNameColor; }
    public static TextColor getPartyCheckOptionSplitterColor() { return partyCheckOptionSplitterColor; }
    public static TextColor getPartyCheckOptionDescriptionColor() { return partyCheckOptionDescriptionColor; }
    public static TextColor getPartyCheckFooterColorColor() { return partyCheckFooterColor; }

    protected static void setTypeColorNormal(TextColor color) { typeColorNormal = color; }
    protected static void setTypeColorFire(TextColor color) { typeColorFire = color; }
    protected static void setTypeColorWater(TextColor color) { typeColorWater = color; }
    protected static void setTypeColorElectric(TextColor color) { typeColorElectric = color; }
    protected static void setTypeColorGrass(TextColor color) { typeColorGrass = color; }
    protected static void setTypeColorIce(TextColor color) { typeColorIce = color; }
    protected static void setTypeColorFighting(TextColor color) { typeColorFighting = color; }
    protected static void setTypeColorPoison(TextColor color) { typeColorPoison = color; }
    protected static void setTypeColorGround(TextColor color) { typeColorGround = color; }
    protected static void setTypeColorFlying(TextColor color) { typeColorFlying = color; }
    protected static void setTypeColorPsychic(TextColor color) { typeColorPsychic = color; }
    protected static void setTypeColorBug(TextColor color) { typeColorBug = color; }
    protected static void setTypeColorRock(TextColor color) { typeColorRock = color; }
    protected static void setTypeColorGhost(TextColor color) { typeColorGhost = color; }
    protected static void setTypeColorDragon(TextColor color) { typeColorDragon = color; }
    protected static void setTypeColorDark(TextColor color) { typeColorDark = color; }
    protected static void setTypeColorSteel(TextColor color) { typeColorSteel = color; }
    protected static void setTypeColorFairy(TextColor color) { typeColorFairy = color; }
    protected static void setCommandPrefixColor(TextColor color) { commandPrefixColor = color; }
    protected static void setCommandValueColor(TextColor color) { commandValueColor = color; }
    protected static void setCommandPlayerColor(TextColor color) { commandPlayerColor = color; }
    protected static void setHoverableBracketColor(TextColor color) { hoverableBracketColor = color; }
    protected static void setHoverableBracketShinyColor(TextColor color) { hoverableBracketShinyColor = color; }
    protected static void setHoverableBracketErrorColor(TextColor color) { hoverableBracketErrorColor = color; }
    protected static void setHoverableTextColor(TextColor color) { hoverableTextColor = color; }
    protected static void setHoverableTextShinyColor(TextColor color) { hoverableTextShinyColor = color; }
    protected static void setHoverableTextErrorColor(TextColor color) { hoverableTextErrorColor = color; }
    protected static void setTooltipLabelColor(TextColor color) { tooltipLabelColor = color; }
    protected static void setTooltipValueColor(TextColor color) { tooltipValueColor = color; }
    protected static void setTooltipFormColor(TextColor color) { tooltipFormColor = color; }
    protected static void setTooltipCurrentExperienceColor(TextColor color) { tooltipCurrentExperienceColor = color; }
    protected static void setTooltipTargetExperienceColor(TextColor color) { tooltipTargetExperienceColor = color; }
    protected static void setTooltipHiddenAbilityColor(TextColor color) { tooltipHiddenAbilityColor = color; }
    protected static void setTooltipStatUpColor(TextColor color) { tooltipStatUpColor = color; }
    protected static void setTooltipStatDownColor(TextColor color) { tooltipStatDownColor = color; }
    protected static void setTooltipHealthColor(TextColor color) { tooltipHealthColor = color; }
    protected static void setTooltipAttackColor(TextColor color) { tooltipAttackColor = color; }
    protected static void setTooltipDefenseColor(TextColor color) { tooltipDefenseColor = color; }
    protected static void setTooltipSpAttackColor(TextColor color) { tooltipSpAttackColor = color; }
    protected static void setTooltipSpDefenseColor(TextColor color) { tooltipSpDefenseColor = color; }
    protected static void setTooltipSpeedColor(TextColor color) { tooltipSpeedColor = color; }
    protected static void setTooltipMaleColor(TextColor color) { tooltipMaleColor = color; }
    protected static void setTooltipFemaleColor(TextColor color) { tooltipFemaleColor = color; }
    protected static void setTooltipGenderlessColor(TextColor color) { tooltipGenderlessColor = color; }
    protected static void setTooltipTrueColor(TextColor color) { tooltipTrueColor = color; }
    protected static void setTooltipFalseColor(TextColor color) { tooltipFalseColor = color; }
    protected static void setPartyCheckButtonTitleColor(TextColor color) { partyCheckButtonTitleColor = color; }
    protected static void setPartyCheckOptionNameColor(TextColor color) { partyCheckOptionNameColor = color; }
    protected static void setPartyCheckOptionSplitterColor(TextColor color) { partyCheckOptionSplitterColor = color; }
    protected static void setPartyCheckOptionDescriptionColor(TextColor color) { partyCheckOptionDescriptionColor = color; }
    protected static void setPartyCheckFooterColorColor(TextColor color) { partyCheckFooterColor = color; }
}
