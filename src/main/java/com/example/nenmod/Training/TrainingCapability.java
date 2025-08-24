package com.yourname.nenmod.training;

import net.minecraft.nbt.CompoundTag;

public class TrainingCapability {
    private int pushups;
    private int situps;
    private int squats;
    private int sprintTicks; // total sprint ticks (20 ticks ≈ 1s)

    // simple anti-spam cooldown (ticks)
    private int actionCooldownTicks = 0;

    public void tick() {
        if (actionCooldownTicks > 0) actionCooldownTicks--;
    }

    public boolean ready() { return actionCooldownTicks == 0; }

    /** start a short cooldown after a manual training action (client key) */
    public void triggerCooldown() { this.actionCooldownTicks = 12; } // ~0.6s

    public void addPushup() { pushups++; }
    public void addSitup()  { situps++; }
    public void addSquat()  { squats++; }
    public void addSprintTicks(int t) { sprintTicks += Math.max(0, t); }

    public int getPushups() { return pushups; }
    public int getSitups()  { return situps; }
    public int getSquats()  { return squats; }
    public int getSprintTicks() { return sprintTicks; }

    // NBT
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Pushups", pushups);
        tag.putInt("Situps", situps);
        tag.putInt("Squats", squats);
        tag.putInt("SprintTicks", sprintTicks);
        tag.putInt("Cooldown", actionCooldownTicks);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        pushups = tag.getInt("Pushups");
        situps = tag.getInt("Situps");
        squats = tag.getInt("Squats");
        sprintTicks = tag.getInt("SprintTicks");
        actionCooldownTicks = tag.getInt("Cooldown");
    }
}
