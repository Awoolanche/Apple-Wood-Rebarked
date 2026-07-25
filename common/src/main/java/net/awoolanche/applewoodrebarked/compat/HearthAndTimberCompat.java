package net.awoolanche.applewoodrebarked.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.awoolanche.applewoodrebarked.util.ModTabs;


import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.level.block.Block;
import net.satisfy.hearth_and_timber.core.registry.TabRegistry;

public class HearthAndTimberCompat {

    public static void registerCreativeTabs() {
        if (!ModCompat.HEARTH_AND_TIMBER) {
            return;
        }

        // Furniture CL Tab
        if (TabRegistry.HEARTH_AND_TIMBER_TAB != null) {
            CreativeTabRegistry.append(
                    TabRegistry.HEARTH_AND_TIMBER_COMPAT_LAYER_TAB,
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
                    HearthAndTimberBlocks.APPLE_BOARD
            );
        } else {
            // If other mods aren't loaded, put them in main tab
            CreativeTabRegistry.append(
                    TabRegistry.HEARTH_AND_TIMBER_TAB,
                    HearthAndTimberBlocks.APPLE_SHINGLES,
                    HearthAndTimberBlocks.APPLE_SHINGLE_STAIRS,
                    HearthAndTimberBlocks.APPLE_SHINGLE_SLAB,
                    HearthAndTimberBlocks.APPLE_SUPPORT,
                    HearthAndTimberBlocks.APPLE_SUPPORT_BEAM,
                    HearthAndTimberBlocks.APPLE_PILLAR,
                    HearthAndTimberBlocks.APPLE_RAILING,
                    HearthAndTimberBlocks.APPLE_WINDOW_CASING,
                    HearthAndTimberBlocks.APPLE_WINDOW_PANE,
                    HearthAndTimberBlocks.APPLE_WINDOW,
                    HearthAndTimberBlocks.APPLE_BOARD
            );
        }
    }
}
