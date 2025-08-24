package com.example.nenmod.client;

import com.example.nenmod.system.PlayerTaskManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(modid = "nenmod", value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class KeybindHandler {

    private static KeyMapping pushupKey;
    private static KeyMapping situpKey;
    private static KeyMapping squatKey;

    private static final PlayerTaskManager taskManager = new PlayerTaskManager();

    public static void register(RegisterKeyMappingsEvent event) {
        pushupKey = new KeyMapping("key.nenmod.pushup", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_P, "key.categories.nenmod");
        situpKey = new KeyMapping("key.nenmod.situp", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_O, "key.categories.nenmod");
        squatKey = new KeyMapping("key.nenmod.squat", KeyConflictContext.IN_GAME, GLFW.GLFW_KEY_I, "key.categories.nenmod");

        event.register(pushupKey);
        event.register(situpKey);
        event.register(squatKey);

        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
    }

    private static class KeyInputHandler {
        @SubscribeEvent
        public void onClientTick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;

            LocalPlayer player = Minecraft.getInstance().player;
            if (player == null) return;

            if (pushupKey.consumeClick()) {
                taskManager.performTask(player, "pushup");
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§eYou did a push-up!"));
            }
            if (situpKey.consumeClick()) {
                taskManager.performTask(player, "situp");
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§eYou did a sit-up!"));
            }
            if (squatKey.consumeClick()) {
                taskManager.performTask(player, "squat");
                player.sendSystemMessage(net.minecraft.network.chat.Component.literal("§eYou did a squat!"));
            }
        }
    }
}
