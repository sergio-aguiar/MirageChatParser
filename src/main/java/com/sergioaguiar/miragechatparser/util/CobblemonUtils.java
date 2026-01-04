package com.sergioaguiar.miragechatparser.util;

import java.util.HashSet;
import java.util.Set;

import com.cobblemon.mod.common.api.abilities.PotentialAbility;
import com.cobblemon.mod.common.api.pokemon.stats.Stats;
import com.cobblemon.mod.common.pokemon.IVs;
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

    public static boolean isHyperTrained(IVs ivs)
    {
        if (ivs.get(Stats.HP) != ivs.getEffectiveBattleIV(Stats.HP)) return true;
        if (ivs.get(Stats.ATTACK) != ivs.getEffectiveBattleIV(Stats.ATTACK)) return true;
        if (ivs.get(Stats.DEFENCE) != ivs.getEffectiveBattleIV(Stats.DEFENCE)) return true;
        if (ivs.get(Stats.SPECIAL_ATTACK) != ivs.getEffectiveBattleIV(Stats.SPECIAL_ATTACK)) return true;
        if (ivs.get(Stats.SPECIAL_DEFENCE) != ivs.getEffectiveBattleIV(Stats.SPECIAL_DEFENCE)) return true;
        if (ivs.get(Stats.SPEED) != ivs.getEffectiveBattleIV(Stats.SPEED)) return true;
        return false;
    }

    public static Set<Stats> getHyperTrainedStats(IVs ivs)
    {
        Set<Stats> hyperTrainedStats = new HashSet<>();

        if (ivs.get(Stats.HP) != ivs.getEffectiveBattleIV(Stats.HP)) hyperTrainedStats.add(Stats.HP);
        if (ivs.get(Stats.ATTACK) != ivs.getEffectiveBattleIV(Stats.ATTACK)) hyperTrainedStats.add(Stats.ATTACK);
        if (ivs.get(Stats.DEFENCE) != ivs.getEffectiveBattleIV(Stats.DEFENCE)) hyperTrainedStats.add(Stats.DEFENCE);
        if (ivs.get(Stats.SPECIAL_ATTACK) != ivs.getEffectiveBattleIV(Stats.SPECIAL_ATTACK)) hyperTrainedStats.add(Stats.SPECIAL_ATTACK);
        if (ivs.get(Stats.SPECIAL_DEFENCE) != ivs.getEffectiveBattleIV(Stats.SPECIAL_DEFENCE)) hyperTrainedStats.add(Stats.SPECIAL_DEFENCE);
        if (ivs.get(Stats.SPEED) != ivs.getEffectiveBattleIV(Stats.SPEED)) hyperTrainedStats.add(Stats.SPEED);

        return hyperTrainedStats;
    }
}
