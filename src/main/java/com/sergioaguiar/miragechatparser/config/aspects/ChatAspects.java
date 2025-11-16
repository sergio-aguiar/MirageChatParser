package com.sergioaguiar.miragechatparser.config.aspects;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ChatAspects
{
    private static final Set<String> DEFAULT_IGNORED_SPECIES_FEATURES = new HashSet<>(List.of
    (
        "claw",
        "cocoon_species",
        "color",
        "colour",
        "cosmetic_item",
        "disguise_form",
        "dolphin_form",
        "dynamax_form",
        "embody_aspect",
        "face_spots",
        "face_spots2",
        "gilded_chest",
        "gimmighoul_coins",
        "gimmighoul_netherite",
        "gyarados_eye_color",
        "hero",
        "hunger_mode",
        "left_ear_spots",
        "life_mode",
        "mega_evolution",
        "metals",
        "meteor_shield",
        "minecraft",
        "missile_form",
        "has_nectar",
        "paint_color",
        "penguin_head",
        "power_construct",
        "right_ear_spots",
        "roaming",
        "schooling_form",
        "song_forme",
        "special_spots",
        "stance_forme",
        "tera_form",
        "whiscash_nero",
        "wooper_heart"
    ));

    public static final String SPECIES_FEATURE_MOOSHTANK_STRING = "mooshtank";
    public static final String SPECIES_FEATURE_MOOSHTANK_FALSE_STRING = "false";

    public static final String SPECIES_FEATURE_NETHERITE_COATING_STRING = "netherite_coating";
    public static final String SPECIES_FEATURE_NETHERITE_COATING_NONE_STRING = "none";
    public static final String SPECIES_FEATURE_NETHERITE_COATING_APPEND_STRING = " Netherite";

    public static final String SPECIES_FEATURE_REGION_BIAS_STRING = "region_bias";
    public static final String SPECIES_FEATURE_REGION_BIAS_APPEND_STRING = " Bias";

    public static final String SPECIES_FEATURE_TREE_STRING = "tree";
    public static final String SPECIES_FEATURE_TREE_NONE_STRING = "none";

    private static Set<String> ignoredSpeciesFeatures;
    private static Map<String, String> displayedAspects;

    public static void setDefaults()
    {
        ignoredSpeciesFeatures = new HashSet<>(DEFAULT_IGNORED_SPECIES_FEATURES);
        displayedAspects = new HashMap<>();
    }

    public static boolean isSpeciesFeatureIgnored(String speciesFeature) { return ignoredSpeciesFeatures.contains(speciesFeature); }
    public static boolean shouldDisplayAspect(String aspect) { return displayedAspects.containsKey(aspect); }
    public static int getDisplayedAspectsCount() { return displayedAspects.size(); }
    public static String getAspectFriendlyName(String aspect) { return displayedAspects.get(aspect); }

    protected static void addIgnoredSpeciesFeature(String speciesFeature) { ignoredSpeciesFeatures.add(speciesFeature); }
    protected static void addDisplayedAspect(String aspect, String aspectFriendlyName) { displayedAspects.put(aspect, aspectFriendlyName); }
}
