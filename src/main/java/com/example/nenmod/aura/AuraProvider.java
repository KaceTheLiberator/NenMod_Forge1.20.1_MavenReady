package com.yourname.nenmod.aura;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.capabilities.CapabilityToken;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class AuraProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<AuraCapability> AURA = CapabilityManager.get(new CapabilityToken<>() {});
    private AuraCapability instance = new AuraCapability();

    @Nonnull
    @Override
    public <T> net.minecraftforge.common.util.LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == AURA ? net.minecraftforge.common.util.LazyOptional.of(() -> instance).cast() : net.minecraftforge.common.util.LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() {
        return instance.saveNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.loadNBT(nbt);
    }
}
