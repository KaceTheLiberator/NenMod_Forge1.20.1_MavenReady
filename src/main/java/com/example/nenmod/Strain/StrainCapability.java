package com.yourname.nenmod.strain;

import net.minecraft.nbt.CompoundTag;

public class StrainCapability {
    private int strain = 0;         // current strain
    private int maxStrain = 100;    // soft cap before overstrain
    private boolean overstrained = false;

    // Tuning
    private int decayPerSecond = 2;     // passive recovery
    private int decayTickBuffer = 0;    // accumulates ticks to 20
    private int lockoutSeconds = 3;     // time forced to stay overstrained after hitting 100%
    private int lockoutTicksLeft = 0;

    public int getStrain() { return strain; }
    public int getMaxStrain() { return maxStrain; }
    public boolean isOverstrained() { return overstrained; }

    public void setMaxStrain(int value) {
        maxStrain = Math.max(10, value);
        if (strain > maxStrain) strain = maxStrain;
    }

    /** Add strain from actions (abilities, sprints, etc.) */
    public void addStrain(int amount) {
        if (amount <= 0) return;
        strain = Math.min(maxStrain, strain + amount);

        // Enter overstrain if we hit the cap
        if (strain >= maxStrain) {
            overstrained = true;
            lockoutTicksLeft = Math.max(lockoutTicksLeft, lockoutSeconds * 20);
        }
    }

    /** Called each player tick (server). Handles decay and lockout. */
    public void tick() {
        // Count down lockout if any
        if (lockoutTicksLeft > 0) {
            lockoutTicksLeft--;
            if (lockoutTicksLeft == 0) {
                // exit hard lock; still considered overstrained until we dip below 80% (hysteresis)
                if (strain < (int)(maxStrain * 0.8f)) {
                    overstrained = false;
                }
            }
            return; // no decay during hard lock
        }

        // Passive recovery: convert 20 ticks into a "second" decay
        decayTickBuffer++;
        if (decayTickBuffer >= 20) {
            decayTickBuffer = 0;

            // Faster recovery if not overstrained
            int decay = decayPerSecond + (!overstrained ? 1 : 0);
            strain = Math.max(0, strain - decay);

            // Leave overstrain when we recover enough (hysteresis threshold 80%)
            if (overstrained && strain < (int)(maxStrain * 0.8f)) {
                overstrained = false;
            }
        }
    }

    /** Force-clear overstrain state (e.g., item/ability cleanses it). */
    public void clearOverstrain() {
        overstrained = false;
        lockoutTicksLeft = 0;
    }

    // NBT
    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Strain", strain);
        tag.putInt("MaxStrain", maxStrain);
        tag.putBoolean("Overstrained", overstrained);
        tag.putInt("DecayPerSecond", decayPerSecond);
        tag.putInt("LockoutTicksLeft", lockoutTicksLeft);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        strain = tag.getInt("Strain");
        maxStrain = tag.getInt("MaxStrain");
        overstrained = tag.getBoolean("Overstrained");
        decayPerSecond = Math.max(0, tag.getInt("DecayPerSecond"));
        lockoutTicksLeft = Math.max(0, tag.getInt("LockoutTicksLeft"));
    }
}
