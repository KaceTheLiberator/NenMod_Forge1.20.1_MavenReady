package com.nenmod.network;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class NetworkHandler {
    private static final String PROTO = "1";
    private static int id = 0;

    public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation("nenmod", "main"),
            () -> PROTO, PROTO::equals, PROTO::equals
    );

    public static void register() {
        CHANNEL.registerMessage(id++, NenSyncS2C.class, NenSyncS2C::encode, NenSyncS2C::decode, NenSyncS2C::handle);
        CHANNEL.registerMessage(id++, NenLevelUpS2C.class, NenLevelUpS2C::encode, NenLevelUpS2C::decode, NenLevelUpS2C::handle);
    }

    public static void sendTo(ServerPlayer sp, Object pkt) {
        CHANNEL.send(PacketDistributor.PLAYER.with(() -> sp), pkt);
    }
}
