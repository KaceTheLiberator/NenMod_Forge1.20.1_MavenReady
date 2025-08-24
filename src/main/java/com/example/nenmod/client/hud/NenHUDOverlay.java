package com.yourmod.client.hud;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.yourmod.player.NenData;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class NenHUDOverlay {

    private static final ResourceLocation AURA_BAR = new ResourceLocation("yourmod", "textures/gui/aura_bar.png");
    private static final ResourceLocation XP_BAR = new ResourceLocation("yourmod", "textures/gui/xp_bar.png");

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        NenData data = NenData.get(mc.player);
        if (!data.hasUnlockedNen()) return; // Only render if Nen unlocked

        PoseStack poseStack = event.getPoseStack();
        GuiGraphics gui = event.getGuiGraphics();

        int screenWidth = mc.getWindow().getGuiScaledWidth();
        int screenHeight = mc.getWindow().getGuiScaledHeight();

        // --- Aura Bar ---
        int auraWidth = 100;
        int auraHeight = 10;
        int auraX = screenWidth / 2 - auraWidth / 2;
        int auraY = screenHeight - 50;

        float auraPercent = (float) data.getCurrentAura() / data.getMaxAura();
        int auraFill = (int) (auraWidth * auraPercent);

        RenderSystem.setShaderTexture(0, AURA_BAR);
        gui.blit(AURA_BAR, auraX, auraY, 0, 0, auraWidth, auraHeight);
        gui.blit(AURA_BAR, auraX, auraY, 0, auraHeight, auraFill, auraHeight);

        // --- XP Bar ---
        int xpWidth = 100;
        int xpHeight = 8;
        int xpX = screenWidth / 2 - xpWidth / 2;
        int xpY = auraY - 15;

        float xpPercent = (float) data.getCurrentXP() / data.getXpForNextLevel();
        int xpFill = (int) (xpWidth * xpPercent);

        RenderSystem.setShaderTexture(0, XP_BAR);
        gui.blit(XP_BAR, xpX, xpY, 0, 0, xpWidth, xpHeight);
        gui.blit(XP_BAR, xpX, xpY, 0, xpHeight, xpFill, xpHeight);

        // --- Nen Level Number ---
        String levelText = "Lvl " + data.getNenLevel();
        gui.drawString(mc.font, levelText, screenWidth / 2 - mc.font.width(levelText) / 2, xpY - 12, 0x00FFFF, true);
    }
}
