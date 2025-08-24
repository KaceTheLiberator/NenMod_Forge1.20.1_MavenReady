package com.example.nenmod.network;

import com.example.nenmod.system.PlayerTaskManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class TaskProgressPacket {
    private final String taskName;

    public TaskProgressPacket(String taskName) {
        this.taskName = taskName;
    }

    public static void encode(TaskProgressPacket packet, FriendlyByteBuf buf) {
        buf.writeUtf(packet.taskName);
    }

    public static TaskProgressPacket decode(FriendlyByteBuf buf) {
        return new TaskProgressPacket(buf.readUtf());
    }

    public static void handle(TaskProgressPacket packet, Supplier<NetworkEvent.Context> ctx) {
        ctx.get().enqueueWork(() -> {
            ServerPlayer player = ctx.get().getSender();
            if (player != null) {
                PlayerTaskManager taskManager = PlayerTaskManager.get(player);
                taskManager.performTask(player, packet.taskName);
            }
        });
        ctx.get().setPacketHandled(true);
    }
}
