package com.example.nenmod;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;

public class PlayerDataManager {

    // Get player data (example for loading)
    public static PlayerData getPlayerData(Player player) {
        CompoundTag tag = player.getPersistentData();
        if (!tag.contains("PlayerData")) {
            return new PlayerData();
        }
        CompoundTag playerDataTag = tag.getCompound("PlayerData");
        PlayerData playerData = new PlayerData();
        playerData.loadData(playerDataTag);
        return playerData;
    }

    // Save player data (example for saving)
    public static void savePlayerData(Player player, PlayerData playerData) {
        CompoundTag tag = player.getPersistentData();
        CompoundTag playerDataTag = new CompoundTag();
        playerData.saveData(playerDataTag);
        tag.put("PlayerData", playerDataTag);
    }
}
