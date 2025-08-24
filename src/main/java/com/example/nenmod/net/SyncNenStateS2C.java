
package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.client.Minecraft;
import java.util.function.Supplier;
import java.util.UUID;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SyncNenStateS2C {
    public static class Snapshot {
        public boolean isGyo, isIn;
        public String active;
        public boolean gumA, gumB;
        public boolean gumAToBlock, gumBToBlock;
        public int gumAX, gumAY, gumAZ, gumAEntityId;
        public int gumBX, gumBY, gumBZ, gumBEntityId;
    }
    public static final Map<Integer, Snapshot> CACHE = new ConcurrentHashMap<>();
    public final int playerId;
    public final Snapshot snap;
    public SyncNenStateS2C(int playerId, Snapshot s){ this.playerId=playerId; this.snap=s; }
    public static void encode(SyncNenStateS2C pkt, FriendlyByteBuf buf){
        buf.writeInt(pkt.playerId);
        Snapshot s = pkt.snap;
        buf.writeBoolean(s.isGyo); buf.writeBoolean(s.isIn);
        buf.writeUtf(s.active==null?"":s.active);
        buf.writeBoolean(s.gumA); buf.writeBoolean(s.gumB);
        buf.writeBoolean(s.gumAToBlock); buf.writeBoolean(s.gumBToBlock);
        buf.writeInt(s.gumAX); buf.writeInt(s.gumAY); buf.writeInt(s.gumAZ); buf.writeInt(s.gumAEntityId);
        buf.writeInt(s.gumBX); buf.writeInt(s.gumBY); buf.writeInt(s.gumBZ); buf.writeInt(s.gumBEntityId);
    }
    public static SyncNenStateS2C decode(FriendlyByteBuf buf){
        int id = buf.readInt();
        Snapshot s = new Snapshot();
        s.isGyo = buf.readBoolean(); s.isIn = buf.readBoolean();
        s.active = buf.readUtf();
        s.gumA = buf.readBoolean(); s.gumB = buf.readBoolean();
        s.gumAToBlock = buf.readBoolean(); s.gumBToBlock = buf.readBoolean();
        s.gumAX = buf.readInt(); s.gumAY = buf.readInt(); s.gumAZ = buf.readInt(); s.gumAEntityId = buf.readInt();
        s.gumBX = buf.readInt(); s.gumBY = buf.readInt(); s.gumBZ = buf.readInt(); s.gumBEntityId = buf.readInt();
        return new SyncNenStateS2C(id, s);
    }
    public static void handle(SyncNenStateS2C pkt, Supplier<NetworkEvent.Context> ctx){
        ctx.get().enqueueWork(() -> {
            CACHE.put(pkt.playerId, pkt.snap);
        });
        ctx.get().setPacketHandled(true);
    }
}
