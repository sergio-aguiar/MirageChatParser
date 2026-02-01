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
import com.cobblemon.mod.common.pokemon.EVs;
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

            if (types.size() == 1 )
            {
                tooltip = tooltip
                    .append(Text.literal(nickname)
                        .setStyle(Style.EMPTY.withColor(ChatColors.TypeColor.fromTypeName(types.get(0).getName()))));
            }
            else
            {
                tooltip = tooltip.append(TextUtils.gradientBetweenTypes(nickname, types.get(0), types.get(1)));
            }
        }

        if (ChatSettings.showSpecies())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip
                .append(TextUtils.coloredSpeciesLine(pokemon, TextUtils.toTitleCase(pokemon.getForm().getName()), aspects, speciesFeatures));
        }

        if (ChatSettings.showLevel())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip
                .append(TextUtils.coloredLevelLine(level, pokemon.getExperience(), nextLevelExperience));
        }

        if (ChatSettings.showSize())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            if (types.size() == 1 )
            {
                tooltip = tooltip.append(TextUtils.coloredMonotypeLine(types.get(0)));
            }
            else
            {
                tooltip = tooltip.append(TextUtils.coloredDualtypeLine(types.get(0), types.get(1)));
            }
        }

        if (ChatSettings.showAbilities())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredAbilitiesLine(Text.translatable(pokemon.getAbility().getDisplayName()).getString(), CobblemonUtils.hasHiddenAbility(pokemon), isClosedSheet));
        }

        if (ChatSettings.showNature())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredNatureLine(nature, natureEffective, isClosedSheet));
        }

        if (ChatSettings.showIVs())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredIVsLine(ivs, isClosedSheet));
        }

        if (ChatSettings.showEVs())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredEVsLine(evs, isClosedSheet));
        }

        if (ChatSettings.showMoves())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredMovesLine(moves, isClosedSheet));
        }

        if (ChatSettings.showGender())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredGenderLine(pokemon.getGender(), isClosedSheet));
        }

        if (ChatSettings.showFriendship())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredFriendshipLine(pokemon.getFriendship(), isClosedSheet));
        }

        if (ChatSettings.showHeldItem())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredHeldItemLine(heldItem, isClosedSheet));
        }   

        if (ChatSettings.showBall())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredCaughtBallLine(pokemon.getCaughtBall()));
        }

        if (ChatSettings.showSize())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredSizeLine(pokemon.getScaleModifier()));
        }

        if (ChatSettings.showEggGroups())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            tooltip = tooltip.append(TextUtils.coloredEggGroupsLine(eggGroups));
        }

        if (NeoDaycareUtils.isModLoaded() && ChatSettings.showNeutered())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            boolean isNeutered = NeoDaycareUtils.isNeutered(pokemon);

            if (isNeutered || ChatSettings.showNeuteredIfFalse())
            {
                tooltip = tooltip.append(TextUtils.coloredNeuterLine(pokemon, isNeutered));
            }
        }

        if (ChatSettings.showOT())
        {
            if (first) first = false;
            else tooltip = tooltip.append(Text.literal("\n"));

            String originalTrainer = pokemon.getOriginalTrainerName() == null ? ChatStrings.getOriginalTrainerString() : pokemon.getOriginalTrainerName();

            tooltip = tooltip.append(TextUtils.coloredOTLine(originalTrainer));
        }

        return TextUtils.hoverableText(species.getName(), tooltip, pokemon.getShiny());
    }
}
