package com.example.nenmod.nen;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class NenDataProvider implements ICapabilityProvider {
    public static final Capability<NenData> NEN_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    private final NenData instance = new NenData();
    private final LazyOptional<NenData> optional = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == NEN_CAP ? optional.cast() : LazyOptional.empty();
    }

    // Save/load to NBT
    public static CompoundTag saveNBT(NenData nen) {
        CompoundTag tag = new CompoundTag();
        tag.putBoolean("Unlocked", nen.hasUnlockedNen());
        tag.putInt("Level", nen.getNenLevel());
        tag.putInt("XP", nen.getNenXP());
        tag.putInt("XPToNext", nen.getXPToNextLevel());
        return tag;
    }

    public static void loadNBT(NenData nen, CompoundTag tag) {
        if (tag.contains("Unlocked")) {
            if (tag.getBoolean("Unlocked")) nen.unlockNen();
        }
        while (nen.getNenLevel() < tag.getInt("Level")) {
            nen.addXP(nen.getXPToNextLevel()); // level sync
        }
        nen.addXP(tag.getInt("XP"));
    }

    public static NenData get(Player player) {
        return player.getCapability(NEN_CAP).orElseThrow(() -> new IllegalStateException("NenData not found!"));
    }
}
