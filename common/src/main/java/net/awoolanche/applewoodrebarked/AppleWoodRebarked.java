package net.awoolanche.applewoodrebarked;


import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.entities.ModEntities;
import net.awoolanche.applewoodrebarked.items.ModItems;
import net.awoolanche.applewoodrebarked.util.ModTabs;
import net.satisfy.vinery.core.registry.ObjectRegistry;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppleWoodRebarked {
    public static final String MOD_ID = "applewoodrebarked";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    public static ResourceLocation identifier(String path) {
        return ResourceLocation.fromNamespaceAndPath("applewoodrebarked", path);
    }

    public static void init() {
        // Write common init code here.
        LOGGER.info("[Let's Do Add-on] Apple Wood Rebarked initialized!");

        // Initialization
        ModBlocks.init();
        ModEntities.init();
        ModItems.init();
        ModTabs.init();
        ModBlockEntities.init();

    }

    public static void commonSetup() {
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), ModBlocks.STRIPPED_APPLE_LOG.get());
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), ModBlocks.STRIPPED_APPLE_WOOD.get());
    }
}
