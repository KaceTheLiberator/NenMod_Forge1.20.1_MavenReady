package com.yourname.nenmod.training;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TrainingProvider implements ICapabilityProvider, INBTSerializable<CompoundTag> {
    public static final Capability<TrainingCapability> TRAINING = CapabilityManager.get(new CapabilityToken<>() {});
    private final TrainingCapability inst = new TrainingCapability();
    private final LazyOptional<TrainingCapability> opt = LazyOptional.of(() -> inst);

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap, @Nullable Direction side) {
        return cap == TRAINING ? opt.cast() : LazyOptional.empty();
    }

    @Override
    public CompoundTag serializeNBT() { return inst.saveNBT(); }

    @Override
    public void deserializeNBT(CompoundTag nbt) { inst.loadNBT(nbt); }
}
