package com.nenmod.hatsu;

import com.nenmod.nen.PlayerNenData;
import net.minecraft.world.entity.player.Player;

public class HatsuUnlockManager {

    private static final int MAX_HATSUS = 5;
    private static final int LEVELS_PER_SLOT = 10;

    public static boolean canUnlockNewHatsu(Player player) {
        PlayerNenData data = PlayerNenData.get(player);

        int currentHatsus = data.getUnlockedHatsus().size();
        if (currentHatsus >= MAX_HATSUS) return false;

        int allowedSlots = data.getNenLevel() / LEVELS_PER_SLOT;
        return currentHatsus < allowedSlots;
    }

    public static boolean unlockHatsu(Player player, String hatsuId) {
        PlayerNenData data = PlayerNenData.get(player);

        if (!canUnlockNewHatsu(player)) {
            return false; // not enough slots
        }

        if (!data.getUnlockedHatsus().contains(hatsuId)) {
            data.getUnlockedHatsus().add(hatsuId);
            return true;
        }
        return false;
    }
}
