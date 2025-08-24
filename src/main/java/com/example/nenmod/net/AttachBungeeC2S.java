
package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import java.util.function.Supplier;
import com.example.nenmod.content.PlayerNenProvider;

public class AttachBungeeC2S {
    public final boolean toBlock;
    public final int x,y,z;
    public final int entityId;
    public AttachBungeeC2S(int entityId) { this.toBlock=false; this.entityId=entityId; this.x=this.y=this.z=0; }
    public AttachBungeeC2S(int x,int y,int z) { this.toBlock=true; this.x=x; this.y=y; this.z=z; this.entityId=-1; }

    public static void encode(AttachBungeeC2S pkt, FriendlyByteBuf buf) {
        buf.writeBoolean(pkt.toBlock);
        buf.writeInt(pkt.x); buf.writeInt(pkt.y); buf.writeInt(pkt.z);
        buf.writeInt(pkt.entityId);
    }
    public static AttachBungeeC2S decode(FriendlyByteBuf buf) {
        boolean tb = buf.readBoolean();
        int x = buf.readInt(), y = buf.readInt(), z = buf.readInt();
        int eid = buf.readInt();
        return tb ? new AttachBungeeC2S(x,y,z) : new AttachBungeeC2S(eid);
    }
    public static void handle(AttachBungeeC2S pkt, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            ServerPlayer sp = c.getSender();
            if (sp == null) return;
            sp.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
                var data = cap.get();
                // Only if ability active
                if (!"hisoka_bungee_gum".equals(data.activeAbility)) return;
                boolean useA = !data.gumAttachedA;
                if (useA) data.gumAttachedA = true; else data.gumAttachedB = true;
                if (useA) data.gumAToBlock = pkt.toBlock; else data.gumBToBlock = pkt.toBlock;
                if (pkt.toBlock) {
                    if (useA) { data.gumAX = pkt.x; data.gumAY = pkt.y; data.gumAZ = pkt.z; data.gumAEntityId = -1; } else { data.gumBX = pkt.x; data.gumBY = pkt.y; data.gumBZ = pkt.z; data.gumBEntityId = -1; }
                } else {
                    if (useA) data.gumAEntityId = pkt.entityId; else data.gumBEntityId = pkt.entityId;
                }
            });
        });
        c.setPacketHandled(true);
    }
}
