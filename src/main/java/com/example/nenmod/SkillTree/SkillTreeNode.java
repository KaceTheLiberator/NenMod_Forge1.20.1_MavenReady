package com.yourmod.skilltree;

import net.minecraft.nbt.CompoundTag;

public class SkillTreeNode {
    private final String id;
    private final String name;
    private final String description;
    private final int cost; // cost in skill points
    private boolean unlocked;

    public SkillTreeNode(String id, String name, String description, int cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
        this.unlocked = false;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public int getCost() { return cost; }
    public boolean isUnlocked() { return unlocked; }

    public boolean unlock() {
        if (!unlocked) {
            unlocked = true;
            return true;
        }
        return false;
    }

    // --- NBT Save/Load ---
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Id", id);
        tag.putBoolean("Unlocked", unlocked);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        this.unlocked = tag.getBoolean("Unlocked");
    }
}
