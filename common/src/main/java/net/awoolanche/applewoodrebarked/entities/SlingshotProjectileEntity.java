package net.awoolanche.applewoodrebarked.entities;

import net.awoolanche.applewoodrebarked.items.SlingshotItem;
import net.awoolanche.applewoodrebarked.util.ModAmmoTooltip;

import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.AreaEffectCloud;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.animal.Chicken;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.FireworkRocketEntity;
import net.minecraft.world.entity.projectile.ThrowableItemProjectile;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.FireworkExplosion;
import net.minecraft.world.item.component.Fireworks;
import net.minecraft.world.item.context.DirectionalPlaceContext;
import net.minecraft.world.InteractionResult;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import java.util.List;

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
        float damage = ModAmmoTooltip.getDamage(ammo);
        boolean setOnFire = false;

        if (ammo.is(Items.CLAY_BALL)) damage = 2f;
        else if (ammo.is(Items.FLINT)) damage = 4f;
        else if (ammo.is(Items.FIRE_CHARGE)) { damage = 5f; setOnFire = true; }
        else if (ammo.is(Items.SLIME_BALL)) damage = 1f;
        else if (ammo.getItem() instanceof BlockItem) damage = 3f;

        if (damage > 0f) {
            Entity owner = this.getOwner();
            if (owner instanceof Player player) {
                target.hurt(level.damageSources().playerAttack(player), damage);
            } else if (owner instanceof LivingEntity living) {
                target.hurt(level.damageSources().thrown(this, living), damage);
            }
        }

        if (setOnFire) target.igniteForSeconds(5);

        if (ammo.is(Items.CHORUS_FRUIT) && target instanceof LivingEntity livingTarget) {
            if (!level.isClientSide) {
                double x = livingTarget.getX();
                double y = livingTarget.getY();
                double z = livingTarget.getZ();

                for (int i = 0; i < 16; ++i) {
                    double targetX = x + (livingTarget.getRandom().nextDouble() - 0.5) * 16.0;
                    double targetY = Mth.clamp(y + (double)(livingTarget.getRandom().nextInt(16) - 8), (double)level.getMinBuildHeight(), (double)(level.getMinBuildHeight() + ((net.minecraft.server.level.ServerLevel)level).getLogicalHeight() - 1));
                    double targetZ = z + (livingTarget.getRandom().nextDouble() - 0.5) * 16.0;

                    if (livingTarget.isPassenger()) {
                        livingTarget.stopRiding();
                    }

                    if (livingTarget.randomTeleport(targetX, targetY, targetZ, true)) {
                        level.playSound(null, x, y, z, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.PLAYERS, 1.0F, 1.0F);
                        livingTarget.playSound(SoundEvents.CHORUS_FRUIT_TELEPORT, 1.0F, 1.0F);
                        break;
                    }
                }
            }
        }

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
        } else if (ammo.is(Items.FLINT)) {
            level.playSound(null, x, y, z, SoundEvents.PLAYER_ATTACK_CRIT, SoundSource.NEUTRAL, 0.5F, 1.5F);
        } else if (ammo.is(Items.SNOWBALL)) {
            level.playSound(null, x, y, z, SoundEvents.SNOWBALL_THROW, SoundSource.NEUTRAL, 0.5F, 1.0F);
        } else if (ammo.is(Items.CHORUS_FRUIT)) {
            level.playSound(null, x, y, z, SoundEvents.CHORUS_FRUIT_TELEPORT, SoundSource.NEUTRAL, 0.5F, 1.2F);
        } else if (ammo.is(Items.FIREWORK_STAR)) {
            spawnFireworkExplosion(ammo, x, y, z);
            applyFlashDebuff(x, y, z);
        } else if (ammo.is(Items.SPLASH_POTION)) {
            applySplashPotion(ammo, x, y, z);
        } else if (ammo.is(Items.LINGERING_POTION)) {
            spawnLingeringCloud(ammo, x, y, z);
        } else if (ammo.getItem() instanceof BlockItem blockItem) {
            placeBlockAmmo(blockItem, hitResult);
        }
    }

    private void placeBlockAmmo(BlockItem blockItem, HitResult hitResult) {
        Level level = this.level();
        if (level.isClientSide) return;

        if (SlingshotItem.isMultiblock(blockItem)) {
            dropAmmoItem(blockItem);
            return;
        }

        if (!(hitResult instanceof BlockHitResult blockHit)) {
            dropAmmoItem(blockItem);
            return;
        }

        Direction hitDirection = blockHit.getDirection();
        BlockPos placePos = blockHit.getBlockPos().relative(hitDirection);

        if (!level.getBlockState(placePos).canBeReplaced()) {
            dropAmmoItem(blockItem);
            return;
        }

        ItemStack placeStack = new ItemStack(blockItem, 1);
        DirectionalPlaceContext context = new DirectionalPlaceContext(level, placePos, hitDirection, placeStack, hitDirection);

        InteractionResult result;
        try {
            result = blockItem.place(context);
        } catch (Exception e) {
            dropAmmoItem(blockItem);
            return;
        }

        if (result.consumesAction()) {
            BlockState placedState = level.getBlockState(placePos);
            level.updateNeighborsAt(placePos, placedState.getBlock());
            level.playSound(null, placePos, placedState.getSoundType().getPlaceSound(), SoundSource.BLOCKS,
                    1.0F, 0.9F + level.random.nextFloat() * 0.2F);
        } else {
            dropAmmoItem(blockItem);
        }
    }

    private void dropAmmoItem(BlockItem blockItem) {
        Level level = this.level();
        if (level.isClientSide) return;

        ItemEntity itemEntity = new ItemEntity(level, this.getX(), this.getY(), this.getZ(), new ItemStack(blockItem, 1));
        itemEntity.setDeltaMovement(this.getDeltaMovement().scale(0.3));
        level.addFreshEntity(itemEntity);

        level.playSound(null, this.getX(), this.getY(), this.getZ(), SoundEvents.STONE_HIT, SoundSource.NEUTRAL, 0.6F, 0.8F);
    }

    private void applySplashPotion(ItemStack potionStack, double x, double y, double z) {
        Level level = this.level();
        if (level.isClientSide) return;

        PotionContents potionContents = potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        boolean isInstant = potionContents.potion().map(p -> p.value().hasInstantEffects()).orElse(false);

        level.levelEvent(null, isInstant ? 2007 : 2002, BlockPos.containing(x, y, z), potionContents.getColor());

        AABB area = new AABB(x - 4, y - 4, z - 4, x + 4, y + 4, z + 4);
        Entity owner = this.getOwner();

        List<LivingEntity> targets = level.getEntitiesOfClass(LivingEntity.class, area);

        for (LivingEntity target : targets) {
            double distanceSqr = target.distanceToSqr(x, y, z);
            if (distanceSqr > 16.0) continue;

            double factor = 1.0 - Math.sqrt(distanceSqr) / 4.0;

            potionContents.forEachEffect(effectInstance -> {
                MobEffect effect = effectInstance.getEffect().value();

                if (effect.isInstantenous()) {
                    effect.applyInstantenousEffect(this, owner, target, effectInstance.getAmplifier(), factor);
                } else {
                    int duration = (int) (factor * effectInstance.getDuration() + 0.5);
                    if (duration > 20) {
                        target.addEffect(new MobEffectInstance(effectInstance.getEffect(), duration, effectInstance.getAmplifier(),
                                effectInstance.isAmbient(), effectInstance.isVisible()));
                    }
                }
            });
        }
    }

    private void spawnLingeringCloud(ItemStack potionStack, double x, double y, double z) {
        Level level = this.level();
        if (level.isClientSide) return;

        PotionContents potionContents = potionStack.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);
        level.levelEvent(null, 2007, BlockPos.containing(x, y, z), potionContents.getColor());

        AreaEffectCloud cloud = new AreaEffectCloud(level, x, y, z);
        if (this.getOwner() instanceof LivingEntity livingOwner) {
            cloud.setOwner(livingOwner);
        }
        cloud.setRadius(3.0F);
        cloud.setRadiusOnUse(-0.5F);
        cloud.setWaitTime(10);
        cloud.setRadiusPerTick(-cloud.getRadius() / (float) cloud.getDuration());
        cloud.setPotionContents(potionContents);

        level.addFreshEntity(cloud);
    }

    private void spawnFireworkExplosion(ItemStack star, double x, double y, double z) {
        Level level = this.level();
        if (level.isClientSide) return;

        FireworkExplosion explosion = star.get(DataComponents.FIREWORK_EXPLOSION);

        ItemStack rocketStack = new ItemStack(Items.FIREWORK_ROCKET);
        rocketStack.set(DataComponents.FIREWORKS, new Fireworks(0, explosion != null ? List.of(explosion) : List.of()));

        FireworkRocketEntity firework = new FireworkRocketEntity(level, x, y, z, rocketStack);
        firework.setDeltaMovement(Vec3.ZERO);
        level.addFreshEntity(firework);
        level.broadcastEntityEvent(firework, (byte) 17);
        firework.discard();

        level.playSound(null, x, y, z, SoundEvents.FIREWORK_ROCKET_BLAST, SoundSource.NEUTRAL, 0.8F, 1.0F);
    }

    private void applyFlashDebuff(double x, double y, double z) {
        Level level = this.level();
        AABB area = new AABB(x - 2, y - 2, z - 2, x + 2, y + 2, z + 2);

        for (LivingEntity target : level.getEntitiesOfClass(LivingEntity.class, area)) {
            target.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 60));
            target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 60));
        }
    }
}