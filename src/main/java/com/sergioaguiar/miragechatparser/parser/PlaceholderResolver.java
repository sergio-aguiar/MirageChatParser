package com.sergioaguiar.miragechatparser.parser;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.cobblemon.mod.common.Cobblemon;
import com.cobblemon.mod.common.api.moves.Move;
import com.cobblemon.mod.common.api.pokemon.egg.EggGroup;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.api.storage.party.PlayerPartyStore;
import com.cobblemon.mod.common.api.storage.pc.PCBox;
import com.cobblemon.mod.common.api.storage.pc.PCStore;
import com.cobblemon.mod.common.api.types.ElementalType;
import com.cobblemon.mod.common.pokeball.PokeBall;
import com.cobblemon.mod.common.pokemon.EVs;
import com.cobblemon.mod.common.pokemon.Gender;
import com.cobblemon.mod.common.pokemon.IVs;
import com.cobblemon.mod.common.pokemon.Nature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.Species;
import com.sergioaguiar.miragechatparser.config.colors.ChatColors;
import com.sergioaguiar.miragechatparser.config.settings.ChatSettings;
import com.sergioaguiar.miragechatparser.config.strings.ChatStrings;
import com.sergioaguiar.miragechatparser.util.CobblemonUtils;
import com.sergioaguiar.miragechatparser.util.NeoDaycareUtils;
import com.sergioaguiar.miragechatparser.util.TextUtils;

import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;

public class PlaceholderResolver
{
    public static Text resolveText(ServerPlayerEntity player, String input) 
    {
        try 
        {
            String lower = input.toLowerCase().replaceAll("\\s+", "");
            if (lower.startsWith("party:") || lower.startsWith("poke:")) 
            {
                String[] parts = lower.split(":");
                int slot = Integer.parseInt(parts[1]);
                boolean isClosedSheet = parts.length > 2 && parts[2].equals("closed");

                return getPartyPokemon(player, slot, isClosedSheet);
            }
            else if (lower.startsWith("pc:")) 
            {
                String[] parts = lower.split(":");
                int box = Integer.parseInt(parts[1]);
                int slot = Integer.parseInt(parts[2]);
                boolean isClosedSheet = parts.length > 3 && parts[3].equals("closed");

                return getPCPokemonName(player, box, slot, isClosedSheet);
            }
        } 
        catch (Exception e) {}

        return null;
    }

    public static Text getPartyPokemon(ServerPlayerEntity player, int slot, boolean isClosedSheet)
    {
        if (slot < 1 || slot > 6) TextUtils.errorPlaceholder("Invalid Slot");

        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        Pokemon pokemon = party.get(slot - 1);

        if (pokemon == null) return TextUtils.errorPlaceholder("Empty Slot");

        return buildPokemonText(pokemon, isClosedSheet);
    }

    public static ArrayList<Text> getAllPartyPokemon(ServerPlayerEntity player, boolean isClosedSheet)
    {
        PlayerPartyStore party = Cobblemon.INSTANCE.getStorage().getParty(player);
        ArrayList<Text> pokemonInfos = new ArrayList<>();

        for (int i = 0; i < party.size(); i++)
        {
            Pokemon pokemon = party.get(i);
            if (pokemon == null) continue;

            pokemonInfos.add(buildPokemonText(pokemon, isClosedSheet));
        }
        return pokemonInfos;
    }

    public static Text getPCPokemonName(ServerPlayerEntity player, int box, int slot, boolean isClosedSheet)
    {
        PCStore pc = Cobblemon.INSTANCE.getStorage().getPC(player);

        if (box < 1 || box > pc.getBoxes().size()) return TextUtils.errorPlaceholder("Invalid Box");
        if (slot < 1 || slot > 30) return TextUtils.errorPlaceholder("Invalid Slot");

        PCBox boxStorage = pc.getBoxes().get(box - 1);
        Pokemon pokemon = boxStorage.get(slot - 1);

        if (pokemon == null) return TextUtils.errorPlaceholder("Empty Slot");
        
        return buildPokemonText(pokemon, isClosedSheet);
    }

    public static Text buildPokemonText(Pokemon pokemon, boolean isClosedSheet) 
    {
        return TextUtils.hoverableText(pokemon.getSpecies().getName(), buildPokemonTooltip(pokemon, isClosedSheet), pokemon.getShiny());
    }

