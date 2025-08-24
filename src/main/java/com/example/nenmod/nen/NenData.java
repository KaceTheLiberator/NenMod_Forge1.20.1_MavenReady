package com.nenmod.nen;

import net.minecraft.nbt.CompoundTag;

public class NenData {
    private boolean unlocked = false; // set true when Ten is learned
    private int level = 1;
    private int xp = 0;

    // next-level requirement is derived (formula below)
    public boolean isUnlocked() { return unlocked; }
    public void unlock() { unlocked = true; if (level < 1) level = 1; }

    public int getLevel() { return level; }
    public int getXp() { return xp; }
    public int getXpToNext() { return xpRequirement(level); }

    // XP & Leveling
    public boolean addXp(int amount) {
        if (!unlocked || amount <= 0) return false;
        xp += amount;
        boolean leveled = false;
        while (xp >= getXpToNext()) {
            xp -= getXpToNext();
            level++;
            leveled = true;
        }
        // clamp
        if (xp < 0) xp = 0;
        return leveled;
    }

    private int xpRequirement(int lvl) {
        // Simple, smooth curve. Tweak as you like.
        // Start 100, then grows ~quadratically.
        return Math.max(100, 80 + (lvl * lvl * 10));
    }

    // NBT
    public CompoundTag save() {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Unlocked", unlocked);
        tag.putInt("Level", level);
        tag.putInt("XP", xp);
        return tag;
    }
    public void load(CompoundTag tag) {
        unlocked = tag.getBoolean("Unlocked");
        level = Math.max(1, tag.getInt("Level"));
        xp = Math.max(0, tag.getInt("XP"));
    }private final UnlockManager unlockManager = new UnlockManager}
    public void levelUp() {
    nenLevel++;
    currentXP = 0;

    // Give 1 Skill Point every level
    unlockManager.addSkillPoint();

    // Give 1 Hatsu Point every 10 levels
    if (nenLevel % 10 == 0) {
        unlockManager.addHatsuPoint();
    }
}
public UnlockManager getUnlockManager() {
    return unlockManager;
}
// Save
tag.put("UnlockManager", unlockManager.saveNBT());

// Load
if (tag.contains("UnlockManager")) {
    unlockManager.loadNBT(tag.getCompound("UnlockManager"));
}
NenData data = NenData.get(player);
if (data.getUnlockManager().spendSkillPoint()) {
    // unlock the node
} else {
    player.sendSystemMessage(Component.literal("Not enough skill points!"));
}
NenData data = NenData.get(player);
if (data.getUnlockManager().spendHatsuPoint()) {
    // unlock the hatsu
} else {
    player.sendSystemMessage(Component.literal("Not enough hatsu points!"));
}
private final SkillTree skillTree = new SkillTree(); // later replaced by affinity tree

public SkillTree getSkillTree() {
    return skillTree;
}
tag.put("SkillTree", skillTree.saveNBT());

if (tag.contains("SkillTree")) {
    skillTree.loadNBT(tag.getCompound("SkillTree"));
}
NenData data = NenData.get(player);
boolean unlocked = data.getSkillTree().unlockNode("enhancer_hp", data);

if (unlocked) {
    player.sendSystemMessage(Component.literal("Unlocked: Boost HP!"));
} else {
    player.sendSystemMessage(Component.literal("Not enough points or already unlocked."));
}



