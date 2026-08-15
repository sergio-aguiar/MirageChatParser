package com.sergioaguiar.mirageessentials.util;

import java.lang.reflect.Method;
import java.util.List;

import com.cobblemon.mod.common.api.pokemon.feature.IntSpeciesFeature;
import com.cobblemon.mod.common.api.pokemon.feature.SpeciesFeature;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.sergioaguiar.mirageessentials.config.chatparser.aspects.ChatAspects;

import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.nbt.NbtCompound;

public class NeoDaycareUtils
{
    private static final String MOD_ID_STRING = "neodaycare";

    private static final String DAYCAREUTILS_CLASS_STRING = "dev.neovitalism.neodaycare.utils.DaycareUtils";
    private static final String MAINCONFIG_CLASS_STRING = "dev.neovitalism.neodaycare.config.MainConfig";

    private static Class<?> daycareUtilsClass;
    private static Class<?> mainConfigClass;

    private static Method isUnbreedableMethod;
    private static Method isEggMethod;
    private static Method getStepsPerCycleMethod;

    private static boolean isNeodaycareAvailable;

    static 
    {
        try
        {
            daycareUtilsClass = Class.forName(DAYCAREUTILS_CLASS_STRING);
            mainConfigClass = Class.forName(MAINCONFIG_CLASS_STRING);
            
            isUnbreedableMethod = daycareUtilsClass.getDeclaredMethod("isUnbreedable", Pokemon.class);
            isEggMethod = daycareUtilsClass.getDeclaredMethod("isEgg", Pokemon.class);
            getStepsPerCycleMethod = mainConfigClass.getDeclaredMethod("getStepsPerCycle");
            
            isNeodaycareAvailable = true;
        }
        catch (Exception e)
        {
            isNeodaycareAvailable = false;
        }
    }

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }

    public static boolean isNeutered(Pokemon pokemon)
    {
        if (pokemon == null) return false;
        if (!isNeodaycareAvailable) return getIsNeuteredFallback(pokemon);
        return tryIsUnbreedable(pokemon);
    }

    private static boolean tryIsUnbreedable(Pokemon pokemon)
    {
        try
        {
            return (boolean) isUnbreedableMethod.invoke(null, pokemon);
        }
        catch (Exception e)
        {
            return getIsNeuteredFallback(pokemon);
        }
    }

    private static boolean getIsNeuteredFallback(Pokemon pokemon)
    {
        return pokemon.getPersistentData().contains("breedable") && !pokemon.getPersistentData().getBoolean("breedable");
    }

    public static boolean isEgg(Pokemon pokemon)
    {
        if (pokemon == null) return false;
        if (!isNeodaycareAvailable) return getIsEggFallback(pokemon);
        return tryIsEgg(pokemon);
    }

    private static boolean tryIsEgg(Pokemon pokemon)
    {
        try
        {
            return (boolean) isEggMethod.invoke(null, pokemon);
        }
        catch (Exception e)
        {
            return getIsEggFallback(pokemon);
        }
    }

    private static boolean getIsEggFallback(Pokemon pokemon)
    {
        return false;
    }

    public static Integer getRemainingSteps(Pokemon pokemon)
    {
        if (pokemon == null || !isEgg(pokemon)) return null;
        if (!isNeodaycareAvailable) return null;
        return tryGetRemainingSteps(pokemon);
    }

    private static Integer tryGetRemainingSteps(Pokemon pokemon)
    {
        try
        {
            NbtCompound data = pokemon.getPersistentData();
            int cycle = data.getInt("Cycle");
            int steps = data.getInt("Steps");
            int stepsPerCycle = (int) getStepsPerCycleMethod.invoke(null);

            return (cycle - 1) * stepsPerCycle + steps;
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
