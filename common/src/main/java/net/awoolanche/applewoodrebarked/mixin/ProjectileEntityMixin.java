package net.awoolanche.applewoodrebarked.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public abstract class ProjectileEntityMixin {

    @Inject(method = "onHitBlock", at = @At("TAIL"))
    private void applewood$onLeafHit(BlockHitResult hitResult, CallbackInfo ci) {
        Projectile projectile = (Projectile) (Object) this;
        Level level = projectile.level();

        if (level.isClientSide) return;

        BlockPos pos = hitResult.getBlockPos();
        BlockState state = level.getBlockState(pos);

        Block appleLeaves = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("vinery", "apple_leaves"));
        Block cherryLeaves = BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("vinery", "dark_cherry_leaves"));

        if (state.is(appleLeaves) || state.is(cherryLeaves)) {
            Block.dropResources(state, level, pos, null, projectile, ItemStack.EMPTY);
        }
    }
}
