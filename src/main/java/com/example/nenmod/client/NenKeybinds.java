
package com.example.nenmod.client;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.KeyMapping;
import org.lwjgl.glfw.GLFW;
import com.example.nenmod.net.DetachBungeeC2S;
import com.example.nenmod.net.NenNetwork;
import net.minecraft.client.Minecraft;

@Mod.EventBusSubscriber(modid="nenmod", value=Dist.CLIENT, bus=Mod.EventBusSubscriber.Bus.MOD)
public class NenKeybinds {
    public static KeyMapping DETACH_ALL;
    public static KeyMapping TOGGLE_GYO;
    public static KeyMapping TOGGLE_IN;
    public static KeyMapping TOGGLE_ZETSU;

    @SubscribeEvent
    public static void register(RegisterKeyMappingsEvent e){
        DETACH_ALL = new KeyMapping("key.nenmod.detach_all", GLFW.GLFW_KEY_V, "key.categories.nenmod");
        TOGGLE_GYO = new KeyMapping("key.nenmod.toggle_gyo", GLFW.GLFW_KEY_G, "key.categories.nenmod");
        TOGGLE_IN = new KeyMapping("key.nenmod.toggle_in", GLFW.GLFW_KEY_H, "key.categories.nenmod");
        TOGGLE_ZETSU = new KeyMapping("key.nenmod.toggle_zetsu", GLFW.GLFW_KEY_Z, "key.categories.nenmod");
        e.register(DETACH_ALL);
        e.register(TOGGLE_GYO);
        e.register(TOGGLE_IN);
        e.register(TOGGLE_ZETSU);
    }
}

@Mod.EventBusSubscriber(modid="nenmod", value=Dist.CLIENT)
class NenClientInput {
    @SubscribeEvent
    public static void onKey(InputEvent.Key event){
        if (NenKeybinds.DETACH_ALL != null && NenKeybinds.DETACH_ALL.consumeClick()) {
            NenNetwork.CHANNEL.sendToServer(new DetachBungeeC2S(-1));
        }
        var mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (NenKeybinds.TOGGLE_GYO != null && NenKeybinds.TOGGLE_GYO.consumeClick()) {
            mc.player.connection.sendCommand("nen gyo");
        }
        if (NenKeybinds.TOGGLE_IN != null && NenKeybinds.TOGGLE_IN.consumeClick()) {
            mc.player.connection.sendCommand("nen in");
        }
        if (NenKeybinds.TOGGLE_ZETSU != null && NenKeybinds.TOGGLE_ZETSU.consumeClick()) {
            mc.player.connection.sendCommand("nen zetsu");
        }
    }
}
