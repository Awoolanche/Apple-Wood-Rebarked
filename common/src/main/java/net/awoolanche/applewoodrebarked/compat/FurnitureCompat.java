package net.awoolanche.applewoodrebarked.compat;

import com.berksire.furniture.core.registry.EntityTypeRegistry;
import com.berksire.furniture.core.registry.ObjectRegistry;
import com.berksire.furniture.core.registry.TabRegistry;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin;
import net.awoolanche.applewoodrebarked.platform.PlatformHelper;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FurnitureCompat {

    private static final ResourceKey<CreativeModeTab> COMPAT_TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath("furniture", "furniture_compat_layer"));

    public static void fixBlockEntityValidBlocks() {
        if (!ModCompat.FURNITURE) {
            return;
        }

        addValidBlock(EntityTypeRegistry.DRESSER_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_DRESSER.get());
        addValidBlock(EntityTypeRegistry.CLOCK_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_CLOCK.get());
        addValidBlock(EntityTypeRegistry.GRANDFATHER_CLOCK_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_GRANDFATHER_CLOCK.get());
        addValidBlock(EntityTypeRegistry.WARDROBE_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_WARDROBE.get());
        addValidBlock(EntityTypeRegistry.CABINET_BLOCK_ENTITY.get(), FurnitureBlocks.APPLE_BASE_CABINET.get());
    }

    public static void registerCreativeTabs() {
        if (!ModCompat.FURNITURE) {
            return;
        }

        if (TabRegistry.FURNITURE_COMPAT_LAYER_TAB != null) {
            insertAfterDarkCherry(ObjectRegistry.SHUTTERS, FurnitureBlocks.APPLE_SHUTTER);
            insertAfterDarkCherry(ObjectRegistry.BENCHES, FurnitureBlocks.APPLE_BENCH);
            insertAfterDarkCherry(ObjectRegistry.DESK_CHAIRS, FurnitureBlocks.APPLE_DESK_CHAIR);
            insertAfterDarkCherry(ObjectRegistry.CABINETS, FurnitureBlocks.APPLE_BASE_CABINET);
            insertAfterDarkCherry(ObjectRegistry.DRESSER, FurnitureBlocks.APPLE_DRESSER);
            insertAfterDarkCherry(ObjectRegistry.WARDROBES, FurnitureBlocks.APPLE_WARDROBE);
            insertAfterDarkCherry(ObjectRegistry.DESKS, FurnitureBlocks.APPLE_DESK);
            insertAfterDarkCherry(ObjectRegistry.CLOCKS, FurnitureBlocks.APPLE_CLOCK);
            insertAfterDarkCherry(ObjectRegistry.GRANDFATHER_CLOCKS, FurnitureBlocks.APPLE_GRANDFATHER_CLOCK);
            insertAfterDarkCherry(ObjectRegistry.MIRRORS, FurnitureBlocks.APPLE_MIRROR);
        }
    }

    private static void insertAfterDarkCherry(
            Map<String, ? extends RegistrySupplier<Block>> compatMap,
            RegistrySupplier<Block> ourBlock) {
        var darkCherry = compatMap.get("dark_cherry");
        if (darkCherry != null) {
            PlatformHelper.insertAfter(COMPAT_TAB_KEY, darkCherry, ourBlock);
        }
    }

    private static void addValidBlock(BlockEntityType<?> type, Block block) {
        BlockEntityTypeMixin accessor = (BlockEntityTypeMixin) type;

        Set<Block> validBlocks = new HashSet<>(accessor.getValidBlocks());
        validBlocks.add(block);
        accessor.setValidBlocks(validBlocks);
    }
}