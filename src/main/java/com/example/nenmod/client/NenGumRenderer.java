
package com.example.nenmod.client;

import com.example.nenmod.net.SyncNenStateS2C;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLevelStageEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.Tesselator;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.vertex.DefaultVertexFormat;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;

@Mod.EventBusSubscriber(modid="nenmod", value=Dist.CLIENT)
public class NenGumRenderer {

    @SubscribeEvent
    public static void onRender(RenderLevelStageEvent e){
        if (e.getStage() != RenderLevelStageEvent.Stage.AFTER_TRANSLUCENT) return;
        var mc = Minecraft.getInstance();
        if (mc.level == null || mc.player == null) return;

        PoseStack pose = e.getPoseStack();
        Vec3 cam = e.getCamera().getPosition();
        var buffer = Tesselator.getInstance().getBuilder();

        RenderSystem.setShader(GameRenderer::getPositionColorShader);
        RenderSystem.disableDepthTest();
        buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR);

        for (var entry : SyncNenStateS2C.CACHE.entrySet()) {
            int id = entry.getKey();
            Entity caster = mc.level.getEntity(id);
            if (!(caster instanceof Player)) continue;
            Player casterP = (Player) caster;
            SyncNenStateS2C.Snapshot s = entry.getValue();

            // Only render if the viewer has Gyo and the caster isn't using In
            var ability = new com.example.nenmod.ability.Ability(); // placeholder flags
            ability.visibility = "INVISIBLE";
            ability.canBeConcealed = true;
            if (!NenRender.shouldRender(ability, mc.player, casterP)) continue;

            // Slot A
            if (s.gumA) {
                Vec3 start = casterP.position();
                Vec3 end;
                if (s.gumAToBlock) {
                    end = new Vec3(s.gumAX + 0.5, s.gumAY + 0.5, s.gumAZ + 0.5);
                } else {
                    Entity t = mc.level.getEntity(s.gumAEntityId);
                    if (t == null) end = start;
                    else end = t.position();
                }
                putLine(buffer, pose, cam, start, end);
            }
            // Slot B
            if (s.gumB) {
                Vec3 start = casterP.position();
                Vec3 end;
                if (s.gumBToBlock) {
                    end = new Vec3(s.gumBX + 0.5, s.gumBY + 0.5, s.gumBZ + 0.5);
                } else {
                    Entity t = mc.level.getEntity(s.gumBEntityId);
                    if (t == null) end = start;
                    else end = t.position();
                }
                putLine(buffer, pose, cam, start, end);
            }
        }

        Tesselator.getInstance().end();
        RenderSystem.enableDepthTest();
    }

    private static void putLine(BufferBuilder buffer, PoseStack pose, Vec3 cam, Vec3 a, Vec3 b){
        var m = pose.last().pose();
        buffer.vertex(m, (float)(a.x - cam.x), (float)(a.y - cam.y), (float)(a.z - cam.z)).color(255,120,200,180).endVertex();
        buffer.vertex(m, (float)(b.x - cam.x), (float)(b.y - cam.y), (float)(b.z - cam.z)).color(255,120,200,180).endVertex();
    }
}
