package com.example.nenmod.skill;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import java.lang.reflect.Type;
import java.util.*;

public class SkillTreeLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new Gson();
    private static final Type LIST_TYPE = new TypeToken<List<SkillNode>>(){}.getType();
    private static final Map<String, SkillNode> NODES = new HashMap<>();

    public SkillTreeLoader() {
        super(GSON, "nenmod/skilltree");
    }

    @Override
    protected void apply(Map<ResourceLocation, com.google.gson.JsonElement> jsonMap, ResourceManager mgr, ProfilerFiller profiler) {
        NODES.clear();
        for (var e : jsonMap.entrySet()) {
            List<SkillNode> list = GSON.fromJson(e.getValue(), LIST_TYPE);
            for (SkillNode n : list) NODES.put(n.id, n);
        }
        System.out.println("[NenMod] Loaded skill nodes: " + NODES.size());
    }

    public static SkillNode get(String id) { return NODES.get(id); }
    public static Collection<SkillNode> all() { return NODES.values(); }
}
