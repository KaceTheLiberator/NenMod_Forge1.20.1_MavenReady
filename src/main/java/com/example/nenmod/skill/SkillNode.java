package com.example.nenmod.skill;

import com.google.gson.annotations.SerializedName;
import java.util.List;

public class SkillNode {
    @SerializedName("id") public String id;
    @SerializedName("name") public String name;
    @SerializedName("affinity") public String affinity;
    @SerializedName("type") public String type; // stat | ability | vow
    @SerializedName("cost") public int cost;
    @SerializedName("requires") public List<String> requires;
    @SerializedName("payload") public String payload; // e.g., abilityId or stat key
    @SerializedName("value") public double value; // stat value
}
