package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import java.util.function.Supplier;

public class SyncNenS2C {
    public SyncNenS2C() { }
    public static void encode(SyncNenS2C pkt, FriendlyByteBuf buf) {}
    public static SyncNenS2C decode(FriendlyByteBuf buf) { return new SyncNenS2C(); }
    public static void handle(SyncNenS2C pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
    }
}
