package com.example.nenmod.net;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;
import net.minecraft.server.level.ServerPlayer;
import java.util.function.Supplier;
import com.example.nenmod.recipe.HatsuRecipeLoader;
import com.example.nenmod.content.PlayerNenProvider;

public class TryCraftHatsuC2S {
    public final String abilityId;
    public TryCraftHatsuC2S(String id) { this.abilityId = id; }
    public static void encode(TryCraftHatsuC2S pkt, FriendlyByteBuf buf) { buf.writeUtf(pkt.abilityId); }
    public static TryCraftHatsuC2S decode(FriendlyByteBuf buf) { return new TryCraftHatsuC2S(buf.readUtf()); }
    public static void handle(TryCraftHatsuC2S pkt, Supplier<NetworkEvent.Context> ctx) {
        var c = ctx.get();
        c.enqueueWork(() -> {
            if (c.getSender() instanceof ServerPlayer sp) {
                var opt = HatsuRecipeLoader.get(pkt.abilityId);
                sp.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
                    if (opt != null && HatsuRecipeLoader.canCraft(sp, cap.get(), opt)) {
                        cap.get().unlockedAbilities.add(pkt.abilityId);
                        sp.displayClientMessage(net.minecraft.network.chat.Component.literal("Unlocked Hatsu: " + pkt.abilityId), false);
                    } else {
                        sp.displayClientMessage(net.minecraft.network.chat.Component.literal("Requirements not met for: " + pkt.abilityId), false);
                    }
                });
            }
        });
        c.setPacketHandled(true);
    }
}
