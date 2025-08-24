package com.yourname.nenmod.training;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class TrainingKeybinds {
    public static KeyMapping PUSHUPS;
    public static KeyMapping SITUPS;
    public static KeyMapping SQUATS;

    @SubscribeEvent
    public static void onRegisterKeys(RegisterKeyMappingsEvent e) {
        PUSHUPS = new KeyMapping("key.nenmod.pushups", InputConstants.KEY_P, "key.categories.nenmod");
        SITUPS  = new KeyMapping("key.nenmod.situps",  InputConstants.KEY_O, "key.categories.nenmod");
        SQUATS  = new KeyMapping("key.nenmod.squats",  InputConstants.KEY_I, "key.categories.nenmod");
        e.register(PUSHUPS);
        e.register(SITUPS);
        e.register(SQUATS);
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        var mc = net.minecraft.client.Minecraft.getInstance();
        if (mc.player == null) return;

        while (PUSHUPS.consumeClick()) {
            TrainingNetwork.CHANNEL.sendToServer(new TrainingNetwork.DoTrainingC2S(TrainingAction.PUSHUP));
        }
        while (SITUPS.consumeClick()) {
            TrainingNetwork.CHANNEL.sendToServer(new TrainingNetwork.DoTrainingC2S(TrainingAction.SITUP));
        }
        while (SQUATS.consumeClick()) {
            TrainingNetwork.CHANNEL.sendToServer(new TrainingNetwork.DoTrainingC2S(TrainingAction.SQUAT));
        }
    }
}
