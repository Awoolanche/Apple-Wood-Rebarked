package net.awoolanche.applewoodrebarked;


import dev.architectury.event.events.common.LifecycleEvent;
import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.entities.ModEntities;
import net.awoolanche.applewoodrebarked.items.ModItems;
import net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin;
import net.awoolanche.applewoodrebarked.util.ModFuels;
import net.awoolanche.applewoodrebarked.util.ModTabs;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.ObjectRegistry;

import net.minecraft.world.level.block.Block;
import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

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

        LifecycleEvent.SETUP.register(ModFuels::init);
        LifecycleEvent.SETUP.register(ModBlocks::fixBlockEntityValidBlocks);

        LifecycleEvent.SETUP.register(() -> {
                var vineryLatticeType = EntityTypeRegistry.LATTICE.get();
                BlockEntityTypeMixin accessor = (BlockEntityTypeMixin) vineryLatticeType;

                Set<Block> validBlocks = new HashSet<>(accessor.getValidBlocks());

                validBlocks.add(ModBlocks.APPLE_LATTICE.get());

                accessor.setValidBlocks(validBlocks);
        });
    }

    public static void commonSetup() {
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), ModBlocks.STRIPPED_APPLE_LOG.get());
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), ModBlocks.STRIPPED_APPLE_WOOD.get());
    }
}
