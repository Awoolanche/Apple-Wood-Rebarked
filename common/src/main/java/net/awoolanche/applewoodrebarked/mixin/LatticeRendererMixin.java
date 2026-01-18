package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.client.render.block.LatticeRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Map;

@Mixin(value = LatticeRenderer.class, remap = false)
public class LatticeRendererMixin {

    @Inject(method = "getTextureMap", at = @At("RETURN"))
    private static void applewoodrebarked$injectTexture(CallbackInfoReturnable<Map<Block, ResourceLocation>> cir) {
        Map<Block, ResourceLocation> map = cir.getReturnValue();
        if (map != null) {
            map.put(ModBlocks.APPLE_LATTICE.get(),
                    ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "textures/block/apple_lattice.png"));
        }
    }
}