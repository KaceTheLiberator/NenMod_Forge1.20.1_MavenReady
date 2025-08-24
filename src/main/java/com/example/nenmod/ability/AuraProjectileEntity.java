
package com.yourmod.nen.entities;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import net.minecraft.nbt.CompoundTag;

public class AuraProjectileEntity extends Projectile {
    private float damage = 10.0f;
    private float speedDecay = 0.98f;
    private int life = 200;

    public AuraProjectileEntity(EntityType<? extends AuraProjectileEntity> type, Level level) {
        super(type, level);
    }

    public AuraProjectileEntity(Level level, LivingEntity owner, float dmg, float velocity) {
        this(ModEntities.AURA_PROJECTILE.get(), level);
        this.setOwner(owner);
        this.damage = dmg;
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        Vec3 look = owner.getLookAngle();
        this.setDeltaMovement(look.scale(velocity));
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        // Move
        Vec3 motion = this.getDeltaMovement().scale(speedDecay);
        this.setDeltaMovement(motion);
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);

        // Lifetime
        if (--life <= 0) {
            this.discard();
            return;
        }

        // Collision check
        HitResult hit = ProjectileUtil.getHitResultOnMoveVector(this, this::canHitEntity);
        if (hit.getType() != HitResult.Type.MISS) {
            this.onHit(hit);
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        if (this.level().isClientSide) return;
        if (getOwner() instanceof Player player && result.getEntity() != null) {
            result.getEntity().hurt(this.damageSources().playerAttack(player), damage);
            this.level().explode(this, getX(), getY(), getZ(), damage / 8.0f, Level.ExplosionInteraction.NONE);
        }
        this.discard();
    }
}
