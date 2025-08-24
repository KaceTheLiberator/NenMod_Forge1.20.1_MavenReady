package com.yourmod.training;

import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.client.event.InputEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.lwjgl.glfw.GLFW;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class TrainingKeybinds {

    public static KeyMapping keyPushup;
    public static KeyMapping keySitup;
    public static KeyMapping keySquat;

    @SubscribeEvent
    public static void registerKeys(RegisterKeyMappingsEvent event) {
        keyPushup = new KeyMapping("key.yourmod.pushup", GLFW.GLFW_KEY_P, "key.categories.yourmod");
        keySitup = new KeyMapping("key.yourmod.situp", GLFW.GLFW_KEY_O, "key.categories.yourmod");
        keySquat = new KeyMapping("key.yourmod.squat", GLFW.GLFW_KEY_I, "key.categories.yourmod");

        event.register(keyPushup);
        event.register(keySitup);
        event.register(keySquat);
    }

    @Mod.EventBusSubscriber
    public static class KeyInputHandler {
        @SubscribeEvent
        public static void onKeyPress(InputEvent.Key event) {
            if (keyPushup.consumeClick()) {
                // Increment player’s pushup count
                // TODO: Hook into PlayerTaskManager capability
            }
            if (keySitup.consumeClick()) {
                // Increment situp count
            }
            if (keySquat.consumeClick()) {
                // Increment squat count
            }
        }
    }
}
