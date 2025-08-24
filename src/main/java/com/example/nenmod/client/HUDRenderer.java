package com.example.nenmod.client;

import com.example.nenmod.PlayerData;
import com.example.nenmod.PlayerDataManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;

@Mod.EventBusSubscriber(modid = "nenmod", bus = Mod.EventBusSubscriber.Bus.MOD, dist = Dist.CLIENT)
public class HUDRenderer {
    private static final Minecraft minecraft = Minecraft.getInstance();

    @SubscribeEvent
    public static void onRenderGameOverlay(RenderGameOverlayEvent.Post event) {
        Player player = minecraft.player;
        if (player != null) {
            PlayerData playerData = PlayerDataManager.getPlayerData(player);
            double currentAura = playerData.getCurrentAura();
            double maxAura = playerData.getMaxAura();
            double strain = playerData.getStrain();

            // Display current and max aura
            minecraft.font.draw(event.getMatrixStack(), "Aura: " + (int)currentAura + "/" + (int)maxAura, 10, 70, 0xFFFFFF);

            // Display strain percentage
            minecraft.font.draw(event.getMatrixStack(), "Strain: " + (int)strain + "%", 10, 90, 0xFFFFFF);

            // Display warning if aura is low (below 20% of max)
            if (currentAura / maxAura < 0.2) {
                minecraft.font.draw(event.getMatrixStack(), "Warning: Low Aura!", 10, 110, 0xFF0000);
            }

            // Display warning if strain is high (above 75%)
            if (strain > 75) {
                minecraft.font.draw(event.getMatrixStack(), "Warning: High Strain!", 10, 130, 0xFF0000);
            }
        }
    }
}
