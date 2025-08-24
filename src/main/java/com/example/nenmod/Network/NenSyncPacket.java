package com.example.nenmod.network;

import com.example.nenmod.nen.NenData;
import com.example.nenmod.nen.NenDataProvider;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NenSyncPacket {
    private final int level;
    private final int xp;
    private final int xpToNext;
    private final boolean unlocked;

    public NenSyncPacket(int level, int xp, int xpToNext, boolean unlocked) {
        this.level = level;
        this.xp = xp;
        this.xpToNext = xpToNext;
        this.unlocked = unlocked;
    }

    public NenSyncPacket(FriendlyByteBuf buf) {
        this.level = buf.readInt();
        this.xp = buf.readInt();
        this.xpToNext = buf.readInt();
        this.unlocked = buf.readBoolean();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeInt(level);
        buf.writeInt(xp);
        buf.writeInt(xpToNext);
        buf.writeBoolean(unlocked);
    }

    public void handle(Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            Player player = net.minecraft.client.Minecraft.getInstance().player;
            if (player != null) {
                NenData nen = NenDataProvider.get(player);
                if (unlocked && !nen.hasUnlockedNen()) nen.unlockNen();
                while (nen.getNenLevel() < level) {
                    nen.addXP(nen.getXPToNextLevel());
                }
                nen.addXP(xp);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
