package net.awoolanche.applewoodrebarked.compat;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin;
import net.awoolanche.applewoodrebarked.platform.PlatformHelper;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.satisfy.hearth_and_timber.core.registry.EntityTypeRegistry;
import net.satisfy.hearth_and_timber.core.registry.ObjectRegistry;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class HearthAndTimberCompat {

    public static final ResourceKey<CreativeModeTab> TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath("hearth_and_timber", "hearth_and_timber"));

    public static void registerCreativeTabs() {
        if (!ModCompat.HEARTH_AND_TIMBER) {
            return;
        }

        insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLES, HearthAndTimberBlocks.APPLE_SHINGLES);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLE_STAIRS, HearthAndTimberBlocks.APPLE_SHINGLE_STAIRS);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLE_SLAB, HearthAndTimberBlocks.APPLE_SHINGLE_SLAB);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_BEAMS, HearthAndTimberBlocks.APPLE_SUPPORT_BEAM);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_SUPPORTS, HearthAndTimberBlocks.APPLE_SUPPORT);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_PILLARS, HearthAndTimberBlocks.APPLE_PILLAR);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_RAILINGS, HearthAndTimberBlocks.APPLE_RAILING);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_WINDOW_CASINGS, HearthAndTimberBlocks.APPLE_WINDOW_CASING);
        insertAfterDarkCherry(ObjectRegistry.COMPAT_BOARDS, HearthAndTimberBlocks.APPLE_BOARD);

        insert(HearthAndTimberBlocks.APPLE_WINDOW_CASING, HearthAndTimberBlocks.APPLE_WINDOW_PANE);
        insert(HearthAndTimberBlocks.APPLE_WINDOW_PANE, HearthAndTimberBlocks.APPLE_WINDOW);
    }

    public static void acceptCompatItems(CreativeModeTab.Output output) {
        if (!ModCompat.HEARTH_AND_TIMBER) {
            return;
        }

        List<Supplier<? extends ItemLike>> items = List.of(
                HearthAndTimberBlocks.APPLE_SHINGLES,
                HearthAndTimberBlocks.APPLE_SHINGLE_STAIRS,
                HearthAndTimberBlocks.APPLE_SHINGLE_SLAB,
                HearthAndTimberBlocks.APPLE_SUPPORT_BEAM,
                HearthAndTimberBlocks.APPLE_SUPPORT,
                HearthAndTimberBlocks.APPLE_PILLAR,
                HearthAndTimberBlocks.APPLE_RAILING,
                HearthAndTimberBlocks.APPLE_WINDOW_CASING,
                HearthAndTimberBlocks.APPLE_WINDOW_PANE,
                HearthAndTimberBlocks.APPLE_WINDOW,
                HearthAndTimberBlocks.APPLE_BOARD);
        for (Supplier<? extends ItemLike> item : items) {
            output.accept(new ItemStack(item.get()));
        }
    }

    public static void patchBlockEntityValidity() {
        if (!ModCompat.HEARTH_AND_TIMBER) {
            return;
        }

        var windowCasingType = EntityTypeRegistry.WINDOW_CASING_BLOCK_ENTITY.get();
        BlockEntityTypeMixin accessor = (BlockEntityTypeMixin) windowCasingType;

        Set<Block> validBlocks = new HashSet<>(accessor.getValidBlocks());
        validBlocks.add(HearthAndTimberBlocks.APPLE_WINDOW_CASING.get());
        accessor.setValidBlocks(validBlocks);
    }

    private static void insertAfterDarkCherry(
            Map<String, ? extends RegistrySupplier<Block>> compatMap,
            RegistrySupplier<Block> ourBlock) {
        var darkCherry = compatMap.get("dark_cherry");
        if (darkCherry != null) {
            insert(darkCherry, ourBlock);
        }
    }

    private static void insert(
            java.util.function.Supplier<? extends net.minecraft.world.level.ItemLike> after,
            java.util.function.Supplier<? extends net.minecraft.world.level.ItemLike> value) {
        PlatformHelper.insertAfter(TAB_KEY, after, value);
    }
}