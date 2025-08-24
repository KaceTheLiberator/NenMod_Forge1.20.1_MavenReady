package com.yourname.nenmod.strain;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class StrainProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<StrainCapability> STRAIN = CapabilityManager.get(new CapabilityToken<>() {});
    private final StrainCapability instance = new StrainCapability();
    private final LazyOptional<StrainCapability> opt = LazyOptional.of(() -> instance);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == STRAIN ? opt.cast() : LazyOptional.empty();
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
