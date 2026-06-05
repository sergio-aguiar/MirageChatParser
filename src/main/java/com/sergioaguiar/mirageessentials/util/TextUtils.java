package com.sergioaguiar.mirageessentials.util;

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
import com.sergioaguiar.mirageessentials.MirageEssentials;
import com.sergioaguiar.mirageessentials.config.antiafk.colors.AntiAFKColors;
import com.sergioaguiar.mirageessentials.config.antiafk.settings.AntiAFKSettings;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspects;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors;
import com.sergioaguiar.mirageessentials.config.chatparser.colors.ChatColors.TypeColor;
import com.sergioaguiar.mirageessentials.config.chatparser.settings.ChatSettings;
import com.sergioaguiar.mirageessentials.config.chatparser.sizes.ChatSizes;
import com.sergioaguiar.mirageessentials.config.chatparser.strings.ChatStrings;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager;
import com.sergioaguiar.mirageessentials.manager.AntiAFKManager.KickReason;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;

public class TextUtils 
{
    public static class CustomTextBuilder
    {
        private MutableText textObject;

        public CustomTextBuilder()
        {
            textObject = Text.literal("")
                .setStyle
                (
                    Style.EMPTY
                        .withItalic(false)
                );
        }

        public void append(String textContent, TextColor textColor)
        {
            append(textContent, textColor, false, false);
        }

        public void append(String textContent, TextColor textColor, boolean isBold, boolean isItalic)
        {
            textObject = textObject.append
            (
                Text.literal(textContent)
                    .setStyle
                    (
                        Style.EMPTY
                            .withColor(textColor)
                            .withBold(isBold)
                            .withItalic(isItalic)
                    )
            );
        }

        public void append(Text text)
        {
            textObject = textObject.append(text);
        }

        public void setHoverEvent(HoverEvent hoverEvent)
        {
            textObject = textObject.setStyle(textObject.getStyle().withHoverEvent(hoverEvent));
        }

