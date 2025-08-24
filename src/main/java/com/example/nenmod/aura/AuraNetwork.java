package com.yourname.nenmod.aura;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.Supplier;

public class AuraNetwork {

    public static class SyncAuraPacket {
        private final int aura;
        private final int maxAura;

        public SyncAuraPacket(int aura, int maxAura) {
            this.aura = aura;
            this.maxAura = maxAura;
        }

        public static void encode(SyncAuraPacket msg, FriendlyByteBuf buf) {
            buf.writeInt(msg.aura);
            buf.writeInt(msg.maxAura);
        }

        public static SyncAuraPacket decode(FriendlyByteBuf buf) {
            return new SyncAuraPacket(buf.readInt(), buf.readInt());
        }

        public static void handle(SyncAuraPacket msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                // Client-side update
                net.minecraft.client.Minecraft.getInstance().player.getCapability(AuraProvider.AURA).ifPresent(cap -> {
                    cap.setAura(msg.aura);
                    cap.setMaxAura(msg.maxAura);
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }

    public static void sendAuraToClient(ServerPlayer player, SimpleChannel channel) {
        player.getCapability(AuraProvider.AURA).ifPresent(cap -> {
            channel.send(PacketDistributor.PLAYER.with(() -> player),
                    new SyncAuraPacket(cap.getAura(), cap.getMaxAura()));
        });
    }
}
