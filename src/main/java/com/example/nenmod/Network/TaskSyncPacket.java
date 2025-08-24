package com.example.nenmod.network;

import com.example.nenmod.system.PlayerTaskManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

public class TaskSyncPacket {
    private final Map<String, Integer> progress;
    private final int nenXp;

    public TaskSyncPacket(Map<String, Integer> progress, int nenXp) {
        this.progress = progress;
        this.nenXp = nenXp;
    }

    public static void encode(TaskSyncPacket packet, FriendlyByteBuf buf) {
        buf.writeVarInt(packet.progress.size());
        for (Map.Entry<String, Integer> entry : packet.progress.entrySet()) {
            buf.writeUtf(entry.getKey());
            buf.writeVarInt(entry.getValue());
        }
        buf.writeVarInt(packet.nenXp);
    }

    public static TaskSyncPacket decode(FriendlyByteBuf buf) {
        int size = buf.readVarInt();
        Map<String, Integer> progress = new HashMap<>();
        for (int i = 0; i < size; i++) {
            String key = buf.readUtf();
            int value = buf.readVarInt();
            progress.put(key, value);
        }
        int nenXp = buf.readVarInt();
        return new TaskSyncPacket(progress, nenXp);
    }

    public static void handle(TaskSyncPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            // Update client-side cached task progress
            Minecraft mc = Minecraft.getInstance();
            if (mc.player != null) {
                PlayerTaskManager.get(mc.player).updateFromServer(packet.progress, packet.nenXp);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