    public static Text buildPokemonTooltip(Pokemon pokemon, boolean isClosedSheet) 
    {
        Species species = pokemon.getSpecies();
        String nickname = (pokemon.getNickname() == null || pokemon.getNickname().getLiteralString() == null) 
            ? species.getName() : pokemon.getNickname().getLiteralString();
        Set<String> aspects = pokemon.getAspects();
        List<SpeciesFeature> speciesFeatures = pokemon.getFeatures();
        int level = pokemon.getLevel();
        int nextLevelExperience = pokemon.getExperience() + pokemon.getExperienceToNextLevel();
        List<ElementalType> types = StreamSupport.stream(pokemon.getTypes().spliterator(), false).collect(Collectors.toUnmodifiableList());
        Nature nature = pokemon.getNature();
        Nature natureEffective = pokemon.getEffectiveNature();
        IVs ivs = pokemon.getIvs();
        EVs evs = pokemon.getEvs();
        List<Move> moves = pokemon.getMoveSet().getMoves();
        HashSet<EggGroup> eggGroups = species.getEggGroups();
        ItemStack heldItem = pokemon.getHeldItem$common();

        MutableText tooltip = Text.literal("");
        boolean first = true;

        if (ChatSettings.showNickname())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getNicknameText(nickname, types));
        }

        if (ChatSettings.showSpecies())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getSpeciesText(pokemon, aspects, speciesFeatures));
        }

        if (ChatSettings.showLevel())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getLevelText(level, pokemon.getExperience(), nextLevelExperience));
        }

        if (ChatSettings.showTypes())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getTypesText(types));
        }

        if (ChatSettings.showAbilities())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getAbilitiesText(pokemon, isClosedSheet));
        }

        if (ChatSettings.showNature())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getNatureText(nature, natureEffective, isClosedSheet));
        }

        if (ChatSettings.showIVs())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getIVsText(ivs, isClosedSheet));
        }

        if (ChatSettings.showEVs())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getEVsText(evs, isClosedSheet));
        }

        if (ChatSettings.showMoves())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getMovesText(moves, isClosedSheet));
        }

        if (ChatSettings.showGender())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getGenderText(pokemon.getGender(), isClosedSheet));
        }

        if (ChatSettings.showFriendship())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getFriendshipText(pokemon.getFriendship(), isClosedSheet));
        }

        if (ChatSettings.showHeldItem())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getHeldItemText(heldItem, isClosedSheet));
        }   

        if (ChatSettings.showBall())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getCaughtBallText(pokemon.getCaughtBall()));
        }

        if (ChatSettings.showSize())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getSizeText(pokemon.getScaleModifier()));
        }

        if (ChatSettings.showEggGroups())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getEggGroupText(eggGroups));
        }

        if (NeoDaycareUtils.isModLoaded() && ChatSettings.showNeutered())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            boolean isNeutered = NeoDaycareUtils.isNeutered(pokemon);

            if (isNeutered || ChatSettings.showNeuteredIfFalse())
            {
                tooltip = tooltip.append(getneuterText(pokemon, isNeutered));
            }
        }

        if (ChatSettings.showOT())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(getOText(pokemon));
        }

        return tooltip;
    }

    public static List<Text> getPokemonTooltipTextList(Pokemon pokemon, boolean isClosedSheet)
    {
        List<Text> tooltipText = new ArrayList<>();

        Species species = pokemon.getSpecies();
        Set<String> aspects = pokemon.getAspects();
        List<SpeciesFeature> speciesFeatures = pokemon.getFeatures();
        int level = pokemon.getLevel();
        int nextLevelExperience = pokemon.getExperience() + pokemon.getExperienceToNextLevel();
        List<ElementalType> types = StreamSupport.stream(pokemon.getTypes().spliterator(), false).collect(Collectors.toUnmodifiableList());
        Nature nature = pokemon.getNature();
        Nature natureEffective = pokemon.getEffectiveNature();
        IVs ivs = pokemon.getIvs();
        EVs evs = pokemon.getEvs();
        List<Move> moves = pokemon.getMoveSet().getMoves();
        HashSet<EggGroup> eggGroups = species.getEggGroups();
        ItemStack heldItem = pokemon.getHeldItem$common();

        if (ChatSettings.showSpecies())
        {
            tooltipText.add(getSpeciesText(pokemon, aspects, speciesFeatures));
        }

        if (ChatSettings.showLevel())
        {
            tooltipText.add(getLevelText(level, pokemon.getExperience(), nextLevelExperience));
        }

        if (ChatSettings.showTypes())
        {
            tooltipText.add(getTypesText(types));
        }

        if (ChatSettings.showAbilities())
        {
            tooltipText.add(getAbilitiesText(pokemon, isClosedSheet));
        }

        if (ChatSettings.showNature())
        {
            tooltipText.add(getNatureText(nature, natureEffective, isClosedSheet));
        }

        if (ChatSettings.showIVs())
        {
            tooltipText.add(getIVsText(ivs, isClosedSheet));
        }

        if (ChatSettings.showEVs())
        {
            tooltipText.add(getEVsText(evs, isClosedSheet));
        }

        if (ChatSettings.showMoves())
        {
            tooltipText.add(getMovesText(moves, isClosedSheet));
        }

        if (ChatSettings.showGender())
        {
            tooltipText.add(getGenderText(pokemon.getGender(), isClosedSheet));
        }

        if (ChatSettings.showFriendship())
        {
            tooltipText.add(getFriendshipText(pokemon.getFriendship(), isClosedSheet));
        }

        if (ChatSettings.showHeldItem())
        {
            tooltipText.add(getHeldItemText(heldItem, isClosedSheet));
        }   

        if (ChatSettings.showBall())
        {
            tooltipText.add(getCaughtBallText(pokemon.getCaughtBall()));
        }

        if (ChatSettings.showSize())
        {
            tooltipText.add(getSizeText(pokemon.getScaleModifier()));
        }

        if (ChatSettings.showEggGroups())
        {
            tooltipText.add(getEggGroupText(eggGroups));
        }

        if (NeoDaycareUtils.isModLoaded() && ChatSettings.showNeutered())
        {
            boolean isNeutered = NeoDaycareUtils.isNeutered(pokemon);

            if (isNeutered || ChatSettings.showNeuteredIfFalse())
            {
                tooltipText.add(getneuterText(pokemon, isNeutered));
            }
        }

        if (ChatSettings.showOT())
        {
            tooltipText.add(getOText(pokemon));
        }

        return tooltipText;
    }

    public static Text getNicknameText(String nickname, List<ElementalType> types)
    {
        if (types.size() == 1)
        {
            return Text.literal(nickname).setStyle(Style.EMPTY.withColor(ChatColors.TypeColor.fromTypeName(types.get(0).getName())).withItalic(false));
        }
        else
        {
            return TextUtils.gradientBetweenTypes(nickname, types.get(0), types.get(1));
        }
    }

    private static Text getSpeciesText(Pokemon pokemon, Set<String> aspects, List<SpeciesFeature> speciesFeatures)
    {
        return TextUtils.coloredSpeciesLine(pokemon, TextUtils.toTitleCase(pokemon.getForm().getName()), aspects, speciesFeatures);
    }

    private static Text getLevelText(int level, int experience, int nextLevelExperience)
    {
        return TextUtils.coloredLevelLine(level, experience, nextLevelExperience);
    }

    private static Text getTypesText(List<ElementalType> types)
    {
        if (types.size() == 1)
        {
            return TextUtils.coloredMonotypeLine(types.get(0));
        }
        else
        {
            return TextUtils.coloredDualtypeLine(types.get(0), types.get(1));
        }
    }

    private static Text getAbilitiesText(Pokemon pokemon, boolean isClosedSheet)
    {
        return TextUtils.coloredAbilitiesLine(Text.translatable(pokemon.getAbility().getDisplayName()).getString(), CobblemonUtils.hasHiddenAbility(pokemon), isClosedSheet);
    }

    private static Text getNatureText(Nature nature, Nature natureEffective, boolean isClosedSheet)
    {
        return TextUtils.coloredNatureLine(nature, natureEffective, isClosedSheet);
    }

    private static Text getIVsText(IVs ivs, boolean isClosedSheet)
    {
        return TextUtils.coloredIVsLine(ivs, isClosedSheet);
    }

    private static Text getEVsText(EVs evs, boolean isClosedSheet)
    {
        return TextUtils.coloredEVsLine(evs, isClosedSheet);
    }

    private static Text getMovesText(List<Move> moves, boolean isClosedSheet)
    {
        return TextUtils.coloredMovesLine(moves, isClosedSheet);
    }

    private static Text getGenderText(Gender gender, boolean isClosedSheet)
    {
        return TextUtils.coloredGenderLine(gender, isClosedSheet);
    }

    private static Text getFriendshipText(int friendship, boolean isClosedSheet)
    {
        return TextUtils.coloredFriendshipLine(friendship, isClosedSheet);
    }

    private static Text getHeldItemText(ItemStack heldItem, boolean isClosedSheet)
    {
        return TextUtils.coloredHeldItemLine(heldItem, isClosedSheet);
    }

    private static Text getCaughtBallText(PokeBall caughtBall)
    {
        return TextUtils.coloredCaughtBallLine(caughtBall);
    }

    private static Text getSizeText(float scale)
    {
        return TextUtils.coloredSizeLine(scale);
    }

    private static Text getEggGroupText(HashSet<EggGroup> eggGroups)
    {
        return TextUtils.coloredEggGroupsLine(eggGroups);
    }

    private static Text getneuterText(Pokemon pokemon, boolean isNeutered)
    {
        return TextUtils.coloredNeuterLine(pokemon, isNeutered);
    }

    private static Text getOText(Pokemon pokemon)
    {
        String originalTrainer;
        try
        {
            originalTrainer = pokemon.getOriginalTrainerName() == null ? ChatStrings.getUnknownPlayerString() : pokemon.getOriginalTrainerName();
        }
        catch (Exception e)
        {
            originalTrainer = ChatStrings.getUnknownPlayerString();
        }

        return TextUtils.coloredOTLine(originalTrainer);
    }
}
