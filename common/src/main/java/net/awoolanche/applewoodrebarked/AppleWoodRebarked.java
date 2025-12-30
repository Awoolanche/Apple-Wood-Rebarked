package net.awoolanche.applewoodrebarked;


import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.entities.ModEntities;
import net.awoolanche.applewoodrebarked.items.ModItems;
import net.awoolanche.applewoodrebarked.render.AppleHangingSignRenderer;
import net.awoolanche.applewoodrebarked.render.AppleSignRenderer;
import net.awoolanche.applewoodrebarked.util.ModTabs;
import net.minecraft.world.level.block.Block;
import net.satisfy.vinery.client.render.block.LatticeRenderer;
import net.satisfy.vinery.client.render.block.storage.ShelfRenderer;
import net.satisfy.vinery.client.render.block.storage.StorageBlockEntityRenderer;
import net.satisfy.vinery.client.render.block.storage.StorageTypeRenderer;
import net.satisfy.vinery.core.registry.ObjectRegistry;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import dev.architectury.registry.client.rendering.BlockEntityRendererRegistry;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import static net.awoolanche.applewoodrebarked.blocks.ModBlocks.APPLE_LATTICE;

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

    public static void registerTexture(Block block, ResourceLocation texture) {
        try {
            Method getMapMethod = LatticeRenderer.class.getDeclaredMethod("getTextureMap");
            getMapMethod.setAccessible(true);
            Map<Block, ResourceLocation> textureMap = (Map<Block, ResourceLocation>) getMapMethod.invoke(null);

            if (textureMap != null) {
                textureMap.put(block, texture);
            }
        } catch (Exception e) {
            System.err.println("Failed to inject Apple Lattice texture into Vinery Renderer!");
            e.printStackTrace();
        }
    }

    public static void commonSetup() {
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), ModBlocks.STRIPPED_APPLE_LOG.get());
            AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), ModBlocks.STRIPPED_APPLE_WOOD.get());
    }

    public static void registerBlockEntityRenderers() {
        BlockEntityRendererRegistry.register(ModBlockEntities.APPLE_SIGN.get(), AppleSignRenderer::new);
        BlockEntityRendererRegistry.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), AppleHangingSignRenderer::new);
    }

    public static final ModelLayerLocation APPLE_SIGN = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "sign_apple"), "main"
    );
    public static final ModelLayerLocation APPLE_HANGING_SIGN = new ModelLayerLocation(
            ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "hanging_sign_apple"), "main"
    );
}
