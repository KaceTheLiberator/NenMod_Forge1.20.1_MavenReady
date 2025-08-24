package com.example.nenmod.ability;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.lang.reflect.Type;
import java.util.*;

public class AbilityLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Type LIST_TYPE = new TypeToken<List<Ability>>(){}.getType();
    private static final Map<String, Ability> ABILITIES = new HashMap<>();

    public AbilityLoader() {
        super(GSON, "nenmod/abilities");
    }

    @Override
    protected void apply(Map<ResourceLocation, com.google.gson.JsonElement> jsonMap, ResourceManager mgr, ProfilerFiller profiler) {
        ABILITIES.clear();
        for (var entry : jsonMap.entrySet()) {
            List<Ability> list = GSON.fromJson(entry.getValue(), LIST_TYPE);
            for (Ability a : list) {
                ABILITIES.put(a.id, a);
            }
        }
        System.out.println("[NenMod] Loaded abilities: " + ABILITIES.size());
    }

    public static Ability get(String id) { return ABILITIES.get(id); }
    public static Collection<Ability> all() { return ABILITIES.values(); }
}
