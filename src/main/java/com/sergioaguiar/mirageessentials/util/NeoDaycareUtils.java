package com.sergioaguiar.mirageessentials.util;

import java.util.List;

import com.cobblemon.mod.common.api.pokemon.PokemonProperties;
import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspects;

import dev.neovitalism.neodaycare.config.MainConfig;
import dev.neovitalism.neodaycare.utils.DaycareUtils;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;

public class NeoDaycareUtils
{
    private static final String MOD_ID_STRING = "neodaycare";

    private static String EGG_PROPERTY_STRING = "egg";

    private static final PokemonProperties EGG_PROPERTY;

    static 
    {
        EGG_PROPERTY = PokemonProperties.Companion.parse(EGG_PROPERTY_STRING);
    }

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }

    public static boolean isNeutered(Pokemon pokemon)
    {
        if (pokemon == null) return false;

        try
        {
            return DaycareUtils.isUnbreedable(pokemon);
        }
        catch (Exception e)
        {
            return pokemon.getPersistentData().contains("breedable") && !pokemon.getPersistentData().getBoolean("breedable");
        }
    }

    public static boolean isEgg(Pokemon pokemon)
    {
        if (pokemon == null) return false;

        try
        {
            return DaycareUtils.isEgg(pokemon);
        }
        catch (Exception e)
        {
            return EGG_PROPERTY.matches(pokemon);
        }
    }

    public static Integer getRemainingSteps(Pokemon pokemon)
    {
        if (pokemon == null || !isEgg(pokemon)) return null;

        try
        {
            NbtCompound data = pokemon.getPersistentData();
            int cycle = data.getInt("Cycle");
            int steps = data.getInt("Steps");

            return (cycle - 1) * MainConfig.getStepsPerCycle() + steps;
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static Integer getTotalSteps(Pokemon pokemon)
    {
        if (pokemon == null || !isEgg(pokemon)) return null;

        try
        {
            return pokemon.getPersistentData().getInt("TotalSteps");
        }
        catch (Exception e)
        {
            return null;
        }
    }

    public static int getHatchPercentage(List<SpeciesFeature> speciesFeatures)
    {
        for (SpeciesFeature feature : speciesFeatures)
        {
            if (!(feature instanceof IntSpeciesFeature intFeature)) continue;

            if (intFeature.getName().equals(ChatAspects.SPECIES_FEATURE_HATCH_PERCENTAGE_STRING))
            {
                return intFeature.getValue();
            }
        }
        
        return 0;
    }
}
