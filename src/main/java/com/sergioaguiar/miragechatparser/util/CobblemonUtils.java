package com.sergioaguiar.miragechatparser.util;

import java.util.HashSet;

import com.cobblemon.mod.common.api.abilities.PotentialAbility;
import com.cobblemon.mod.common.pokemon.Pokemon;
import com.cobblemon.mod.common.pokemon.abilities.HiddenAbilityType;

public class CobblemonUtils 
{
    public static boolean hasHiddenAbility(Pokemon pokemon)
    {
        if (pokemon == null) return false;

        HashSet<String> uniqueAbilities = new HashSet<>();
        boolean isHidden = false;

        for (final PotentialAbility ability : pokemon.getForm().getAbilities())
        {
            uniqueAbilities.add(ability.getTemplate().getDisplayName());
            if (ability.getType() == HiddenAbilityType.INSTANCE && ability.getTemplate() == pokemon.getAbility().getTemplate()) isHidden = true;
        }

        return isHidden && uniqueAbilities.size() > 1 ? true : false;
    }

    public static boolean hasForcedAbility(Pokemon pokemon)
    {
        if (pokemon == null) return false;
        if (pokemon.getAbility().getForced()) return true;
        return false;
    }
}
