package com.example.nenmod;

import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.event.TickEvent;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec3;
import com.example.nenmod.content.PlayerNenProvider;
import com.example.nenmod.net.AttachBungeeC2S;
import com.example.nenmod.net.SyncNenStateS2C;
import net.minecraftforge.network.PacketDistributor;
import com.example.nenmod.net.DetachBungeeC2S;
import com.example.nenmod.net.NenNetwork;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import com.example.nenmod.command.NenCommands;

@Mod.EventBusSubscriber(modid="nenmod")

    @SubscribeEvent
    public static void onRightClickBlock(PlayerInteractEvent.RightClickBlock e) {
        Player p = e.getEntity();
        p.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
            var d = cap.get();
            if ("hisoka_bungee_gum".equals(d.activeAbility)) {
                var pos = e.getPos();
                NenNetwork.CHANNEL.sendToServer(new AttachBungeeC2S(pos.getX(), pos.getY(), pos.getZ()));
                e.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onRightClickEntity(PlayerInteractEvent.EntityInteract e) {
        Player p = e.getEntity();
        p.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
            var d = cap.get();
            if ("hisoka_bungee_gum".equals(d.activeAbility)) {
                NenNetwork.CHANNEL.sendToServer(new AttachBungeeC2S(e.getTarget().getId()));
                e.setCanceled(true);
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent e) {
        if (e.phase != TickEvent.Phase.END) return;
        Player p = e.player;
        p.getCapability(PlayerNenProvider.CAP).ifPresent(cap -> {
            var d = cap.get();

            // Nen drains (scaled by mastery, 0..100 -> 0.5x at max)
            float masteryFactorTen = (1f - d.masteryTen/200f);
            float masteryFactorGyo = (1f - d.masteryGyo/200f);
            float masteryFactorIn  = (1f - d.masteryIn/200f);

            if (!d.isZetsuActive) {
                int drain = 0;
                if (d.isTenActive) drain += Math.round(1 * masteryFactorTen);
                if (d.isGyoActive) drain += Math.round(2 * masteryFactorGyo);
                if (d.isInActive)  drain += Math.round(3 * masteryFactorIn);
                if (drain > 0 && e.player.level().getGameTime() % 20L == 0L) { // per second
                    d.nen = Math.max(0, d.nen - drain);
                }
            }
            // Mastery growth (tiny per tick when active)
            if (d.isTenActive) d.masteryTen = Math.min(100f, d.masteryTen + 0.001f);
            if (d.isGyoActive) d.masteryGyo = Math.min(100f, d.masteryGyo + 0.002f);
            if (d.isInActive)  d.masteryIn  = Math.min(100f, d.masteryIn  + 0.003f);

            // Auto-drop In/Gyo/Ten if nen depleted
            if (d.nen <= 0) { d.isInActive=false; d.isGyoActive=false; d.isTenActive=false; d.activeAbility=""; d.gumAttached=false; }

                        // Send periodic state snapshot to clients
            if (!p.level().isClientSide && p.level().getGameTime() % 20L == 0L) {
                SyncNenStateS2C.Snapshot s = new SyncNenStateS2C.Snapshot();
                s.isGyo = d.isGyoActive; s.isIn = d.isInActive; s.active = d.activeAbility;
                s.gumA = d.gumAttachedA; s.gumB = d.gumAttachedB;
                s.gumAToBlock = d.gumAToBlock; s.gumBToBlock = d.gumBToBlock;
                s.gumAX = d.gumAX; s.gumAY = d.gumAY; s.gumAZ = d.gumAZ; s.gumAEntityId = d.gumAEntityId;
                s.gumBX = d.gumBX; s.gumBY = d.gumBY; s.gumBZ = d.gumBZ; s.gumBEntityId = d.gumBEntityId;
                com.example.nenmod.net.NenNetwork.CHANNEL.send(PacketDistributor.TRACKING_ENTITY.with(() -> p), new SyncNenStateS2C(p.getId(), s));
                com.example.nenmod.net.NenNetwork.CHANNEL.send(PacketDistributor.PLAYER.with(() -> (net.minecraft.server.level.ServerPlayer)p), new SyncNenStateS2C(p.getId(), s));
            }

            // PHYSICS_MULTI
            // Bungee Gum physics (server side only)

            // Handle both slots
            if (!p.level().isClientSide) {
                java.util.function.BiConsumer<Boolean, Integer> apply = (isA, tickMod) -> {
                    boolean attached = isA ? d.gumAttachedA : d.gumAttachedB;
                    if (!attached) return;
                    boolean toBlock = isA ? d.gumAToBlock : d.gumBToBlock;
                    net.minecraft.world.phys.Vec3 anchor;
                    if (toBlock) {
                        int ax = isA?d.gumAX:d.gumBX, ay=isA?d.gumAY:d.gumBY, az=isA?d.gumAZ:d.gumBZ;
                        anchor = new net.minecraft.world.phys.Vec3(ax+0.5, ay+0.5, az+0.5);
                    } else {
                        int eid = isA?d.gumAEntityId:d.gumBEntityId;
                        var ent = p.level().getEntity(eid);
                        if (ent == null) { if (isA) d.gumAttachedA=false; else d.gumAttachedB=false; return; }
                        anchor = ent.position();
                    }
                    net.minecraft.world.phys.Vec3 delta = anchor.subtract(p.position());
                    double dist = delta.length();
                    if (dist > MAX_RANGE) {
                        if (isA) d.gumAttachedA=false; else d.gumAttachedB=false;
                        return;
                    }
                    if (dist > REST_LEN) {
                        double k = 0.35;
                        double affinityMul = 0.75;
                        try {
                            java.lang.reflect.Field f = d.getClass().getDeclaredField("nenAffinity");
                            f.setAccessible(true);
                            Object val = f.get(d);
                            if (val != null && val.toString().equalsIgnoreCase("Transmuter")) affinityMul = 1.0;
                        } catch (Exception ignored) {}
                        double extra = (dist - REST_LEN) * k * affinityMul;
                        net.minecraft.world.phys.Vec3 pull = delta.normalize().scale(extra);
                        p.setDeltaMovement(p.getDeltaMovement().add(pull));
                        if (p.level().getGameTime() % 20L == 0L) {
                            int cost = (int)Math.max(1, Math.round(2 * (1.0/affinityMul)));
                            d.nen = Math.max(0, d.nen - cost);
                            d.masteryBungee = Math.min(100f, d.masteryBungee + 0.003f);
                        }
                    }
                };
                apply.accept(true, 0);
                apply.accept(false, 0);
            }
    
                Vec3 anchor;
                if (d.gumToBlock) {
                    anchor = new Vec3(d.gumX + 0.5, d.gumY + 0.5, d.gumZ + 0.5);
                } else {
                    var ent = p.level().getEntity(d.gumEntityId);
                    if (ent == null) { d.gumAttached=false; return; }
                    anchor = ent.position();
                }
                Vec3 delta = anchor.subtract(p.position());
                double dist = delta.length();
                if (dist > MAX_RANGE) {
                    // Snap
                    d.gumAttached = false;
                } else if (dist > REST_LEN) {
                    double k = 0.35; // stiffness baseline
                    // Affinity scaling: weaker for non-transmuters
                    double affinityMul = "Transmuter".equalsIgnoreCase(d.nenAffinity) ? 1.0 : 0.75;
                    double extra = (dist - REST_LEN) * k * affinityMul;
                    Vec3 pull = delta.normalize().scale(extra);
                    p.setDeltaMovement(p.getDeltaMovement().add(pull));
                    // Drain nen per second while under tension
                    if (p.level().getGameTime() % 20L == 0L) {
                        int cost = (int)Math.max(1, Math.round(2 * (1.0/affinityMul)));
                        d.nen = Math.max(0, d.nen - cost);
                        d.masteryBungee = Math.min(100f, d.masteryBungee + 0.003f);
                    }
                }
            }
        });
    }
    
public class CommonEvents {
    private static final double REST_LEN = 2.0; // blocks
    private static final double MAX_RANGE = 15.0;

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent e) {
        NenCommands.register(e.getDispatcher());
    }
}
