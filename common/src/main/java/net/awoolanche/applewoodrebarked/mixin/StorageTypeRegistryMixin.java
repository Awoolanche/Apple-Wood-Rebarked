package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.core.registry.StorageTypeRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Set;

@Mixin(StorageTypeRegistry.class)
public class StorageTypeRegistryMixin {

    @Inject(
            method = "registerBlocks(Ljava/util/Set;)Ljava/util/Set;",
            at = @At("TAIL"),
            remap = false
    )
    private static void applewoodrebarked$addAppleShelf(
            Set<Block> blocks,
            CallbackInfoReturnable<Set<Block>> cir
    ) {
        blocks.add(ModBlocks.APPLE_SHELF.get());
    }
}
