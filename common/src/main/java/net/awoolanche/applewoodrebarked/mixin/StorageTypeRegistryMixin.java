package net.awoolanche.applewoodrebarked.mixin;

import net.satisfy.vinery.core.registry.StorageTypeRegistry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mixin(value = StorageTypeRegistry.class, remap = false)
public class StorageTypeRegistryMixin {

    @Inject(
            method = "registerBlocks",
            at = @At("TAIL")
    )
    private static void applewoodrebarked$addAppleStorageBlocks(Set<Block> blocks, CallbackInfoReturnable<Set<Block>> cir) {
        applewoodrebarked$addIfPresent(blocks, "apple_shelf");
        applewoodrebarked$addIfPresent(blocks, "apple_wine_rack_big");
        applewoodrebarked$addIfPresent(blocks, "apple_wine_rack_mid");
        applewoodrebarked$addIfPresent(blocks, "apple_wine_rack_small");
    }

    @Inject(
            method = "getCabinetBlocks",
            at = @At("RETURN"),
            cancellable = true
    )
    private static void applewoodrebarked$addAppleCabinets(CallbackInfoReturnable<Block[]> cir) {
        Block[] original = cir.getReturnValue();

        Block appleCabinet = applewoodrebarked$get("apple_cabinet");
        Block appleDrawer = applewoodrebarked$get("apple_drawer");

        List<Block> toAdd = new ArrayList<>();
        if (appleCabinet != Blocks.AIR) toAdd.add(appleCabinet);
        if (appleDrawer != Blocks.AIR) toAdd.add(appleDrawer);

        if (!toAdd.isEmpty()) {
            Block[] extended = new Block[original.length + toAdd.size()];
            System.arraycopy(original, 0, extended, 0, original.length);
            for (int i = 0; i < toAdd.size(); i++) {
                extended[original.length + i] = toAdd.get(i);
            }
            cir.setReturnValue(extended);
        }
    }

    @Unique
    private static void applewoodrebarked$addIfPresent(Set<Block> set, String path) {
        Block block = applewoodrebarked$get(path);
        if (block != Blocks.AIR) {
            set.add(block);
        }
    }

    @Unique
    private static Block applewoodrebarked$get(String path) {
        // Safe lookup using the registry to avoid circular dependency issues during init
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("applewoodrebarked", path));
    }
}