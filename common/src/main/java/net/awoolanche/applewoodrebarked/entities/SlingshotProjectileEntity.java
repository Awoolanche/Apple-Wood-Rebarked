package net.awoolanche.applewoodrebarked.entities;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class SlingshotProjectileEntity extends ThrowableItemProjectile {
    private boolean hasBounced = false;

    public SlingshotProjectileEntity(EntityType<? extends SlingshotProjectileEntity> type, Level level) {
        super(type, level);
        if (this.getItem().isEmpty()) this.setItem(new ItemStack(Items.STONE));
    }

    @Override
    protected void defineSynchedData(net.minecraft.network.syncher.SynchedEntityData.Builder builder) {
        super.defineSynchedData(builder);
    }

    public SlingshotProjectileEntity(Level level, LivingEntity owner, ItemStack stack) {
        super(ModEntities.SLINGSHOT_PROJECTILE.get(), owner, level);
        this.setItem(stack.copy());
    }

    @Override
    protected Item getDefaultItem() {
        return Items.EGG;
    }

    @Override
    protected void onHitBlock(BlockHitResult hitResult) {
        Level level = this.level();
        ItemStack ammo = this.getItem();
        BlockPos pos = hitResult.getBlockPos();

        Block appleLeaves = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("vinery", "apple_leaves"));
        Block cherryLeaves = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("vinery", "dark_cherry_leaves"));
        Block block = level.getBlockState(pos).getBlock();

        if (block == appleLeaves || block == cherryLeaves) {
            if (this.getOwner() instanceof Player player) {
                level.getBlockState(pos).useWithoutItem(level, player, hitResult);
            }
        }

        if (ammo.is(Items.SLIME_BALL) && !hasBounced) {
            bounceProjectile(hitResult);
            spawnSlimeParticles();
            return;
        }

        super.onHitBlock(hitResult);

        if (!level.isClientSide) {
            applySlingshotEffect(hitResult);
            level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    @Override
    protected void onHitEntity(EntityHitResult entityHitResult) {
        Level level = this.level();
        Entity target = entityHitResult.getEntity();
        ItemStack ammo = this.getItem();
        float damage = 0f;
        boolean setOnFire = false;

        if (ammo.is(Items.CLAY_BALL)) damage = 2f;
        else if (ammo.is(Items.FIRE_CHARGE)) { damage = 5f; setOnFire = true; }
        else if (ammo.is(Items.SLIME_BALL)) damage = 1f;

        if (damage > 0f) {
            Entity owner = this.getOwner();
            if (owner instanceof Player player) {
                target.hurt(level.damageSources().playerAttack(player), damage);
            } else if (owner instanceof LivingEntity living) {
                target.hurt(level.damageSources().thrown(this, living), damage);
            }
        }

        if (setOnFire) target.igniteForSeconds(5);

        if (ammo.is(Items.SLIME_BALL) && !hasBounced) {
            this.setDeltaMovement(this.getDeltaMovement().scale(-0.8));
            hasBounced = true;
            level.playSound(null, getX(), getY(), getZ(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.NEUTRAL, 0.5f, 1.0f);
            spawnSlimeParticles();
            return;
        }

        super.onHitEntity(entityHitResult);

        if (!level.isClientSide) {
            applySlingshotEffect(entityHitResult);
            level.broadcastEntityEvent(this, (byte) 3);
            this.discard();
        }
    }

    private void bounceProjectile(HitResult hitResult) {
        Vec3 motion = this.getDeltaMovement();
        if (hitResult instanceof BlockHitResult blockHit) {
            switch (blockHit.getDirection()) {
                case UP, DOWN -> this.setDeltaMovement(motion.x, -motion.y * 0.8, motion.z);
                case NORTH, SOUTH -> this.setDeltaMovement(motion.x, motion.y, -motion.z * 0.8);
                case EAST, WEST -> this.setDeltaMovement(-motion.x * 0.8, motion.y, motion.z);
            }
        }
        hasBounced = true;
        this.level().playSound(null, getX(), getY(), getZ(), SoundEvents.SLIME_BLOCK_HIT, SoundSource.NEUTRAL, 0.5F, 1.0F);
    }

    private void spawnSlimeParticles() {
        if (this.level().isClientSide) {
            RandomSource rng = this.level().random;
            for (int i = 0; i < 10; i++) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(), this.getY(), this.getZ(),
                        (rng.nextDouble() - 0.5) * 0.3, (rng.nextDouble() - 0.5) * 0.3, (rng.nextDouble() - 0.5) * 0.3);
            }
        }
    }

    @Override
    public void handleEntityEvent(byte status) {
        if (status == 3) {
            RandomSource rng = this.level().random;
            Vec3 motion = this.getDeltaMovement();
            for (int i = 0; i < 12; ++i) {
                this.level().addParticle(new ItemParticleOption(ParticleTypes.ITEM, this.getItem()),
                        this.getX(), this.getY(), this.getZ(),
                        motion.x * 0.4 + (rng.nextDouble() - 0.5) * 0.3,
                        motion.y * 0.4 + (rng.nextDouble() - 0.5) * 0.3,
                        motion.z * 0.4 + (rng.nextDouble() - 0.5) * 0.3);
            }
        } else super.handleEntityEvent(status);
    }

    private void applySlingshotEffect(HitResult hitResult) {
        Level level = this.level();
        ItemStack ammo = this.getItem();
        double x = this.getX(), y = this.getY(), z = this.getZ();

        if (ammo.is(Items.EGG)) {
            level.playSound(null, x, y, z, SoundEvents.EGG_THROW, SoundSource.NEUTRAL, 0.8F, 0.8F + level.random.nextFloat() * 0.4F);
            if (!level.isClientSide && level.random.nextInt(8) == 0) {
                Chicken chicken = EntityType.CHICKEN.create(level);
                if (chicken != null) {
                    chicken.setAge(-24000);
                    chicken.moveTo(x, y, z, this.getYRot(), 0.0F);
                    level.addFreshEntity(chicken);
                }
            }
        } else if (ammo.is(Items.FIRE_CHARGE)) {
            level.explode(this, x, y, z, 0.1f, Level.ExplosionInteraction.NONE);
            if (hitResult instanceof BlockHitResult blockHit) {
                BlockPos firePos = blockHit.getBlockPos().relative(blockHit.getDirection());
                if (level.isEmptyBlock(firePos)) level.setBlockAndUpdate(firePos, net.minecraft.world.level.block.Blocks.FIRE.defaultBlockState());
            }
            level.playSound(null, x, y, z, SoundEvents.FLINTANDSTEEL_USE, SoundSource.BLOCKS, 1.0F, level.random.nextFloat() * 0.4F + 0.8F);
        } else if (ammo.is(Items.CLAY_BALL)) {
            level.playSound(null, x, y, z, SoundEvents.SLIME_BLOCK_FALL, SoundSource.NEUTRAL, 0.5F, 0.5F + level.random.nextFloat() * 0.4F);
        } else if (ammo.is(Items.SNOWBALL)) {
            level.playSound(null, x, y, z, SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 1.0F);
        }
    }
}