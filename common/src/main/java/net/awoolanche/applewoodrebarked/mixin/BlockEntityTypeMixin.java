package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;


import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

import java.util.HashSet;
import java.util.Set;

@Mixin(BlockEntityType.Builder.class)
public abstract class BlockEntityTypeMixin {

    @ModifyArg(
            method = "of",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/block/entity/BlockEntityType$Builder;<init>(Lnet/minecraft/world/level/block/entity/BlockEntityType$BlockEntitySupplier;Ljava/util/Set;)V"
            ),
            index = 1
    )
    private static Set<Block> applewoodrebarked$extendVineryLattice(Set<Block> original) {
        boolean isVineryLattice = original.stream().anyMatch(block ->
                BuiltInRegistries.BLOCK.getKey(block)
                        .equals(ResourceLocation.fromNamespaceAndPath("vinery", "oak_lattice"))
        );

        if (!isVineryLattice) return original;

        Set<Block> extended = new HashSet<>(original);
        extended.add(ModBlocks.APPLE_LATTICE.get());
        return extended;
    }
}

