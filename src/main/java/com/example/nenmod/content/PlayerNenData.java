package com.example.nenmod.content;

import net.minecraft.nbt.CompoundTag;
import java.util.HashSet;
import java.util.Set;

public class PlayerNenData {
    // Nen pool & states
    public int nen = 100;
    public int nenMax = 100;
    public String activeAbility = "";
    public boolean isTenActive = false;
    public boolean isGyoActive = false;
    public boolean isInActive = false;
    public boolean isZetsuActive = false;

    // Mastery (0..100) - hidden from UI
    public float masteryTen = 0f;
    public float masteryGyo = 0f;
    public float masteryIn = 0f;
    public float masteryZetsu = 0f;

    // Ability-specific mastery
    public float masteryBungee = 0f;

    // Bungee Gum state (two slots)
    public boolean gumAttachedA = false; public boolean gumAttachedB = false;
    public boolean gumAToBlock = false; public boolean gumBToBlock = false;
    public int gumAEntityId = -1; public int gumBEntityId = -1;
    public int gumAX = 0, gumAY = 0, gumAZ = 0; public int gumBX = 0, gumBY = 0, gumBZ = 0;    
    
    public int auraXP = 0;
    public int auraLevel = 0;
    public String nenAffinity = "Unknown"; // Enhancer/Emitter/Transmuter/Conjurer/Manipulator/Specialist
    public final Set<String> unlockedAbilities = new HashSet<>();
    public final Set<String> unlockedNodes = new HashSet<>();

    public void copyFrom(PlayerNenData other) {
        this.nen = other.nen;
        this.nenMax = other.nenMax;
        this.activeAbility = other.activeAbility;
        this.isTenActive = other.isTenActive;
        this.isGyoActive = other.isGyoActive;
        this.isInActive = other.isInActive;
        this.isZetsuActive = other.isZetsuActive;
        this.masteryTen = other.masteryTen;
        this.masteryGyo = other.masteryGyo;
        this.masteryIn = other.masteryIn;
        this.masteryZetsu = other.masteryZetsu;
        this.masteryBungee = other.masteryBungee;
        this.gumAttachedA = other.gumAttachedA; this.gumAttachedB = other.gumAttachedB;
        this.gumAToBlock = other.gumAToBlock; this.gumBToBlock = other.gumBToBlock;
        this.gumAEntityId = other.gumAEntityId; this.gumBEntityId = other.gumBEntityId;
        this.gumAX = other.gumAX; this.gumAY = other.gumAY; this.gumAZ = other.gumAZ; this.gumBX = other.gumBX; this.gumBY = other.gumBY; this.gumBZ = other.gumBZ;
    
        this.auraXP = other.auraXP;
        this.auraLevel = other.auraLevel;
        this.nenAffinity = other.nenAffinity;
        this.unlockedAbilities.clear();
        this.unlockedAbilities.addAll(other.unlockedAbilities);
        this.unlockedNodes.clear();
        this.unlockedNodes.addAll(other.unlockedNodes);
    }

    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("nen", nen);
        tag.putInt("nenMax", nenMax);
        tag.putString("activeAbility", activeAbility);
        tag.putBoolean("ten", isTenActive);
        tag.putBoolean("gyo", isGyoActive);
        tag.putBoolean("in", isInActive);
        tag.putBoolean("zetsu", isZetsuActive);
        tag.putFloat("mTen", masteryTen);
        tag.putFloat("mGyo", masteryGyo);
        tag.putFloat("mIn", masteryIn);
        tag.putFloat("mZetsu", masteryZetsu);
        tag.putFloat("mBungee", masteryBungee);
        tag.putBoolean("gumAttachedA", gumAttachedA); tag.putBoolean("gumAttachedB", gumAttachedB);
        tag.putBoolean("gumAToBlock", gumAToBlock); tag.putBoolean("gumBToBlock", gumBToBlock);
        tag.putInt("gumAEntityId", gumAEntityId); tag.putInt("gumBEntityId", gumBEntityId);
        tag.putInt("gumAX", gumAX); tag.putInt("gumAY", gumAY); tag.putInt("gumAZ", gumAZ); tag.putInt("gumBX", gumBX); tag.putInt("gumBY", gumBY); tag.putInt("gumBZ", gumBZ);
    
        CompoundTag tag = new CompoundTag();
        tag.putInt("auraXP", auraXP);
        tag.putInt("auraLevel", auraLevel);
        tag.putString("nenAffinity", nenAffinity);
        var ab = new net.minecraft.nbt.ListTag();
        for (String s : unlockedAbilities) ab.add(net.minecraft.nbt.StringTag.valueOf(s));
        tag.put("abilities", ab);
        var nd = new net.minecraft.nbt.ListTag();
        for (String s : unlockedNodes) nd.add(net.minecraft.nbt.StringTag.valueOf(s));
        tag.put("nodes", nd);
        return tag;
    }

    public void load(CompoundTag tag) {
        auraXP = tag.getInt("auraXP");
        nen = tag.getInt("nen");
        nenMax = tag.getInt("nenMax");
        if (tag.contains("gumAttachedA")) {
        activeAbility = tag.getString("activeAbility");
        isTenActive = tag.getBoolean("ten");
        isGyoActive = tag.getBoolean("gyo");
        isInActive = tag.getBoolean("in");
        isZetsuActive = tag.getBoolean("zetsu");
        masteryTen = tag.getFloat("mTen");
        masteryGyo = tag.getFloat("mGyo");
        masteryIn = tag.getFloat("mIn");
        masteryZetsu = tag.getFloat("mZetsu");
        masteryBungee = tag.getFloat("mBungee");
        
        this.gumAttachedA = tag.getBoolean("gumAttachedA");
        this.gumAttachedB = tag.getBoolean("gumAttachedB");
        this.gumAToBlock = tag.getBoolean("gumAToBlock");
        this.gumBToBlock = tag.getBoolean("gumBToBlock");
        this.gumAEntityId = tag.getInt("gumAEntityId");
        this.gumBEntityId = tag.getInt("gumBEntityId");
        this.gumAX = tag.getInt("gumAX"); this.gumAY = tag.getInt("gumAY"); this.gumAZ = tag.getInt("gumAZ");
        this.gumBX = tag.getInt("gumBX"); this.gumBY = tag.getInt("gumBY"); this.gumBZ = tag.getInt("gumBZ");
    
        }
        
        gumAttached = tag.getBoolean("gumAttached");
        gumToBlock = tag.getBoolean("gumToBlock");
        gumEntityId = tag.getInt("gumEntityId");
        gumX = tag.getInt("gumX"); gumY = tag.getInt("gumY"); gumZ = tag.getInt("gumZ");
        auraLevel = tag.getInt("auraLevel");
        nenAffinity = tag.getString("nenAffinity");
        unlockedAbilities.clear();
        var ab = tag.getList("abilities", 8);
        for (int i=0;i<ab.size();i++) unlockedAbilities.add(ab.getString(i));
        unlockedNodes.clear();
        var nd = tag.getList("nodes", 8);
        for (int i=0;i<nd.size();i++) unlockedNodes.add(nd.getString(i));
    }
}
