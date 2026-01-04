package com.sergioaguiar.miragechatparser.util;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.egg.EggGroup;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.StringSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokeball.PokeBall;
import com.cobblemon.mod.common.pokemon.EVs;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.miragechatparser.config.aspects.ChatAspects;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors.TypeColor;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;

import net.minecraft.item.ItemStack;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class TextUtils 
{
    public static final Pattern PLACEHOLDER_PATTERN = Pattern.compile("\\[(.*?)\\]");
    public static final String IV_AND_EV_STRING_FORMAT = "%.2f%% (%d/%d/%d/%d/%d/%d)";

    public static final String NORMAL_FORM_STRING = "Normal";

    public static Text getFormattedIVs(IVs ivs, Set<Stats> hyperTrainedStats, boolean effective) 
    {
        int hp = effective ? ivs.getEffectiveBattleIV(Stats.HP) : ivs.get(Stats.HP);
        int atk = effective ? ivs.getEffectiveBattleIV(Stats.ATTACK) : ivs.get(Stats.ATTACK);
        int def = effective ? ivs.getEffectiveBattleIV(Stats.DEFENCE) : ivs.get(Stats.DEFENCE);
        int spa = effective ? ivs.getEffectiveBattleIV(Stats.SPECIAL_ATTACK) : ivs.get(Stats.SPECIAL_ATTACK);
        int spd = effective ? ivs.getEffectiveBattleIV(Stats.SPECIAL_DEFENCE) : ivs.get(Stats.SPECIAL_DEFENCE);
        int spe = effective ? ivs.getEffectiveBattleIV(Stats.SPEED) : ivs.get(Stats.SPEED);

        double total = hp + atk + def + spa + spd + spe;
        double percent = (total / 186.0) * 100.0;

        return Text.literal(String.format("%.2f%% (", percent))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()))
            .append(Text.literal(String.valueOf(hp))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipHealthColor())
                    .withBold(hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(atk))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipAttackColor())
                    .withBold(hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(def))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipDefenseColor())
                    .withBold(hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spa))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpAttackColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spd))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpDefenseColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spe))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpeedColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldItalicHyperTrainingValues())
                ))
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static Text getFormattedEVs(EVs evs) 
    {
        int hp = evs.get(Stats.HP);
        int atk = evs.get(Stats.ATTACK);
        int def = evs.get(Stats.DEFENCE);
        int spa = evs.get(Stats.SPECIAL_ATTACK);
        int spd = evs.get(Stats.SPECIAL_DEFENCE);
        int spe = evs.get(Stats.SPEED);

        double total = hp + atk + def + spa + spd + spe;
        double percent = (total / 510.0) * 100.0;

        return Text.literal(String.format("%.2f%% (", percent))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()))
            .append(Text.literal(String.valueOf(hp))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipHealthColor())))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(atk))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipAttackColor())))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(def))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipDefenseColor())))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spa))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpAttackColor())))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spd))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpDefenseColor())))
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())))
            .append(Text.literal(String.valueOf(spe))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpeedColor())))
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static String toTitleCase(String input)
    {
        if (input == null || input.isEmpty()) return input;

        String[] words = input.toLowerCase().split("\\s+");
        StringBuilder sb = new StringBuilder();

        for (String word : words)
        {
            if (!word.isEmpty())
            {
                sb.append(Character.toUpperCase(word.charAt(0)))
                .append(word.substring(1))
                .append(" ");
            }
        }

        return sb.toString().trim();
    }

    public static String toTitleCaseWithDelimiters(String input) {
        if (input == null || input.isEmpty()) return input;

        input = input.replace('-', ' ').replace('_', ' ');
        return toTitleCase(input);
    }

    public static String formatStatName(String stat)
    {
        return switch (stat.toUpperCase()) 
        {
            case "HP" -> ChatStrings.getHealthString();
            case "ATTACK" -> ChatStrings.getAttackString();
            case "DEFENSE", "DEFENCE" -> ChatStrings.getDefenseString();
            case "SPECIAL_ATTACK" -> ChatStrings.getSpecialAttackString();
            case "SPECIAL_DEFENSE", "SPECIAL_DEFENCE" -> ChatStrings.getSpecialDefenseString();
            case "SPEED" -> ChatStrings.getSpeedString();
            default -> toTitleCase(stat);
        };
    }

    public static String fromTranslationKey(String key)
    {
        if (key == null || key.isEmpty()) return key;

        String[] parts = key.split("\\.");
        String lastPart = parts[parts.length - 1];

        lastPart = lastPart.replace('_', ' ').replace('-', ' ');
        return toTitleCase(lastPart);
    }

    public static Text coloredSpeciesLine(Pokemon pokemon, String formName, Set<String> aspects, List<SpeciesFeature> speciesFeatures)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getSpeciesString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(pokemon.getSpecies().getName())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        boolean isFormNormal = formName.equals(NORMAL_FORM_STRING);

        List<StringSpeciesFeature> allowedSpeciesFeatures = new LinkedList<>();

        for (SpeciesFeature feature : speciesFeatures)
        {
            if (feature instanceof StringSpeciesFeature stringSpeciesFeature)
            {
                if (ChatAspects.isSpeciesFeatureIgnored(stringSpeciesFeature.getName())) continue;
                allowedSpeciesFeatures.add(stringSpeciesFeature);
            }
        }

        if (!isFormNormal || ChatSettings.showFormIfNormal())
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("))
                .append(Text.literal(toTitleCaseWithDelimiters(formName))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())))
                .append(Text.literal(")"));
        }

        if (isFormNormal && !allowedSpeciesFeatures.isEmpty())
        {
            for (StringSpeciesFeature stringFeature : allowedSpeciesFeatures)
            {
                if (stringFeature == null) continue;

                String featureKey = stringFeature.getName();
                String featureValue = stringFeature.getValue();

                if (featureKey.equals(ChatAspects.SPECIES_FEATURE_MOOSHTANK_STRING) && featureValue.equals(ChatAspects.SPECIES_FEATURE_MOOSHTANK_FALSE_STRING))
                {
                    continue;
                }
                else if (featureKey.equals(ChatAspects.SPECIES_FEATURE_NETHERITE_COATING_STRING))
                {
                    if (featureValue.equals(ChatAspects.SPECIES_FEATURE_NETHERITE_COATING_NONE_STRING)) continue;
                    else featureValue += ChatAspects.SPECIES_FEATURE_NETHERITE_COATING_APPEND_STRING;
                }
                else if (featureKey.equals(ChatAspects.SPECIES_FEATURE_REGION_BIAS_STRING))
                {
                    featureValue += ChatAspects.SPECIES_FEATURE_REGION_BIAS_APPEND_STRING;
                }
                else if (featureKey.equals(ChatAspects.SPECIES_FEATURE_TREE_STRING) && featureValue.equals(ChatAspects.SPECIES_FEATURE_TREE_NONE_STRING))
                {
                    continue;
                }

                coloredLine = coloredLine
                    .append(Text.literal(" ("))
                    .append(Text.literal(toTitleCaseWithDelimiters(featureValue))
                        .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())))
                    .append(Text.literal(")"));
            }
        }

        if (ChatAspects.getDisplayedAspectsCount() > 0)
        {
            for (String aspect : aspects)
            {
                if (ChatAspects.shouldDisplayAspect(aspect))
                {
                    coloredLine = coloredLine
                        .append(Text.literal(" ("))
                        .append(Text.literal(ChatAspects.getAspectFriendlyName(aspect))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())))
                        .append(Text.literal(")"));
                }
            }
        }

        return coloredLine; 
    }

    public static Text coloredLevelLine(int level, int currentExperience, int targetExperience)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getLevelString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(String.valueOf(level))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (level != 100)
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("))
                .append(Text.literal(String.valueOf(currentExperience))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipCurrentExperienceColor())))
                .append(Text.literal("/"))
                .append(Text.literal(String.valueOf(targetExperience))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipTargetExperienceColor())))
                .append(Text.literal(")"));
        }

        return coloredLine;
    }

    public static Text coloredMonotypeLine(ElementalType type)
    {
        TextColor typeColor = TypeColor.fromTypeName(type.getName());

        return Text.literal(ChatStrings.getTypeString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(String.valueOf(toTitleCase(type.getName())))
                .setStyle(Style.EMPTY.withColor(typeColor)));
    }

    public static Text coloredDualtypeLine(ElementalType type1, ElementalType type2)
    {
        TextColor typeColor1 = TypeColor.fromTypeName(type1.getName());
        TextColor typeColor2 = TypeColor.fromTypeName(type2.getName());

        return Text.literal(ChatStrings.getTypesString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(String.valueOf(toTitleCase(type1.getName())))
                .setStyle(Style.EMPTY.withColor(typeColor1)))
            .append(Text.literal(ChatStrings.getTypeSeparatorString()))
            .append(Text.literal(String.valueOf(toTitleCase(type2.getName())))
                .setStyle(Style.EMPTY.withColor(typeColor2)));
    }

    public static Text coloredAbilitiesLine(String abilityName, boolean isHidden)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getAbilityString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(abilityName)
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (isHidden)
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("))
                .append(Text.literal(ChatStrings.getHiddenAbilityString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipHiddenAbilityColor())))
                .append(Text.literal(")"));
        }
        
        return coloredLine;
    }

    public static Text coloredNatureLine(Nature nature, Nature natureEffective) 
    {
        boolean isMinted = !nature.getDisplayName().toString().equals(natureEffective.getDisplayName().toString());

        MutableText coloredLine = Text.literal(isMinted ? ChatStrings.getNatureMintedString() : ChatStrings.getNatureString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.translatable(natureEffective.getDisplayName().toString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (natureEffective.getIncreasedStat() != null && natureEffective.getDecreasedStat() != null)
        {
            coloredLine = coloredLine
                .append(Text.literal(" (")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())))
                .append(Text.literal(ChatStrings.getStatIncreaseString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatUpColor())))
                .append(Text.literal(formatStatName(natureEffective.getIncreasedStat().toString()))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatUpColor())))
                .append(Text.literal("/")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())))
                .append(Text.literal(ChatStrings.getStatDecreaseString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatDownColor())))
                .append(Text.literal(formatStatName(natureEffective.getDecreasedStat().toString()))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatDownColor())))
                .append(Text.literal(")")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())));
        }

        return coloredLine;
    }

    public static Text coloredIVsLine(IVs ivs)
    {
        Set<Stats> hyperTrainedStats = CobblemonUtils.getHyperTrainedStats(ivs);

        MutableText coloredLine = Text.literal(hyperTrainedStats.isEmpty() ? ChatStrings.getIVsString() : ChatStrings.getIVsHyperTrainedString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()));

        return coloredLine.append(hyperTrainedStats.isEmpty() ? getFormattedIVs(ivs, hyperTrainedStats, false) : getFormattedIVs(ivs, hyperTrainedStats, true));
    }

    public static Text coloredEVsLine(EVs evs)
    {
        return Text.literal(ChatStrings.getEVsString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(getFormattedEVs(evs));
    }

    public static Text coloredMovesLine(List<Move> moves) 
    {
        MutableText coloredLine = Text.literal(ChatStrings.getMovesString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()));

        for (int i = 0; i < moves.size(); i++) 
        {
            Move move = moves.get(i);
            TextColor typeColor = TypeColor.fromTypeName(move.getType().getName());

            coloredLine = coloredLine
                .append(Text.literal(move.getDisplayName().getString())
                    .setStyle(Style.EMPTY.withColor(typeColor)));

            if (i < moves.size() - 1) 
            {
                coloredLine = coloredLine
                    .append(Text.literal(ChatStrings.getMoveSeparatorString())
                        .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
            }
        }

        return coloredLine;
    }

    public static Text coloredGenderLine(Gender gender) 
    {
        TextColor genderColor;
        String genderSymbol;
        switch (gender) 
        {
            case MALE:
                genderColor = ChatColors.getTooltipMaleColor();
                genderSymbol = ChatStrings.getMaleIconString();
                break;
            case FEMALE:
                genderColor = ChatColors.getTooltipFemaleColor();
                genderSymbol = ChatStrings.getFemaleIconString();
                break;
            default:
                genderColor = ChatColors.getTooltipGenderlessColor();
                genderSymbol = ChatStrings.getGenderlessIconString();
        }

        return Text.literal(ChatStrings.getGenderString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal("%s %s".formatted(genderSymbol, toTitleCase(gender.name())))
                .setStyle(Style.EMPTY.withColor(genderColor)));
    }

    public static Text coloredFriendshipLine(int happiness)
    {
        return Text.literal(ChatStrings.getFriendshipString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(Integer.toString(happiness))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static Text coloredHeldItemLine(ItemStack heldItem)
    {
        return Text.literal(ChatStrings.getHeldItemString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(heldItem.isEmpty() ? ChatStrings.getEmptyHeldItemString() : heldItem.getName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static Text coloredCaughtBallLine(PokeBall caughtBall)
    {
        return Text.literal(ChatStrings.getCaughtBallString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(caughtBall.item.getName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static Text coloredSizeLine(float scaleModifier)
    {
        return Text.literal(ChatStrings.getSizeString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(CobblemonUtils.SizeCategory.fromSize(scaleModifier).toString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static Text coloredEggGroupsLine(Set<EggGroup> eggGroups)
    {
        MutableText coloredLine =
            Text.literal(ChatStrings.getEggGroupsString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()));

        int i = 0;
        for (EggGroup eggGroup : eggGroups)
        {
            coloredLine = coloredLine
                .append(Text.literal(toTitleCase(eggGroup.name().replace("_", " ")))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

            if (i < eggGroups.size() - 1) 
            {
                coloredLine = coloredLine
                    .append(Text.literal(ChatStrings.getEggGroupsSeparatorString())
                        .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
            }
            i++;
        }

        return coloredLine;
    }

    public static Text coloredNeuterLine(Pokemon pokemon, boolean isNeutered)
    {
        return Text.literal(ChatStrings.getNeuteredString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(isNeutered ? ChatStrings.getTrueString() : ChatStrings.getFalseString())
                .setStyle(Style.EMPTY.withColor(isNeutered ? ChatColors.getTooltipTrueColor() : ChatColors.getTooltipFalseColor())));
    }

    public static Text coloredOTLine(String playerName)
    {
        return Text.literal(ChatStrings.getOriginalTrainerString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()))
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
    }

    public static MutableText gradientBetweenTypes(String text, ElementalType type1, ElementalType type2)
    {
        MutableText result = Text.literal("");

        int length = text.length();
        if (length == 0) return result;

        TextColor color1 = TypeColor.fromTypeName(type1.getName());
        TextColor color2 = TypeColor.fromTypeName(type2.getName());

        int rgb1 = color1.getRgb();
        int rgb2 = color2.getRgb();

        for (int i = 0; i < length; i++) 
        {
            float t = (float) i / Math.max(1, length - 1);

            int r = (int) ((1 - t) * ((rgb1 >> 16) & 0xFF) + t * ((rgb2 >> 16) & 0xFF));
            int g = (int) ((1 - t) * ((rgb1 >> 8) & 0xFF) + t * ((rgb2 >> 8) & 0xFF));
            int b = (int) ((1 - t) * (rgb1 & 0xFF) + t * (rgb2 & 0xFF));

            int rgb = (r << 16) | (g << 8) | b;
            TextColor blended = TextColor.fromRgb(rgb);

            result = result
                .append(Text.literal(String.valueOf(text.charAt(i)))
                    .setStyle(Style.EMPTY.withColor(blended)));
        }

        return result;
    }

    public static MutableText hoverableText(String speciesName, Text tooltip, boolean isShiny)
    {
        MutableText hoverableText = Text.literal("[")
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor()))
            .append(Text.literal("%s%s".formatted(isShiny ? ChatStrings.getShinyIconString() : "", speciesName))
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableTextShinyColor() : ChatColors.getHoverableTextColor())))
            .append(Text.literal("]")
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor())));

        return hoverableText.setStyle(hoverableText.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip)));
    }

    public static MutableText errorPlaceholder(String errorMessage)
    {
        return Text.literal("[")
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableBracketErrorColor()))
            .append(Text.literal(errorMessage)
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableTextErrorColor())))
            .append(Text.literal("]")
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableBracketErrorColor())));
    }
}
