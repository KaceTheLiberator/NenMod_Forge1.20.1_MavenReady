package com.example.nenmod.recipe;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.lang.reflect.Type;
import java.util.*;
import com.example.nenmod.content.PlayerNenData;

public class HatsuRecipeLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Type LIST_TYPE = new TypeToken<List<HatsuRecipe>>(){}.getType();
    private static final Map<String, HatsuRecipe> RECIPES = new HashMap<>();

    public HatsuRecipeLoader() {
        super(GSON, "nenmod/hatsu_recipes");
    }

    @Override
    protected void apply(Map<ResourceLocation, com.google.gson.JsonElement> jsonMap, ResourceManager mgr, ProfilerFiller profiler) {
        RECIPES.clear();
        for (var e : jsonMap.entrySet()) {
            List<HatsuRecipe> list = GSON.fromJson(e.getValue(), LIST_TYPE);
            for (HatsuRecipe r : list) RECIPES.put(r.abilityId, r);
        }
        System.out.println("[NenMod] Loaded Hatsu recipes: " + RECIPES.size());
    }

    public static HatsuRecipe get(String abilityId) { return RECIPES.get(abilityId); }

    public static boolean canCraft(ServerPlayer sp, PlayerNenData data, HatsuRecipe rec) {
        if (data.auraLevel < rec.minAuraLevel) return false;
        if (rec.requiresAbilities != null) {
            for (String need : rec.requiresAbilities) if (!data.unlockedAbilities.contains(need)) return false;
        }
        return true;
    }
}
