package com.yourmod.training;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerTaskManager {

    private int pushups;
    private int situps;
    private int squats;
    private int running;

    public static final int REQUIRED_PUSHUPS = 100;
    public static final int REQUIRED_SITUPS = 100;
    public static final int REQUIRED_SQUATS = 100;
    public static final int REQUIRED_RUNNING = 1000; // blocks

    public void addPushup() { if (pushups < REQUIRED_PUSHUPS) pushups++; }
    public void addSitup() { if (situps < REQUIRED_SITUPS) situps++; }
    public void addSquat() { if (squats < REQUIRED_SQUATS) squats++; }
    public void addRunning(int blocks) { if (running < REQUIRED_RUNNING) running += blocks; }

    public boolean hasCompletedTraining() {
        return pushups >= REQUIRED_PUSHUPS &&
               situps >= REQUIRED_SITUPS &&
               squats >= REQUIRED_SQUATS &&
               running >= REQUIRED_RUNNING;
    }

    public void saveNBT(CompoundTag tag) {
        tag.putInt("Pushups", pushups);
        tag.putInt("Situps", situps);
        tag.putInt("Squats", squats);
        tag.putInt("Running", running);
    }

    public void loadNBT(CompoundTag tag) {
        pushups = tag.getInt("Pushups");
        situps = tag.getInt("Situps");
        squats = tag.getInt("Squats");
        running = tag.getInt("Running");
    }
}
