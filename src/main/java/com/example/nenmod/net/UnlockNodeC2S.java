package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import java.util.function.Supplier;
import com.example.nenmod.skill.SkillTreeLoader;
import com.example.nenmod.content.PlayerNenProvider;

public class UnlockNodeC2S {
    public final String nodeId;
    public UnlockNodeC2S(String id) { this.nodeId = id; }
    public static void encode(UnlockNodeC2S pkt, FriendlyByteBuf buf) { buf.writeUtf(pkt.nodeId); }
    public static UnlockNodeC2S decode(FriendlyByteBuf buf) { return new UnlockNodeC2S(buf.readUtf()); }
    public static void handle(UnlockNodeC2S pkt, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            if (c.getSender() instanceof ServerPlayer sp) {
                var node = SkillTreeLoader.get(pkt.nodeId);
                if (node == null) return;
                sp.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
                    if (!cap.get().unlockedNodes.contains(node.id)) {
                        // Here you would check costs/requirements/affinity/etc.
                        cap.get().unlockedNodes.add(node.id);
                        // Auto-unlock ability if node payload matches ability id
                        if ("hisoka_bungee_gum".equals(node.payload)) {
                            cap.get().unlockedAbilities.add("hisoka_bungee_gum");
                        }
                        sp.displayClientMessage(net.minecraft.network.chat.Component.literal("Unlocked node: " + node.id), false);
                    }
                });
            }
        });
        c.setPacketHandled(true);
    }
}
