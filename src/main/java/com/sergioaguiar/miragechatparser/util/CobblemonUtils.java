package com.sergioaguiar.miragechatparser.util;

import java.util.HashSet;

import com.cobblemon.mod.common.api.abilities.PotentialAbility;
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

    public static int hasHiddenAbility(Pokemon pokemon)
    {
        if (pokemon == null) return -1;
        if (pokemon.getAbility().getForced()) return 2;

        HashSet<String> uniqueAbilities = new HashSet<>();
        boolean isHidden = false;

        for (final PotentialAbility ability : pokemon.getForm().getAbilities())
        {
            uniqueAbilities.add(ability.getTemplate().getDisplayName());
            if (ability.getType() == HiddenAbilityType.INSTANCE && ability.getTemplate() == pokemon.getAbility().getTemplate()) isHidden = true;
        }

        return isHidden && uniqueAbilities.size() > 1 ? 1 : 0;
    }
}
