package com.nenmod.network;

import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NenLevelUpS2C {
    public final int newLevel;
    public NenLevelUpS2C(int lvl) { this.newLevel = lvl; }

    public static void encode(NenLevelUpS2C m, FriendlyByteBuf b) { b.writeInt(m.newLevel); }
    public static NenLevelUpS2C decode(FriendlyByteBuf b) { return new NenLevelUpS2C(b.readInt()); }

    public static void handle(NenLevelUpS2C m, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            var mc = Minecraft.getInstance();
            if (mc.player == null) return;
            // Local-only ding
            mc.player.playSound(SoundEvents.PLAYER_LEVELUP, 1.0f, 1.0f);
            // Minimal popup (HUD already exists; this is a tiny message)
            mc.gui.setOverlayMessage(net.minecraft.network.chat.Component.literal("Nen Level Up! (" + m.newLevel + ")")
                    , false);
        });
        ctx.get().setPacketHandled(true);
    }
}
