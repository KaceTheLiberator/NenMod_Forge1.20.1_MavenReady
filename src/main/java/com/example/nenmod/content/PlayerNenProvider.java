package com.example.nenmod.content;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;

public class PlayerNenProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<PlayerNenData> CAP = CapabilityManager.get(new CapabilityToken<>(){});
    private final PlayerNenData data = new PlayerNenData();
    private final LazyOptional<PlayerNenData> opt = LazyOptional.of(() -> data);

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
        return cap == CAP ? opt.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() { return data.save(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { data.load(nbt); }

    public void copyFrom(PlayerNenProvider other) { this.data.copyFrom(other.data); }

    public PlayerNenData get() { return data; }
}
