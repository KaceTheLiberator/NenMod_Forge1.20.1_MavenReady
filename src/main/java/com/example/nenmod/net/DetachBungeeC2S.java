
package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import java.util.function.Supplier;
import com.example.nenmod.content.PlayerNenProvider;

public class DetachBungeeC2S {
    public final int slot; // 0=A,1=B; -1=all
    public DetachBungeeC2S(int slot){ this.slot=slot; }
    public DetachBungeeC2S(){ this(-1); }
    public DetachBungeeC2S() {}
    public static void encode(DetachBungeeC2S pkt, FriendlyByteBuf buf) { buf.writeInt(pkt.slot); }
    public static DetachBungeeC2S decode(FriendlyByteBuf buf) { return new DetachBungeeC2S(buf.readInt()); }
    public static void handle(DetachBungeeC2S pkt, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            ServerPlayer sp = c.getSender();
            if (sp == null) return;
            sp.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
                var data = cap.get();
                if (pkt.slot < 0 || pkt.slot == 0) { data.gumAttachedA = false; data.gumAEntityId = -1; }
                if (pkt.slot < 0 || pkt.slot == 1) { data.gumAttachedB = false; data.gumBEntityId = -1; }
            });
        });
        c.setPacketHandled(true);
    }
}
