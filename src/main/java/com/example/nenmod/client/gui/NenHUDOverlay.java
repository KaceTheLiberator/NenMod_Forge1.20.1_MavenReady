package com.yourmod.nenmod.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.yourmod.nenmod.player.AuraManager;
import com.yourmod.nenmod.player.NenData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class NenHUDOverlay {

    private static final ResourceLocation BAR_EMPTY = new ResourceLocation("nenmod", "textures/gui/nen_bar_empty.png");
    private static final ResourceLocation BAR_FILL = new ResourceLocation("nenmod", "textures/gui/nen_bar_fill.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        // Get Nen data
        NenData nenData = NenData.get(mc.player);
        if (nenData == null || !nenData.hasUnlockedNen()) return; // Only show if Nen unlocked

        int nenLevel = nenData.getNenLevel();
        int currentAura = AuraManager.getCurrentAura(mc.player);
        int maxAura = AuraManager.getMaxAura(mc.player);

        // Position HUD bottom center
        int screenWidth = event.getWindow().getGuiScaledWidth();
        int screenHeight = event.getWindow().getGuiScaledHeight();

        int barWidth = 100;
        int barHeight = 10;
        int x = (screenWidth / 2) - (barWidth / 2);
        int y = screenHeight - 50;

        PoseStack poseStack = event.getPoseStack();

        // Draw empty bar
        RenderSystem.setShaderTexture(0, BAR_EMPTY);
        event.getGuiGraphics().blit(BAR_EMPTY, x, y, 0, 0, barWidth, barHeight, barWidth, barHeight);

        // Draw fill bar
        int fillWidth = (int) ((currentAura / (float) maxAura) * barWidth);
        RenderSystem.setShaderTexture(0, BAR_FILL);
        event.getGuiGraphics().blit(BAR_FILL, x, y, 0, 0, fillWidth, barHeight, barWidth, barHeight);

        // Draw Nen level above bar (always solid aqua)
        Font font = mc.font;
        String levelText = "Nen Lv. " + nenLevel;
        int textWidth = font.width(levelText);
        int textX = (screenWidth / 2) - (textWidth / 2);
        int textY = y - 12;

        event.getGuiGraphics().drawString(font, levelText, textX, textY, 0x00FFFF, false);
    }
}
