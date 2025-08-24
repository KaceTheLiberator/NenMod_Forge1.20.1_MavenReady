
package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.client.Minecraft;
import java.util.function.Supplier;

// Minimal placeholder; server could broadcast to clients if needed
public class SyncBungeeS2C {
    public SyncBungeeS2C() {}
    public static void encode(SyncBungeeS2C pkt, FriendlyByteBuf buf) {}
    public static SyncBungeeS2C decode(FriendlyByteBuf buf) { return new SyncBungeeS2C(); }
    public static void handle(SyncBungeeS2C pkt, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().setPacketHandled(true);
    }
}
