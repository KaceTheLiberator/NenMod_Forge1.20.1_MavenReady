package com.yourname.nenmod.aura;

import net.minecraft.nbt.CompoundTag;

public class AuraCapability {
    private int aura = 100;
    private int maxAura = 100;
    private int regenRate = 1;
    private boolean exhausted = false;

    public int getAura() {
        return aura;
    }

    public void setAura(int value) {
        this.aura = Math.max(0, Math.min(value, maxAura));
        if (aura == 0) exhausted = true;
    }

    public void addAura(int value) {
        setAura(this.aura + value);
    }

    public int getMaxAura() {
        return maxAura;
    }

    public void setMaxAura(int value) {
        this.maxAura = Math.max(1, value);
        if (aura > maxAura) aura = maxAura;
    }

    public int getRegenRate() {
        return regenRate;
    }

    public void setRegenRate(int value) {
        this.regenRate = Math.max(0, value);
    }

    public boolean isExhausted() {
        return exhausted;
    }

    public void setExhausted(boolean exhausted) {
        this.exhausted = exhausted;
    }

    public void regen() {
        if (!exhausted && aura < maxAura) {
            addAura(regenRate);
        }
    }

    public CompoundTag saveNBT() {
        CompoundTag tag = new CompoundTag();
        tag.putInt("Aura", aura);
        tag.putInt("MaxAura", maxAura);
        tag.putInt("RegenRate", regenRate);
        tag.putBoolean("Exhausted", exhausted);
        return tag;
    }

    public void loadNBT(CompoundTag tag) {
        this.aura = tag.getInt("Aura");
        this.maxAura = tag.getInt("MaxAura");
        this.regenRate = tag.getInt("RegenRate");
        this.exhausted = tag.getBoolean("Exhausted");
    }
}
