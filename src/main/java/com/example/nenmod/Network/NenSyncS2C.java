package com.nenmod.network;

import com.nenmod.nen.NenProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NenSyncS2C {
    public final boolean unlocked;
    public final int level, xp, xpToNext;

    public NenSyncS2C(boolean unlocked, int level, int xp, int xpToNext) {
        this.unlocked = unlocked; this.level = level; this.xp = xp; this.xpToNext = xpToNext;
    }

    public static void encode(NenSyncS2C m, FriendlyByteBuf b) {
        b.writeBoolean(m.unlocked); b.writeInt(m.level); b.writeInt(m.xp); b.writeInt(m.xpToNext);
    }
    public static NenSyncS2C decode(FriendlyByteBuf b) {
        return new NenSyncS2C(b.readBoolean(), b.readInt(), b.readInt(), b.readInt());
    }

    public static void handle(NenSyncS2C m, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var p = Minecraft.getInstance().player;
            if (p == null) return;
            var nd = NenProvider.get(p);
            // Apply values
            // We avoid running level-up logic here; we just set.
            // Quick way: load NBT-like
            var tag = new net.minecraft.nbt.CompoundTag();
            tag.putBoolean("Unlocked", m.unlocked);
            tag.putInt("Level", m.level);
            tag.putInt("XP", m.xp);
            nd.load(tag);
            // (xpToNext is derived client-side)
        });
        ctx.get().setPacketHandled(true);
    }
}
