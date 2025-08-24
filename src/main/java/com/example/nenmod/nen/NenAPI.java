package com.nenmod.nen;

import com.nenmod.network.NetworkHandler;
import com.nenmod.network.NenLevelUpS2C;
import com.nenmod.network.NenSyncS2C;
import net.minecraft.server.level.ServerPlayer;

public class NenAPI {
    /** Unlock Nen (Ten learned). Also sync to client. */
    public static void unlockNen(ServerPlayer sp) {
        var nd = NenProvider.get(sp);
        if (!nd.isUnlocked()) {
            nd.unlock();
            NetworkHandler.sendTo(sp, new NenSyncS2C(nd.isUnlocked(), nd.getLevel(), nd.getXp(), nd.getXpToNext()));
        }
    }

    /** Award XP. Handles level-ups + syncing + client feedback packet. */
    public static void addXp(ServerPlayer sp, int amount) {
        var nd = NenProvider.get(sp);
        boolean leveled = nd.addXp(amount);
        NetworkHandler.sendTo(sp, new NenSyncS2C(nd.isUnlocked(), nd.getLevel(), nd.getXp(), nd.getXpToNext()));
        if (leveled) {
            NetworkHandler.sendTo(sp, new NenLevelUpS2C(nd.getLevel()));
        }
    }

    // Convenience getters for HUD
    public static boolean isUnlocked(ServerPlayer sp) { return NenProvider.get(sp).isUnlocked(); }
    public static int getLevel(ServerPlayer sp) { return NenProvider.get(sp).getLevel(); }
    public static int getXp(ServerPlayer sp) { return NenProvider.get(sp).getXp(); }
    public static int getXpToNext(ServerPlayer sp) { return NenProvider.get(sp).getXpToNext(); }
}
