package net.awoolanche.applewoodrebarked.compat;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.util.ModTabs;
import com.berksire.furniture.core.registry.EntityTypeRegistry;
import com.berksire.furniture.core.registry.TabRegistry;

import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.level.block.Block;

public class FurnitureCompat {

    public static void fixBlockEntityValidBlocks() {
        if (!net.awoolanche.applewoodrebarked.util.ModCompat.FURNITURE) {
            return;
        }

        addValidBlock(EntityTypeRegistry.DRESSER_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_DRESSER.get());
        addValidBlock(EntityTypeRegistry.CLOCK_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_CLOCK.get());
        addValidBlock(EntityTypeRegistry.GRANDFATHER_CLOCK_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_GRANDFATHER_CLOCK.get());
        addValidBlock(EntityTypeRegistry.WARDROBE_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_WARDROBE.get());
        addValidBlock(EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_BASE_CABINET.get());
    }

    public static void registerCreativeTabs() {
        if (!net.awoolanche.applewoodrebarked.util.ModCompat.FURNITURE) {
            return;
        }

        // Furniture CL Tab
        if (TabRegistry.FURNITURE_COMPAT_LAYER_TAB != null) {
            CreativeTabRegistry.append(
                    TabRegistry.FURNITURE_COMPAT_LAYER_TAB,
                    FurnitureBlocks.APPLE_SHUTTER,
                    FurnitureBlocks.APPLE_BENCH,
                    FurnitureBlocks.APPLE_DESK_CHAIR,
                    FurnitureBlocks.APPLE_BASE_CABINET,
                    FurnitureBlocks.APPLE_DRESSER,
                    FurnitureBlocks.APPLE_WARDROBE,
                    FurnitureBlocks.APPLE_DESK,
                    FurnitureBlocks.APPLE_CLOCK,
                    FurnitureBlocks.APPLE_GRANDFATHER_CLOCK,
                    FurnitureBlocks.APPLE_MIRROR
            );
        } else {
            // If other mods aren't loaded, put them in main tab
            CreativeTabRegistry.append(
                    ModTabs.APPLE_WOOD_REBARKED_TAB,
                    FurnitureBlocks.APPLE_SHUTTER,
                    FurnitureBlocks.APPLE_BENCH,
                    FurnitureBlocks.APPLE_DESK_CHAIR,
                    FurnitureBlocks.APPLE_BASE_CABINET,
                    FurnitureBlocks.APPLE_DRESSER,
                    FurnitureBlocks.APPLE_WARDROBE,
                    FurnitureBlocks.APPLE_DESK,
                    FurnitureBlocks.APPLE_CLOCK,
                    FurnitureBlocks.APPLE_GRANDFATHER_CLOCK,
                    FurnitureBlocks.APPLE_MIRROR
            );
        }
    }

    private static void addValidBlock(net.minecraft.world.level.block.entity.BlockEntityType<?> type, Block block) {
        net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin accessor =
                (net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin) type;

        java.util.Set<Block> validBlocks = new java.util.HashSet<>(accessor.getValidBlocks());
        validBlocks.add(block);
        accessor.setValidBlocks(validBlocks);
    }
}