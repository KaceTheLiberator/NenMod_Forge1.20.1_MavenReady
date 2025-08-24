
package com.yourmod.nen.entities;

import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.nbt.CompoundTag;

public class AuraBladeEntity extends Projectile {
    private float damage = 7.0f;
    private int life = 12; // short-lived slash

    public AuraBladeEntity(EntityType<? extends AuraBladeEntity> type, Level level) {
        super(type, level);
    }

    public AuraBladeEntity(Level level, LivingEntity owner, float dmg) {
        this(ModEntities.AURA_BLADE.get(), level);
        this.setOwner(owner);
        this.damage = dmg;
        this.setPos(owner.getX(), owner.getEyeY() - 0.1, owner.getZ());
        Vec3 look = owner.getLookAngle().normalize().scale(1.4);
        this.setDeltaMovement(look);
    }

    @Override
    public void tick() {
        super.tick();
        if (this.level().isClientSide) return;

        // Move forward like a short-range slash
        Vec3 motion = this.getDeltaMovement();
        this.setPos(this.getX() + motion.x, this.getY() + motion.y, this.getZ() + motion.z);

        // Hit entities nearby (front-biased)
        for (LivingEntity e : this.level().getEntitiesOfClass(LivingEntity.class, this.getBoundingBox().inflate(0.6))) {
            if (e == this.getOwner()) continue;
            if (!e.isAlive()) continue;
            e.hurt(this.damageSources().indirectMagic(this, getOwner()), damage);
        }

        if (--life <= 0) {
            this.discard();
        }
    }

    @Override
    protected void onHit(HitResult hitResult) {
        super.onHit(hitResult);
        if (!this.level().isClientSide) {
            this.discard();
        }
    }

    public void setDamage(float dmg) { this.damage = dmg; }

    @Override
    protected void defineSynchedData() {}

    @Override
    protected void readAdditionalSaveData(CompoundTag tag) {
        this.damage = tag.getFloat("Damage");
        this.life = tag.getInt("Life");
    }

    @Override
    protected void addAdditionalSaveData(CompoundTag tag) {
        tag.putFloat("Damage", this.damage);
        tag.putInt("Life", this.life);
    }
}
