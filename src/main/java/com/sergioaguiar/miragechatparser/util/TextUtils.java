package com.sergioaguiar.miragechatparser.util;

import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;

import com.cobblemon.mod.common.api.moves.HiddenPowerUtil;
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
import com.sergioaguiar.miragechatparser.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.miragechatparser.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.miragechatparser.config.chatparser.aspects.ChatAspects;
import com.sergioaguiar.miragechatparser.config.chatparser.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.chatparser.colors.ChatColors.TypeColor;
import com.sergioaguiar.miragechatparser.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.chatparser.sizes.ChatSizes;
import com.sergioaguiar.miragechatparser.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager;
import com.sergioaguiar.miragechatparser.manager.AntiAFKManager.KickReason;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
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

        MutableText text = Text.literal(String.format("%.2f%% (", percent))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()));

        text = text
            .append(Text.literal(String.valueOf(hp))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipHealthColor())
                    .withBold(hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(atk))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipAttackColor())
                    .withBold(hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(def))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipDefenseColor())
                    .withBold(hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spa))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpAttackColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spd))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpDefenseColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spe))
                .setStyle(Style.EMPTY
                    .withColor(ChatColors.getTooltipSpeedColor())
                    .withBold(hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldBoldHyperTrainingValues())
                    .withItalic(hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldItalicHyperTrainingValues())
                ));

        text = text
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return text;
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

        MutableText text = Text.literal(String.format("%.2f%% (", percent))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()));

        text = text
            .append(Text.literal(String.valueOf(hp))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipHealthColor())));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(atk))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipAttackColor())));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(def))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipDefenseColor())));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spa))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpAttackColor())));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spd))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpDefenseColor())));

        text = text
            .append(Text.literal("/")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        text = text
            .append(Text.literal(String.valueOf(spe))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipSpeedColor())));

        text = text
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return text;
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
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
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

        if (!isFormNormal || ChatSettings.shouldShowFormIfNormal())
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("));

            coloredLine = coloredLine
                .append(Text.literal(toTitleCaseWithDelimiters(formName))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())));

            coloredLine = coloredLine
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
                    .append(Text.literal(" ("));

                coloredLine = coloredLine
                    .append(Text.literal(toTitleCaseWithDelimiters(featureValue))
                        .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())));

                coloredLine = coloredLine
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
                        .append(Text.literal(" ("));

                    coloredLine = coloredLine
                        .append(Text.literal(ChatAspects.getAspectFriendlyName(aspect))
                            .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipFormColor())));

                    coloredLine = coloredLine
                        .append(Text.literal(")"));
                }
            }
        }

        return coloredLine; 
    }

    public static Text coloredLevelLine(int level, int currentExperience, int targetExperience)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getLevelString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(String.valueOf(level))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (level != 100)
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("));

            coloredLine = coloredLine
                .append(Text.literal(String.valueOf(currentExperience))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipCurrentExperienceColor())));

            coloredLine = coloredLine
                .append(Text.literal("/"));

            coloredLine = coloredLine
                .append(Text.literal(String.valueOf(targetExperience))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipTargetExperienceColor())));

            coloredLine = coloredLine
                .append(Text.literal(")"));
        }

        return coloredLine;
    }

    public static Text coloredMonotypeLine(ElementalType type)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getTypeString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(String.valueOf(toTitleCase(type.getName())))
                .setStyle(Style.EMPTY.withColor(TypeColor.fromTypeName(type.getName()))));

        return coloredLine;
    }

    public static Text coloredDualtypeLine(ElementalType type1, ElementalType type2)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getTypesString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(String.valueOf(toTitleCase(type1.getName())))
                .setStyle(Style.EMPTY.withColor(TypeColor.fromTypeName(type1.getName()))));

        coloredLine = coloredLine
            .append(Text.literal(ChatStrings.getTypeSeparatorString()));

        coloredLine = coloredLine
            .append(Text.literal(String.valueOf(toTitleCase(type2.getName())))
                .setStyle(Style.EMPTY.withColor(TypeColor.fromTypeName(type2.getName()))));

        return coloredLine;
    }

    public static Text coloredAbilitiesLine(String abilityName, boolean isHidden, boolean isClosedSheet)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getAbilityString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(isClosedSheet ? ChatStrings.getClosedSheetString() : abilityName)
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (!isClosedSheet && isHidden)
        {
            coloredLine = coloredLine
                .append(Text.literal(" ("));

            coloredLine = coloredLine
                .append(Text.literal(ChatStrings.getHiddenAbilityString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipHiddenAbilityColor())));

            coloredLine = coloredLine
                .append(Text.literal(")"));
        }
        
        return coloredLine;
    }

    public static Text coloredNatureLine(Nature nature, Nature natureEffective, boolean isClosedSheet) 
    {
        boolean isMinted = !nature.getDisplayName().toString().equals(natureEffective.getDisplayName().toString());

        MutableText coloredLine = Text.literal(isMinted ? ChatStrings.getNatureMintedString() : ChatStrings.getNatureString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.translatable(isClosedSheet ? ChatStrings.getClosedSheetString() : natureEffective.getDisplayName().toString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        if (!isClosedSheet && natureEffective.getIncreasedStat() != null && natureEffective.getDecreasedStat() != null)
        {
            coloredLine = coloredLine
                .append(Text.literal(" (")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())));

            coloredLine = coloredLine
                .append(Text.literal(ChatStrings.getStatIncreaseString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatUpColor())));

            coloredLine = coloredLine
                .append(Text.literal(formatStatName(natureEffective.getIncreasedStat().toString()))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatUpColor())));

            coloredLine = coloredLine
                .append(Text.literal("/")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())));

            coloredLine = coloredLine
                .append(Text.literal(ChatStrings.getStatDecreaseString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatDownColor())));

            coloredLine = coloredLine
                .append(Text.literal(formatStatName(natureEffective.getDecreasedStat().toString()))
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipStatDownColor())));

            coloredLine = coloredLine
                .append(Text.literal(")")
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor())));
        }

        return coloredLine;
    }

    public static Text coloredIVsLine(IVs ivs, boolean isClosedSheet)
    {
        Set<Stats> hyperTrainedStats = CobblemonUtils.getHyperTrainedStats(ivs);

        MutableText coloredLine = Text.literal(hyperTrainedStats.isEmpty() || isClosedSheet ? ChatStrings.getIVsString() : ChatStrings.getIVsHyperTrainedString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        return coloredLine.append
            (isClosedSheet 
                ? Text.literal(ChatStrings.getClosedSheetString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()))
                : (hyperTrainedStats.isEmpty() 
                    ? getFormattedIVs(ivs, hyperTrainedStats, false)
                    : getFormattedIVs(ivs, hyperTrainedStats, true)
                )
            );
    }

    public static Text coloredEVsLine(EVs evs, boolean isClosedSheet)
    {
        return Text.literal(ChatStrings.getEVsString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false))
            .append
            (isClosedSheet 
                ? Text.literal(ChatStrings.getClosedSheetString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()))
                : getFormattedEVs(evs)
            );
    }

    public static Text coloredMovesLine(Pokemon pokemon, List<Move> moves, boolean isClosedSheet) 
    {
        MutableText coloredLine = Text.literal(ChatStrings.getMovesString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        if (isClosedSheet)
        {
            coloredLine = coloredLine
                .append(Text.literal(ChatStrings.getClosedSheetString())
                    .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
        }
        else
        {
            for (int i = 0; i < moves.size(); i++) 
            {
                Move move = moves.get(i);
                String actualMoveName = move.getDisplayName().getString();
                boolean isHiddenPower = actualMoveName.equalsIgnoreCase("Hidden Power");
                ElementalType moveType = isHiddenPower ? HiddenPowerUtil.getHiddenPowerType(pokemon) : move.getType();
                String moveName = actualMoveName + (isHiddenPower ? " " + moveType.getName() : "");
                TextColor typeColor = TypeColor.fromTypeName(moveType.getName());

                coloredLine = coloredLine
                    .append(Text.literal(moveName)
                        .setStyle(Style.EMPTY.withColor(typeColor)));

                if (i < moves.size() - 1) 
                {
                    coloredLine = coloredLine
                        .append(Text.literal(ChatStrings.getMoveSeparatorString())
                            .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
                }
            }
        }

        return coloredLine;
    }

    public static Text coloredGenderLine(Gender gender, boolean isClosedSheet) 
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

        MutableText coloredLine = Text.literal(ChatStrings.getGenderString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        if (isClosedSheet)
        {
            coloredLine = coloredLine.append(Text.literal(ChatStrings.getClosedSheetString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));
        }
        else
        {
            coloredLine = coloredLine.append(Text.literal("%s %s".formatted(genderSymbol, toTitleCase(gender.name())))
                .setStyle(Style.EMPTY.withColor(genderColor)));
        }

        return coloredLine;
    }

    public static Text coloredFriendshipLine(int happiness, boolean isClosedSheet)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getFriendshipString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(isClosedSheet ? ChatStrings.getClosedSheetString() : Integer.toString(happiness))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return coloredLine;
    }

    public static Text coloredHeldItemLine(ItemStack heldItem, boolean isClosedSheet)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getHeldItemString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(isClosedSheet ? ChatStrings.getClosedSheetString() : (heldItem.isEmpty() ? ChatStrings.getEmptyHeldItemString() : heldItem.getName().getString()))
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return coloredLine;
    }

    public static Text coloredCaughtBallLine(PokeBall caughtBall)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getCaughtBallString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(caughtBall.item.getName().getString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return coloredLine;
    }

    public static Text coloredSizeLine(float scaleModifier)
    {
        TextColor sizeColor;
        try
        {
            sizeColor = TextColor.parse(ChatSizes.getColorfromScale(scaleModifier)).getOrThrow();
        }
        catch (Exception e)
        {
            sizeColor = ChatColors.getTooltipValueColor();
        }

        MutableText coloredLine = Text.literal(ChatStrings.getSizeString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(ChatSizes.getSizefromScale(scaleModifier).toString())
                .setStyle(Style.EMPTY.withColor(sizeColor)));

        return coloredLine;
    }

    public static Text coloredEggGroupsLine(Set<EggGroup> eggGroups)
    {
        MutableText coloredLine =
            Text.literal(ChatStrings.getEggGroupsString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

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
        MutableText coloredLine = Text.literal(ChatStrings.getNeuteredString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(isNeutered ? ChatStrings.getTrueString() : ChatStrings.getFalseString())
                .setStyle(Style.EMPTY.withColor(isNeutered ? ChatColors.getTooltipTrueColor() : ChatColors.getTooltipFalseColor())));

        return coloredLine;
    }

    public static Text coloredOTLine(String playerName)
    {
        MutableText coloredLine = Text.literal(ChatStrings.getOriginalTrainerString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipLabelColor()).withItalic(false));

        coloredLine = coloredLine
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor())));

        return coloredLine;
    }

    public static MutableText gradientBetweenTypes(String text, ElementalType type1, ElementalType type2)
    {
        MutableText result = Text.literal("").setStyle(Style.EMPTY.withItalic(false));

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
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor()));

        hoverableText = hoverableText
            .append(Text.literal("%s%s".formatted(isShiny ? ChatStrings.getShinyIconString() : "", speciesName))
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableTextShinyColor() : ChatColors.getHoverableTextColor())));

        hoverableText = hoverableText
            .append(Text.literal("]")
                .setStyle(Style.EMPTY.withColor(isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor())));

        return hoverableText.setStyle(hoverableText.getStyle().withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip)));
    }

    public static MutableText errorPlaceholder(String errorMessage)
    {
        MutableText errorText = Text.literal("[")
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableBracketErrorColor()));

        errorText = errorText
            .append(Text.literal(errorMessage)
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableTextErrorColor())));

        errorText = errorText
            .append(Text.literal("]")
                .setStyle(Style.EMPTY.withColor(ChatColors.getHoverableBracketErrorColor())));

        return errorText;
    }

    public static MutableText playerAFKMessage(String playerName)
    {
        MutableText afkText = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            afkText = afkText
                .append(Text.literal("AFKChecker » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
        }

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is now AFK.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        return afkText;
    }

    public static MutableText playerNotAFKMessage(ServerPlayerEntity player, String timeAway)
    {
        MutableText afkText = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            afkText = afkText
                .append(Text.literal("AFKChecker » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
        }

        afkText = afkText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(player.getDisplayName().getString())
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

        afkText = afkText
            .append(Text.literal(" is no longer AFK.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        if (AntiAFKSettings.shouldHideAFKTimesWhenBypassingKicks() && LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.kick"))
        {
            return afkText;
        }

        afkText = afkText
            .append(Text.literal(" (Gone for ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerGoneColor())));

        afkText = afkText
            .append(Text.literal("%s".formatted(timeAway))
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        afkText = afkText
            .append(Text.literal(")")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerGoneColor())));

        return afkText;
    }

    public static MutableText provedActivityMessage()
    {
        MutableText captchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            captchaMessage = captchaMessage
                .append(Text.literal("AFKaptcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPrefixColor())));
        }

        captchaMessage = captchaMessage
            .append(Text.literal("Thank you for proving you are active!")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        return captchaMessage;
    }

    public static MutableText playerKickMessage(KickReason kickReason)
    {
        MutableText kickText = Text.literal("AFKChecker\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickTitleColor()));

        kickText = kickText
            .append(Text.literal(kickReason.getPlayerKickMessage())
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickDescriptionColor())));

        return kickText;
    }

    public static MutableText playerChatKickMessage(String playerName, String kickMessage)
    {
        MutableText generalKickText = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            generalKickText = generalKickText
                .append(Text.literal("AFKChecker » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPrefixColor())));
        }

        generalKickText = generalKickText
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        generalKickText = generalKickText
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerPlayerColor())));

        generalKickText = generalKickText
            .append(Text.literal(" has been kicked.\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCheckerTextColor())));

        generalKickText = generalKickText
            .append(Text.literal("Reason: ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickReasonTitleColor())));

        generalKickText = generalKickText
            .append(Text.literal("%s".formatted(kickMessage))
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickReasonDescriptionColor())));

        return generalKickText;
    }

    public static MutableText playerPermKickMessage(ServerPlayerEntity player, int currentTicks)
    {
        UUID playerUUID = player.getUuid();
        MutableText permKickText = Text.literal("").setStyle(Style.EMPTY);

        permKickText = permKickText
            .append(Text.literal("====================\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoBorderColor())));

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Is Marked as AFK:",
                " %b ".formatted(AntiAFKManager.isPlayerAFK(player)),
                "\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Is in a Vehicle:",
                " %b ".formatted(player.hasVehicle()),
                "\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Is in a fluid (water/lava):",
                " %b ".formatted(player.isTouchingWater() || player.isInFluid()),
                "\n\n"
            )
        );

        permKickText = permKickText
            .append(Text.literal("Last action info:\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoTitleColor())));

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Position Movement:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastPositionMovement(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Camera Movement:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastCameraMovement(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Chat Message:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastMessageSent(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "CAPTCHA Answer:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastCaptchaAnswerSent(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "CAPTCHA Ignored:",
                " %d ".formatted(AntiAFKManager.getIgnoredCaptchas(playerUUID)),
                "times\n"
            )
        );

        permKickText.append
        (
            playerPermKickMessageLine
            (
                "Suspicious CAPTCHA:",
                " %d ".formatted(AntiAFKManager.getPlayerSuspiciousActionCount(playerUUID)),
                "times"
            )
        );

        permKickText = permKickText
            .append(Text.literal("\n====================\n")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoBorderColor())));

        return permKickText;
    }

    public static MutableText playerIgnoredForcedCaptcha(String playerName)
    {
        MutableText ignoredCaptchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            ignoredCaptchaMessage = ignoredCaptchaMessage
                .append(Text.literal("AFKaptcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPrefixColor())));
        }

        ignoredCaptchaMessage = ignoredCaptchaMessage
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        ignoredCaptchaMessage = ignoredCaptchaMessage
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPlayerColor())));

        ignoredCaptchaMessage = ignoredCaptchaMessage
            .append(Text.literal(" ignored the CAPTCHA you forced on them.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        return ignoredCaptchaMessage;
    }

    public static MutableText playerAnsweredForcedCaptcha(String playerName)
    {
        MutableText answeredCaptchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            answeredCaptchaMessage = answeredCaptchaMessage
                .append(Text.literal("AFKaptcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPrefixColor())));
        }

        answeredCaptchaMessage = answeredCaptchaMessage
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        answeredCaptchaMessage = answeredCaptchaMessage
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPlayerColor())));

        answeredCaptchaMessage = answeredCaptchaMessage
            .append(Text.literal(" answered the CAPTCHA you forced on them.")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        return answeredCaptchaMessage;
    }

    public static MutableText playerPerformedSuspiciousCaptchaAction(String playerName, String susAction)
    {
        MutableText susCaptchaMessage = Text.literal("").setStyle(Style.EMPTY);

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            susCaptchaMessage = susCaptchaMessage
                .append(Text.literal("AFKaptcha » ")
                    .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPrefixColor())));
        }

        susCaptchaMessage = susCaptchaMessage
            .append(Text.literal("Player ")
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        susCaptchaMessage = susCaptchaMessage
            .append(Text.literal(playerName)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaPlayerColor())));

        susCaptchaMessage = susCaptchaMessage
            .append(Text.literal(" performed a suspicious CAPTCHA answer: %s.".formatted(susAction))
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getAFKCaptchaTextColor())));

        return susCaptchaMessage;
    }

    public static MutableText playerPermKickMessageLine(String title, String value, String units)
    {
        MutableText timeText = Text.literal("").setStyle(Style.EMPTY);

        timeText = timeText
            .append(Text.literal(title)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoTitleColor())));

        timeText = timeText
            .append(Text.literal(value)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoTimeColor())));

        timeText = timeText
            .append(Text.literal(units)
                .setStyle(Style.EMPTY.withColor(AntiAFKColors.getKickInfoTextColor())));

        return timeText;
    }

    public static String secondsToReadableTimeString(int totalSeconds)
    {
        Duration duration = Duration.ofSeconds(totalSeconds);
    
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long seconds = duration.toSecondsPart();

        List<String> resultPartList = new ArrayList<>();

        if (days > 0) resultPartList.add(days + " Day" + (days > 1 ? "s" : ""));
        if (hours > 0) resultPartList.add(hours + " Hour" + (hours > 1 ? "s" : ""));
        if (minutes > 0) resultPartList.add(minutes + " Minute" + (minutes > 1 ? "s" : ""));
        if (seconds > 0 || resultPartList.isEmpty()) resultPartList.add(seconds + " Second" + (seconds > 1 ? "s" : ""));

        return String.join(", ", resultPartList);
    }
}
