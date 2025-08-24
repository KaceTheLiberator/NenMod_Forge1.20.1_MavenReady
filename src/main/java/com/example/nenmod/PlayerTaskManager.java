package com.example.nenmod;

import org.bukkit.entity.Player;
import java.util.HashMap;
import java.util.Map;

public class PlayerTaskManager {
    // Track player's physical task progress
    public static final Map<Player, Integer> runDistance = new HashMap<>();
    public static final Map<Player, Integer> sitUps = new HashMap<>();
    public static final Map<Player, Integer> pushUps = new HashMap<>();
    public static final Map<Player, Integer> squats = new HashMap<>();

    // Task completion thresholds
    private static final int RUN_DISTANCE = 1000;  // 1000 blocks
    private static final int SIT_UPS = 100;
    private static final int PUSH_UPS = 100;
    private static final int SQUATS = 100;

    // Update the player's progress for physical tasks
    public static void updateProgress(Player player, String task) {
        switch (task) {
            case "RUN":
                updateRunDistance(player, 10);  // 10 blocks run for each key press
                break;
            case "SIT_UP":
                updateSitUps(player, 1);
                break;
            case "PUSH_UP":
                updatePushUps(player, 1);
                break;
            case "SQUAT":
                updateSquats(player, 1);
                break;
        }

        // Unlock Ten when all tasks are completed
        if (runDistance.getOrDefault(player, 0) >= RUN_DISTANCE
            && sitUps.getOrDefault(player, 0) >= SIT_UPS
            && pushUps.getOrDefault(player, 0) >= PUSH_UPS
            && squats.getOrDefault(player, 0) >= SQUATS) {
            NenUnlockManager.unlockTen(player);
        }
    }

    // Update the run distance progress
    public static void updateRunDistance(Player player, int distance) {
        int currentDistance = runDistance.getOrDefault(player, 0);
        runDistance.put(player, currentDistance + distance);
    }

    // Update the sit-ups progress
    public static void updateSitUps(Player player, int count) {
        int currentSitUps = sitUps.getOrDefault(player, 0);
        sitUps.put(player, currentSitUps + count);
    }

    // Update the push-ups progress
    public static void updatePushUps(Player player, int count) {
        int currentPushUps = pushUps.getOrDefault(player, 0);
        pushUps.put(player, currentPushUps + count);
    }

    // Update the squats progress
    public static void updateSquats(Player player, int count) {
        int currentSquats = squats.getOrDefault(player, 0);
        squats.put(player, currentSquats + count);
    }

    // Check if all tasks are completed
    public static boolean checkTasksCompleted(Player player) {
        return runDistance.getOrDefault(player, 0) >= RUN_DISTANCE
            && sitUps.getOrDefault(player, 0) >= SIT_UPS
            && pushUps.getOrDefault(player, 0) >= PUSH_UPS
            && squats.getOrDefault(player, 0) >= SQUATS;
    }
}
