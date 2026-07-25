package net.awoolanche.applewoodrebarked.compat;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.platform.PlatformHelper;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.block.Block;
import net.satisfy.hearth_and_timber.core.registry.ObjectRegistry;
import net.satisfy.hearth_and_timber.core.registry.TabRegistry;

import java.util.Map;

public class HearthAndTimberCompat {

    private static final ResourceKey<CreativeModeTab> COMPAT_TAB_KEY = ResourceKey.create(
            Registries.CREATIVE_MODE_TAB,
            ResourceLocation.fromNamespaceAndPath("hearth_and_timber", "hearth_and_timber_compat"));

    public static void registerCreativeTabs() {
        if (!ModCompat.HEARTH_AND_TIMBER) {
            return;
        }

        if (TabRegistry.HEARTH_AND_TIMBER_COMPAT_LAYER_TAB != null) {
            insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLES, HearthAndTimberBlocks.APPLE_SHINGLES);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLE_STAIRS, HearthAndTimberBlocks.APPLE_SHINGLE_STAIRS);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_SHINGLE_SLAB, HearthAndTimberBlocks.APPLE_SHINGLE_SLAB);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_SUPPORTS, HearthAndTimberBlocks.APPLE_SUPPORT);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_PILLARS, HearthAndTimberBlocks.APPLE_PILLAR);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_RAILINGS, HearthAndTimberBlocks.APPLE_RAILING);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_WINDOW_CASINGS, HearthAndTimberBlocks.APPLE_WINDOW_CASING);
            insertAfterDarkCherry(ObjectRegistry.COMPAT_BOARDS, HearthAndTimberBlocks.APPLE_BOARD);

            PlatformHelper.insertAfter(COMPAT_TAB_KEY, HearthAndTimberBlocks.APPLE_WINDOW_CASING, HearthAndTimberBlocks.APPLE_WINDOW_PANE);
            PlatformHelper.insertAfter(COMPAT_TAB_KEY, HearthAndTimberBlocks.APPLE_WINDOW_PANE, HearthAndTimberBlocks.APPLE_WINDOW);
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
}