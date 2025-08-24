package com.example.nenmod;

import net.minecraft.nbt.CompoundTag;

public class PlayerData {
    private int nenLevel;
    private int xp;
    private int skillPoints;
    private double currentAura;
    private double maxAura;
    private double strain;  // Track the player's strain

    public PlayerData() {
        this.nenLevel = 1;
        this.xp = 0;
        this.skillPoints = 0;
        this.currentAura = 100.0;
        this.maxAura = 100.0;
        this.strain = 0.0;  // Default strain
    }

    // Getters and setters
    public double getCurrentAura() {
        return currentAura;
    }

    public void setCurrentAura(double currentAura) {
        this.currentAura = currentAura;
    }

    public double getMaxAura() {
        return maxAura;
    }

    public void setMaxAura(double maxAura) {
        this.maxAura = maxAura;
    }

    public double getStrain() {
        return strain;
    }

    public void setStrain(double strain) {
        this.strain = strain;
    }

    // Add aura (with a cap on maxAura)
    public void addAura(double auraAmount) {
        this.currentAura = Math.min(this.currentAura + auraAmount, maxAura);
    }

    // Subtract aura (not going below 0)
    public void subtractAura(double auraAmount) {
        this.currentAura = Math.max(this.currentAura - auraAmount, 0);
    }

    // Increase strain based on ability usage
    public void increaseStrain(double amount) {
        this.strain = Math.min(this.strain + amount, 100.0); // Strain shouldn't exceed 100%
    }

    // Reset strain over time (e.g., every 10 seconds)
    public void decreaseStrain() {
        if (this.strain > 0) {
            this.strain -= 0.5;  // Reduce strain by a small amount over time
        }
    }

    // Save player data to NBT
    public void saveData(CompoundTag tag) {
        tag.putInt("nenLevel", nenLevel);
        tag.putInt("xp", xp);
        tag.putInt("skillPoints", skillPoints);
        tag.putDouble("currentAura", currentAura);
        tag.putDouble("maxAura", maxAura);
        tag.putDouble("strain", strain);  // Save strain
    }

    // Load player data from NBT
    public void loadData(CompoundTag tag) {
        this.nenLevel = tag.getInt("nenLevel");
        this.xp = tag.getInt("xp");
        this.skillPoints = tag.getInt("skillPoints");
        this.currentAura = tag.getDouble("currentAura");
        this.maxAura = tag.getDouble("maxAura");
        this.strain = tag.getDouble("strain");  // Load strain
    }
}
