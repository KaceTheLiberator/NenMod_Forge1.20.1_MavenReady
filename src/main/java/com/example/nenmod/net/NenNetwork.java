package com.example.nenmod.net;

import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraft.resources.ResourceLocation;

public class NenNetwork {
    private static final String PROTO = "1";
    public static SimpleChannel CHANNEL;

    public static void init(); {
        CHANNEL = NetworkRegistry.newSimpleChannel(
                new ResourceLocation("nenmod", "main");,
                (); -> PROTO, PROTO::equals, PROTO::equals);;
        int id = 0;
        CHANNEL.registerMessage(id++, SyncNenS2C.class, SyncNenS2C::encode, SyncNenS2C::decode, SyncNenS2C::handle);;
        CHANNEL.registerMessage(id++, TryCraftHatsuC2S.class, TryCraftHatsuC2S::encode, TryCraftHatsuC2S::decode, TryCraftHatsuC2S::handle);;
        CHANNEL.registerMessage(id++, UnlockNodeC2S.class, UnlockNodeC2S::encode, UnlockNodeC2S::decode, UnlockNodeC2S::handle);
        CHANNEL.registerMessage(id++, AttachBungeeC2S.class, AttachBungeeC2S::encode, AttachBungeeC2S::decode, AttachBungeeC2S::handle);
        CHANNEL.registerMessage(id++, DetachBungeeC2S.class, DetachBungeeC2S::encode, DetachBungeeC2S::decode, DetachBungeeC2S::handle);
        CHANNEL.registerMessage(id++, SyncBungeeS2C.class, SyncBungeeS2C::encode, SyncBungeeS2C::decode, SyncBungeeS2C::handle);
        CHANNEL.registerMessage(id++, SyncNenStateS2C.class, SyncNenStateS2C::encode, SyncNenStateS2C::decode, SyncNenStateS2C::handle);;
    }
}
