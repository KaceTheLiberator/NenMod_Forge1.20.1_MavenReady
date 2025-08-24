package com.nenmod.nen;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.capabilities.*;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class NenProvider implements ICapabilityProvider, ICapabilitySerializable<CompoundTag> {
    public static final Capability<NenData> NEN_CAP = CapabilityManager.get(new CapabilityToken<>() {});
    private final NenData data = new NenData();
    private final LazyOptional<NenData> opt = LazyOptional.of(() -> data);

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        return cap == NEN_CAP ? opt.cast() : LazyOptional.empty();
    }

    @Override public CompoundTag serializeNBT() { return data.save(); }
    @Override public void deserializeNBT(CompoundTag nbt) { data.load(nbt); }

    public static NenData get(Player player) {
        return player.getCapability(NEN_CAP).orElseThrow(() -> new IllegalStateException("NenData missing"));
    }
}
