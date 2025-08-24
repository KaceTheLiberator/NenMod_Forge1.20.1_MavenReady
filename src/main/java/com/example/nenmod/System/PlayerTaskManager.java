package com.example.nenmod.system;

import com.example.nenmod.events.NenXpEvents;
import net.minecraft.server.level.ServerPlayer;

import java.util.HashMap;
import java.util.Map;

public class PlayerTaskManager {

    private static final Map<String, Integer> TASK_REQUIREMENTS = new HashMap<>();

    static {
        TASK_REQUIREMENTS.put("pushup", 100);
        TASK_REQUIREMENTS.put("situp", 100);
        TASK_REQUIREMENTS.put("squat", 100);
        TASK_REQUIREMENTS.put("running", 1000); // tracked separately with distance
    }

    private final Map<String, Integer> progress = new HashMap<>();

    public void performTask(ServerPlayer player, String task) {
        int current = progress.getOrDefault(task, 0);
        int required = TASK_REQUIREMENTS.getOrDefault(task, 0);

        if (current < required) {
            progress.put(task, current + 1);

            // ✅ Hook into NenXp system
            NenXpEvents.onPhysicalTask(player, task);

            // Unlock notification
            if (progress.get(task) >= required) {
                player.sendSystemMessage(
                        net.minecraft.network.chat.Component.literal("§aTask completed: " + task)
                );
            }
        }
    }

    public boolean isTaskComplete(String task) {
        return progress.getOrDefault(task, 0) >= TASK_REQUIREMENTS.getOrDefault(task, 0);
    }

    public boolean allTasksComplete() {
        for (String task : TASK_REQUIREMENTS.keySet()) {
            if (!isTaskComplete(task)) {
                return false;
            }
        }
        return true;
    }
}
