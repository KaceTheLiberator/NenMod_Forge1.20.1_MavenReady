package com.yourmod.player;

import net.minecraft.nbt.CompoundTag;

public class UnlockManager {

    private int skillPoints;
    private int hatsuPoints;

    public UnlockManager() {
        this.skillPoints = 0;
        this.hatsuPoints = 0;
    }

    // --- Points Management ---
    public int getSkillPoints() { return skillPoints; }
    public int getHatsuPoints() { return hatsuPoints; }

    public void addSkillPoint() { this.skillPoints++; }
    public void addHatsuPoint() { this.hatsuPoints++; }

    public boolean spendSkillPoint() {
        if (skillPoints > 0) {
            skillPoints--;
            return true;
        }
        return false;
    }

    public boolean spendHatsuPoint() {
        if (hatsuPoints > 0) {
            hatsuPoints--;
            return true;
        }
        return false;
    }

    // --- NBT Save/Load ---
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("SkillPoints", skillPoints);
        tag.putInt("HatsuPoints", hatsuPoints);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        this.skillPoints = tag.getInt("SkillPoints");
        this.hatsuPoints = tag.getInt("HatsuPoints");
    }
}
