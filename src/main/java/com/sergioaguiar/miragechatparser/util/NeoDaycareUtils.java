package com.sergioaguiar.miragechatparser.util;

import com.cobblemon.mod.common.pokemon.Pokemon;

import net.fabricmc.loader.api.FabricLoader;

public class NeoDaycareUtils
{
    private static final String MOD_ID_STRING = "neodaycare";

    public static boolean isModLoaded()
    {
        return FabricLoader.getInstance().isModLoaded(MOD_ID_STRING);
    }

    public static boolean isNeutered(Pokemon pokemon)
    {
        if (pokemon == null) return false;
        return pokemon.getPersistentData().contains("breedable") && !pokemon.getPersistentData().getBoolean("breedable");
    }
}
