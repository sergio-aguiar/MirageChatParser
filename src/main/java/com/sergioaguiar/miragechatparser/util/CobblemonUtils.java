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
    public enum SizeCategory
    {
        TINY(0.2f, 0.5f),
        SMALL(0.51f, 0.9f),
        AVERAGE(0.91f, 1.2f),
        BIG(1.21f, 1.6f),
        LARGE(1.61f, 1.9f),
        HUGE(1.91f, 2.0f);

        private final float min;
        private final float max;

        SizeCategory(float min, float max)
        {
            this.min = min;
            this.max = max;
        }

        public float getMin()
        {
            return min;
        }

        public float getMax()
        {
            return max;
        }

        public static SizeCategory fromSize(float size)
        {
            for (SizeCategory category : values())
            {
                if (size >= category.min && size <= category.max)
                {
                    return category;
                }
            }
            return AVERAGE;
        }

        @Override
        public String toString()
        {
            return name().charAt(0) + name().substring(1).toLowerCase();
        }
    }

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
