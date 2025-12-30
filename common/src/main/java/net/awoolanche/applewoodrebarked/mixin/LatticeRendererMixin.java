package net.awoolanche.applewoodrebarked.mixin;

import net.satisfy.vinery.client.render.block.LatticeRenderer;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = LatticeRenderer.class, remap = false)
public class LatticeRendererMixin {

    @Inject(method = "getTextureMap", at = @At("TAIL"), cancellable = true)
    private static void applewoodrebarked$injectAppleLatticeTexture(CallbackInfoReturnable<Map<Block, ResourceLocation>> cir) {
        Map<Block, ResourceLocation> map = cir.getReturnValue();

        ResourceLocation appleTexture = ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "textures/block/apple_lattice.png");
        map.putIfAbsent(ModBlocks.APPLE_LATTICE.get(), appleTexture);
    }
}