        public MutableText getText()
        {
            return textObject;
        }
    }

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

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            String.format("%.2f%% (", percent),
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(hp),
            ChatColors.getTooltipHealthColor(),
            hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.HP) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(atk),
            ChatColors.getTooltipAttackColor(),
            hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.ATTACK) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(def),
            ChatColors.getTooltipDefenseColor(),
            hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spa),
            ChatColors.getTooltipSpAttackColor(),
            hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.SPECIAL_ATTACK) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spd),
            ChatColors.getTooltipSpDefenseColor(),
            hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.SPECIAL_DEFENCE) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spe),
            ChatColors.getTooltipSpeedColor(),
            hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldBoldHyperTrainingValues(),
            hyperTrainedStats.contains(Stats.SPEED) && ChatSettings.shouldItalicHyperTrainingValues()
        );

        textBuilder.append
        (
            ")",
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
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

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            String.format("%.2f%% (", percent),
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(hp),
            ChatColors.getTooltipHealthColor()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(atk),
            ChatColors.getTooltipAttackColor()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(def),
            ChatColors.getTooltipDefenseColor()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spa),
            ChatColors.getTooltipSpAttackColor()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spd),
            ChatColors.getTooltipSpDefenseColor()
        );

        textBuilder.append
        (
            "/",
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(spe),
            ChatColors.getTooltipSpeedColor()
        );

        textBuilder.append
        (
            ")",
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
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
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getSpeciesString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            pokemon.getSpecies().getName(),
            ChatColors.getTooltipValueColor()
        );

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
            textBuilder.append
            (
                " (",
                ChatColors.getTooltipValueColor()
            );

            textBuilder.append
            (
                toTitleCaseWithDelimiters(formName),
                ChatColors.getTooltipFormColor()
            );

            textBuilder.append
            (
                ")",
                ChatColors.getTooltipValueColor()
            );
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

                textBuilder.append
                (
                    " (",
                    ChatColors.getTooltipValueColor()
                );

                textBuilder.append
                (
                    toTitleCaseWithDelimiters(featureValue),
                    ChatColors.getTooltipFormColor()
                );

                textBuilder.append
                (
                    ")",
                    ChatColors.getTooltipValueColor()
                );
            }
        }

        if (ChatAspects.getDisplayedAspectsCount() > 0)
        {
            for (String aspect : aspects)
            {
                if (ChatAspects.shouldDisplayAspect(aspect))
                {
                    textBuilder.append
                    (
                        " (",
                        ChatColors.getTooltipValueColor()
                    );

                    textBuilder.append
                    (
                        ChatAspects.getAspectFriendlyName(aspect),
                        ChatColors.getTooltipFormColor()
                    );

                    textBuilder.append
                    (
                        ")",
                        ChatColors.getTooltipValueColor()
                    );
                }
            }
        }

        return textBuilder.getText(); 
    }

    public static Text coloredLevelLine(int level, int currentExperience, int targetExperience)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getLevelString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            String.valueOf(level),
            ChatColors.getTooltipValueColor()
        );

        if (level != 100)
        {
            textBuilder.append
            (
                " (",
                ChatColors.getTooltipValueColor()
            );

            textBuilder.append
            (
                String.valueOf(currentExperience),
                ChatColors.getTooltipCurrentExperienceColor()
            );

            textBuilder.append
            (
                "/",
                ChatColors.getTooltipValueColor()
            );

            textBuilder.append
            (
                String.valueOf(targetExperience),
                ChatColors.getTooltipTargetExperienceColor()
            );

            textBuilder.append
            (
                ")",
                ChatColors.getTooltipValueColor()
            );
        }

        return textBuilder.getText();
    }

    public static Text coloredMonotypeLine(ElementalType type)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getTypeString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            String.valueOf(toTitleCase(type.getName())),
            TypeColor.fromTypeName(type.getName())
        );

        return textBuilder.getText();
    }

    public static Text coloredDualtypeLine(ElementalType type1, ElementalType type2)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getTypesString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            String.valueOf(toTitleCase(type1.getName())),
            TypeColor.fromTypeName(type1.getName())
        );

        textBuilder.append
        (
            ChatStrings.getTypeSeparatorString(),
            ChatColors.getTooltipValueColor()
        );

        textBuilder.append
        (
            String.valueOf(toTitleCase(type2.getName())),
            TypeColor.fromTypeName(type2.getName())
        );

        return textBuilder.getText();
    }

    public static Text coloredAbilitiesLine(String abilityName, boolean isHidden, boolean isClosedSheet)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getAbilityString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            isClosedSheet ? ChatStrings.getClosedSheetString() : abilityName,
            ChatColors.getTooltipValueColor()
        );

        if (!isClosedSheet && isHidden)
        {
            textBuilder.append
            (
                " (",
                ChatColors.getTooltipValueColor()
            );

            textBuilder.append
            (
                ChatStrings.getHiddenAbilityString(),
                ChatColors.getTooltipHiddenAbilityColor()
            );

            textBuilder.append
            (
                ")",
                ChatColors.getTooltipValueColor()
            );
        }
        
        return textBuilder.getText();
    }

    public static Text coloredNatureLine(Nature nature, Nature natureEffective, boolean isClosedSheet) 
    {
        boolean isMinted = !nature.getDisplayName().toString().equals(natureEffective.getDisplayName().toString());

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            isMinted ? ChatStrings.getNatureMintedString() : ChatStrings.getNatureString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            Text.translatable(isClosedSheet ? ChatStrings.getClosedSheetString() : natureEffective.getDisplayName().toString())
                .setStyle(Style.EMPTY.withColor(ChatColors.getTooltipValueColor()))
        );

        if (!isClosedSheet && natureEffective.getIncreasedStat() != null && natureEffective.getDecreasedStat() != null)
        {
            textBuilder.append
            (
                " (",
                ChatColors.getTooltipLabelColor()
            );

            textBuilder.append
            (
                ChatStrings.getStatIncreaseString(),
                ChatColors.getTooltipStatUpColor()
            );

            textBuilder.append
            (
                formatStatName(natureEffective.getIncreasedStat().toString()),
                ChatColors.getTooltipStatUpColor()
            );

            textBuilder.append
            (
                "/",
                ChatColors.getTooltipLabelColor()
            );

            textBuilder.append
            (
                ChatStrings.getStatDecreaseString(),
                ChatColors.getTooltipStatDownColor()
            );

            textBuilder.append
            (
                formatStatName(natureEffective.getDecreasedStat().toString()),
                ChatColors.getTooltipStatDownColor()
            );

            textBuilder.append
            (
                ")",
                ChatColors.getTooltipLabelColor()
            );
        }

        return textBuilder.getText();
    }

    public static Text coloredIVsLine(IVs ivs, boolean isClosedSheet)
    {
        Set<Stats> hyperTrainedStats = CobblemonUtils.getHyperTrainedStats(ivs);

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            hyperTrainedStats.isEmpty() || isClosedSheet ? ChatStrings.getIVsString() : ChatStrings.getIVsHyperTrainedString(),
            ChatColors.getTooltipLabelColor()
        );

        if (isClosedSheet)
        {
            textBuilder.append
            (
                ChatStrings.getClosedSheetString(),
                ChatColors.getTooltipValueColor()
            );
        } 
        else
        {
            textBuilder.append
            (
                hyperTrainedStats.isEmpty() 
                    ? getFormattedIVs(ivs, hyperTrainedStats, false)
                    : getFormattedIVs(ivs, hyperTrainedStats, true)
            );
        }

        return textBuilder.getText();
    }

    public static Text coloredEVsLine(EVs evs, boolean isClosedSheet)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getEVsString(),
            ChatColors.getTooltipLabelColor()
        );

        if (isClosedSheet)
        {
            textBuilder.append
            (
                ChatStrings.getClosedSheetString(),
                ChatColors.getTooltipValueColor()
            );
        }
        else
        {
            textBuilder.append
            (
                getFormattedEVs(evs)
            );
        }

        return textBuilder.getText();
    }

    public static Text coloredMovesLine(Pokemon pokemon, List<Move> moves, boolean isClosedSheet) 
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getMovesString(),
            ChatColors.getTooltipLabelColor()
        );

        if (isClosedSheet)
        {
            textBuilder.append
            (
                ChatStrings.getClosedSheetString(),
                ChatColors.getTooltipValueColor()
            );
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

                textBuilder.append
                (
                    moveName,
                    typeColor
                );

                if (i < moves.size() - 1) 
                {
                    textBuilder.append
                    (
                        ChatStrings.getMoveSeparatorString(),
                        ChatColors.getTooltipValueColor()
                    );
                }
            }
        }

        return textBuilder.getText();
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

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getGenderString(),
            ChatColors.getTooltipLabelColor()
        );

        if (isClosedSheet)
        {
            textBuilder.append
            (
                ChatStrings.getClosedSheetString(),
                ChatColors.getTooltipValueColor()
            );
        }
        else
        {
            textBuilder.append
            (
                "%s %s".formatted(genderSymbol, toTitleCase(gender.name())),
                genderColor
            );
        }

        return textBuilder.getText();
    }

    public static Text coloredFriendshipLine(int happiness, boolean isClosedSheet)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getFriendshipString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            isClosedSheet ? ChatStrings.getClosedSheetString() : Integer.toString(happiness),
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
    }

    public static Text coloredHeldItemLine(ItemStack heldItem, boolean isClosedSheet)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getHeldItemString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            isClosedSheet 
                ? ChatStrings.getClosedSheetString() 
                : getItemName(heldItem),
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
    }

    public static Text coloredCaughtBallLine(PokeBall caughtBall)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getCaughtBallString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            caughtBall.item.getName().getString(),
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
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

        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getSizeString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            ChatSizes.getSizefromScale(scaleModifier).toString(),
            sizeColor
        );

        return textBuilder.getText();
    }

    public static Text coloredEggGroupsLine(Set<EggGroup> eggGroups)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getEggGroupsString(),
            ChatColors.getTooltipLabelColor()
        );

        int i = 0;
        for (EggGroup eggGroup : eggGroups)
        {
            textBuilder.append
            (
                toTitleCase(eggGroup.name().replace("_", " ")),
                ChatColors.getTooltipValueColor()
            );

            if (i < eggGroups.size() - 1) 
            {
                textBuilder.append
                (
                    ChatStrings.getEggGroupsSeparatorString(),
                    ChatColors.getTooltipValueColor()
                );
            }
            i++;
        }

        return textBuilder.getText();
    }

    public static Text coloredNeuterLine(Pokemon pokemon, boolean isNeutered)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getNeuteredString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            isNeutered ? ChatStrings.getTrueString() : ChatStrings.getFalseString(),
            isNeutered ? ChatColors.getTooltipTrueColor() : ChatColors.getTooltipFalseColor()
        );

        return textBuilder.getText();
    }

    public static Text coloredOTLine(String playerName)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            ChatStrings.getOriginalTrainerString(),
            ChatColors.getTooltipLabelColor()
        );

        textBuilder.append
        (
            playerName,
            ChatColors.getTooltipValueColor()
        );

        return textBuilder.getText();
    }

    public static MutableText gradientBetweenTypes(String text, ElementalType type1, ElementalType type2)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        int length = text.length();
        if (length == 0) return textBuilder.getText();

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

            textBuilder.append
            (
                String.valueOf(text.charAt(i)),
                blended
            );
        }

        return textBuilder.getText();
    }

    public static MutableText hoverableText(String speciesName, Text tooltip, boolean isShiny)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "[",
            isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor()
        );

        textBuilder.append
        (
            "%s%s".formatted(isShiny ? ChatStrings.getShinyIconString() : "", speciesName),
            isShiny ? ChatColors.getHoverableTextShinyColor() : ChatColors.getHoverableTextColor()
        );

        textBuilder.append
        (
            "]",
            isShiny ? ChatColors.getHoverableBracketShinyColor() : ChatColors.getHoverableBracketColor()
        );

        textBuilder.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, tooltip));

        return textBuilder.getText();
    }

    public static MutableText errorPlaceholder(String errorMessage)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "[",
            ChatColors.getHoverableBracketErrorColor()
        );

        textBuilder.append
        (
            errorMessage,
            ChatColors.getHoverableTextErrorColor()
        );

        textBuilder.append
        (
            "]",
            ChatColors.getHoverableBracketErrorColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerAFKMessage(String playerName)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            textBuilder.append
            (
                "AFKChecker » ",
                AntiAFKColors.getAFKCheckerPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCheckerTextColor()
            );
        }

        textBuilder.append
        (
            playerName,
            AntiAFKColors.getAFKCheckerPlayerColor()
        );

        textBuilder.append
        (
            " is now AFK.",
            AntiAFKColors.getAFKCheckerTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerNotAFKMessage(ServerPlayerEntity player, String timeAway)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            textBuilder.append
            (
                "AFKChecker » ",
                AntiAFKColors.getAFKCheckerPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCheckerTextColor()
            );
        }

        textBuilder.append
        (
            player.getDisplayName().getString(),
            AntiAFKColors.getAFKCheckerPlayerColor()
        );

        textBuilder.append
        (
            " is no longer AFK.",
            AntiAFKColors.getAFKCheckerTextColor()
        );

        if (AntiAFKSettings.shouldHideAFKTimesWhenBypassingKicks() && LuckPermsUtils.hasPermission(player, "mirageantiafk.bypass.kick"))
        {
            return textBuilder.getText();
        }

        textBuilder.append
        (
            " (Gone for ",
            AntiAFKColors.getAFKCheckerGoneColor()
        );

        textBuilder.append
        (
            String.format("%s", timeAway),
            AntiAFKColors.getAFKCheckerTimeColor()
        );

        textBuilder.append
        (
            ")",
            AntiAFKColors.getAFKCheckerGoneColor()
        );

        return textBuilder.getText();
    }

    public static MutableText provedActivityMessage()
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            textBuilder.append
            (
                "AFKaptcha » ",
                AntiAFKColors.getAFKCaptchaPrefixColor()
            );
        }

        textBuilder.append
        (
            "Thank you for proving you are active!",
            AntiAFKColors.getAFKCaptchaTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerKickMessage(KickReason kickReason)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "AFKChecker\n",
            AntiAFKColors.getKickTitleColor()
        );

        textBuilder.append
        (
            kickReason.getPlayerKickMessage(),
            AntiAFKColors.getKickDescriptionColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerChatKickMessage(String playerName, String kickMessage)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCheckerMessagePrefix())
        {
            textBuilder.append
            (
                "AFKChecker » ",
                AntiAFKColors.getAFKCheckerPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCheckerTextColor()
            );
        }

        textBuilder.append
        (
            playerName,
            AntiAFKColors.getAFKCheckerPlayerColor()
        );

        textBuilder.append
        (
            " has been kicked.\n",
            AntiAFKColors.getAFKCheckerTextColor()
        );

        textBuilder.append
        (
            "Reason: ",
            AntiAFKColors.getKickReasonTitleColor()
        );

        textBuilder.append
        (
            kickMessage,
            AntiAFKColors.getKickReasonDescriptionColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerPermKickMessage(ServerPlayerEntity player, int currentTicks)
    {
        UUID playerUUID = player.getUuid();
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "====================\n",
            AntiAFKColors.getKickInfoBorderColor()
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Is Marked as AFK:",
                " %b ".formatted(AntiAFKManager.isPlayerAFK(player)),
                "\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Is in a Vehicle:",
                " %b ".formatted(player.hasVehicle()),
                "\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Is in a fluid (water/lava):",
                " %b ".formatted(player.isTouchingWater() || player.isInFluid()),
                "\n\n"
            )
        );

        textBuilder.append
        (
            "Last action info:\n",
            AntiAFKColors.getKickInfoTitleColor()
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Position Movement:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastPositionMovement(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Camera Movement:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastCameraMovement(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Chat Message:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastMessageSent(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "CAPTCHA Answer:",
                " %s ".formatted(secondsToReadableTimeString((int) AntiAFKManager.getSecondsSinceLastCaptchaAnswerSent(playerUUID, currentTicks))),
                "seconds ago\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "CAPTCHA Ignored:",
                " %d ".formatted(AntiAFKManager.getIgnoredCaptchas(playerUUID)),
                "times\n"
            )
        );

        textBuilder.append
        (
            playerPermKickMessageLine
            (
                "Suspicious CAPTCHA:",
                " %d ".formatted(AntiAFKManager.getPlayerSuspiciousActionCount(playerUUID)),
                "times"
            )
        );

        textBuilder.append
        (
            "\n====================\n",
            AntiAFKColors.getKickInfoBorderColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerIgnoredForcedCaptcha(String playerName)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            textBuilder.append
            (
                "AFKaptcha » ",
                AntiAFKColors.getAFKCaptchaPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCaptchaTextColor()
            );
        }

        textBuilder.append
        (
            playerName,
            AntiAFKColors.getAFKCaptchaPlayerColor()
        );

        textBuilder.append
        (
            " ignored the CAPTCHA you forced on them.",
            AntiAFKColors.getAFKCaptchaTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerAnsweredForcedCaptcha(String playerName)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            textBuilder.append
            (
                "AFKaptcha » ",
                AntiAFKColors.getAFKCaptchaPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCaptchaTextColor()
            );
        }

        textBuilder.append
        (
            playerName,
            AntiAFKColors.getAFKCaptchaPlayerColor()
        );

        textBuilder.append
        (
            " answered the CAPTCHA you forced on them.",
            AntiAFKColors.getAFKCaptchaTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerPerformedSuspiciousCaptchaAction(String playerName, String susAction)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        if (!AntiAFKSettings.shouldHideAFKCaptchaMessagePrefix())
        {
            textBuilder.append
            (
                "AFKaptcha » ",
                AntiAFKColors.getAFKCaptchaPrefixColor()
            );
        }

        if (!AntiAFKSettings.shouldHidePlayerWordStart())
        {
            textBuilder.append
            (
                "Player ",
                AntiAFKColors.getAFKCaptchaTextColor()
            );
        }

        textBuilder.append
        (
            playerName,
            AntiAFKColors.getAFKCaptchaPlayerColor()
        );

        textBuilder.append
        (
            " performed a suspicious CAPTCHA answer: %s.".formatted(susAction),
            AntiAFKColors.getAFKCaptchaTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText playerPermKickMessageLine(String title, String value, String units)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            title,
            AntiAFKColors.getKickInfoTitleColor()
        );

        textBuilder.append
        (
            value,
            AntiAFKColors.getKickInfoTimeColor()
        );

        textBuilder.append
        (
            units,
            AntiAFKColors.getKickInfoTextColor()
        );

        return textBuilder.getText();
    }

    public static MutableText infoCommandMessage()
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "Info » ",
            ChatColors.getCommandPrefixColor()
        );

        textBuilder.append
        (
            "%s ".formatted(MirageEssentials.MOD_NAME),
            ChatColors.getCommandValueColor()
        );

        textBuilder.append
        (
            "v%s".formatted(FabricLoader.getInstance().getModContainer(MirageEssentials.MOD_ID).orElseThrow().getMetadata().getVersion().getFriendlyString()),
            ChatColors.getCommandPlayerColor()
        );

        textBuilder.append
        (
            " is developed by ",
            ChatColors.getCommandValueColor()
        );

        textBuilder.append
        (
            "pioavenger",
            ChatColors.getCommandPlayerColor()
        );

        return textBuilder.getText();
    }

    public static MutableText afkListCommand(MinecraftServer server)
    {
        CustomTextBuilder textBuilder = new CustomTextBuilder();

        textBuilder.append
        (
            "AFKList » ",
            ChatColors.getCommandPrefixColor()
        );

        textBuilder.append
        (
            "List of currently-AFK players:\n",
            ChatColors.getCommandValueColor()
        );

        List<MutableText> afkPlayerNames = AntiAFKManager.getAFKPlayerNames(server);

        if (afkPlayerNames.isEmpty())
        {
            textBuilder.append
            (
                "None...",
                ChatColors.getCommandValueColor()
            );

            return textBuilder.getText();
        }

        MutableText firstPlayerName = afkPlayerNames.get(0);
        textBuilder.append(firstPlayerName);

        if (afkPlayerNames.size() >= 2)
        {
            for (int i = 1; i < afkPlayerNames.size(); i++)
            {
                MutableText iPlayerName = afkPlayerNames.get(i);

                textBuilder.append
                (
                    ", ",
                    ChatColors.getCommandValueColor()
                );

                textBuilder.append(iPlayerName);
            }
        }

        return textBuilder.getText();
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
        if (seconds > 0 || resultPartList.isEmpty()) resultPartList.add(seconds + " Second" + (seconds != 1 ? "s" : ""));

        return String.join(", ", resultPartList);
    }

    public static Text getItemText(ItemStack stack)
    {
        TextUtils.CustomTextBuilder textBuilder = new TextUtils.CustomTextBuilder();

        textBuilder.append
        (
            "[",
            ChatColors.getHoverableItemBracketColor()
        );

        textBuilder.append
        (
            getItemName(stack),
            ChatColors.getHoverableItemTextColor()
        );

        textBuilder.append
        (
            "]",
            ChatColors.getHoverableItemBracketColor()
        );

        textBuilder.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ITEM, new HoverEvent.ItemStackContent(stack)));

        return textBuilder.getText();
    }

    public static String getItemName(ItemStack stack)
    {
        if (stack == null || stack.isEmpty()) return ChatStrings.getEmptyHeldItemString();

        return ChatSettings.shouldShowOriginalItemNames()
            ? stack.getItem().getName().getString()
            : stack.getName().getString();
    }
}
