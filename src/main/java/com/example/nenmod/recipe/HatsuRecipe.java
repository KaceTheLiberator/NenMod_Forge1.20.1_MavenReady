package com.example.nenmod.recipe;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class HatsuRecipe {
    @SerializedName("ability_id") public String abilityId;
    @SerializedName("requires_abilities") public List<String> requiresAbilities;
    @SerializedName("min_aura_level") public int minAuraLevel;
    @SerializedName("notes") public String notes;
}
