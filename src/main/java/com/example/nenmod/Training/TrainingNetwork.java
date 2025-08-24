package com.yourname.nenmod.training;

import com.yourname.nenmod.nen.NenData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;

import java.util.function.Supplier;

public class TrainingNetwork {
    public static final String PROTOCOL = "1";
    public static SimpleChannel CHANNEL;

    private static int id = 0;
    private static int nextId() { return id++; }

    public static void register() {
        CHANNEL = NetworkRegistry.ChannelBuilder
                .named(new net.minecraft.resources.ResourceLocation("nenmod", "training"))
                .networkProtocolVersion(() -> PROTOCOL)
                .clientAcceptedVersions(PROTOCOL::equals)
                .serverAcceptedVersions(PROTOCOL::equals)
                .simpleChannel();

        CHANNEL.registerMessage(nextId(), DoTrainingC2S.class, DoTrainingC2S::encode, DoTrainingC2S::decode, DoTrainingC2S::handle, Optional.of(NetworkDirection.PLAY_TO_SERVER));
    }

    // -------- Packet --------
    public static class DoTrainingC2S {
        public final TrainingAction action;
        public DoTrainingC2S(TrainingAction a) { this.action = a; }

        public static void encode(DoTrainingC2S msg, FriendlyByteBuf buf) { buf.writeEnum(msg.action); }
        public static DoTrainingC2S decode(FriendlyByteBuf buf) { return new DoTrainingC2S(buf.readEnum(TrainingAction.class)); }

        public static void handle(DoTrainingC2S msg, Supplier<NetworkEvent.Context> ctx) {
            ctx.get().enqueueWork(() -> {
                ServerPlayer player = ctx.get().getSender();
                if (player == null) return;

                player.getCapability(TrainingProvider.TRAINING).ifPresent(train -> {
                    train.tick(); // update cooldown counter
                    if (!train.ready()) return; // anti-spam
                    train.triggerCooldown();

                    // Grant XP only if Nen is unlocked
                    var nen = NenData.get(player);
                    if (nen != null && nen.hasNenUnlocked()) {
                        int xp = switch (msg.action) {
                            case PUSHUP, SITUP, SQUAT -> 1; // minimal per rep
                        };
                        nen.addXp(xp);
                    }

                    // Increment counters
                    switch (msg.action) {
                        case PUSHUP -> train.addPushup();
                        case SITUP  -> train.addSitup();
                        case SQUAT  -> train.addSquat();
                    }
                });
            });
            ctx.get().setPacketHandled(true);
        }
    }
}
