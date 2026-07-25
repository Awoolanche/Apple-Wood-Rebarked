package net.awoolanche.applewoodrebarked.fabric.client;

import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.compat.FurnitureBlocks;
import net.awoolanche.applewoodrebarked.compat.HearthAndTimberBlocks;
import net.awoolanche.applewoodrebarked.entities.AppleBoatEntity;
import net.awoolanche.applewoodrebarked.entities.ModEntities;
import net.awoolanche.applewoodrebarked.render.AppleBoatRenderer;
import net.awoolanche.applewoodrebarked.render.AppleHangingSignRenderer;
import net.awoolanche.applewoodrebarked.render.AppleSignRenderer;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.awoolanche.applewoodrebarked.util.ModPredicates;
import net.awoolanche.applewoodrebarked.util.ModWoodType;
import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.client.renderer.entity.ThrownItemRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

import java.util.List;


public class AppleWoodRebarkedFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        ModPredicates.init();
        tryAddWoodType(ModWoodType.APPLE);
        registerBoatModels();


        EntityRendererRegistry.register(ModEntities.APPLE_BOAT.get(), (context) -> new AppleBoatRenderer(context, false));
        EntityRendererRegistry.register(ModEntities.APPLE_CHEST_BOAT.get(), (context) -> new AppleBoatRenderer(context, true));
        EntityRendererRegistry.register(ModEntities.SLINGSHOT_PROJECTILE.get(), ThrownItemRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.APPLE_SIGN.get(), AppleSignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), AppleHangingSignRenderer::new);
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_LATTICE.get(), RenderType.cutout());

        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_LATTICE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_DOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_TRAPDOOR.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.APPLE_CRATE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CHERRY_CRATE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.RED_GRAPE_CRATE.get(), RenderType.cutout());
        BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.WHITE_GRAPE_CRATE.get(), RenderType.cutout());

        if (ModCompat.FARM_AND_CHARM) {
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TOMATO_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.POTATO_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CARROT_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.BEETROOT_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.CORN_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.STRAWBERRY_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.ONION_CRATE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.LETTUCE_CRATE.get(), RenderType.cutout());
        }

        if (ModCompat.LILIS_LUCKY_LURES) {
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.FISH_CRATE.get(), RenderType.cutout());
        }

        if (ModCompat.HERBALBREWS) {
            BlockRenderLayerMap.INSTANCE.putBlock(ModBlocks.TEA_CRATE.get(), RenderType.cutout());
        }

        if (ModCompat.FURNITURE) {
            BlockRenderLayerMap.INSTANCE.putBlock(FurnitureBlocks.APPLE_WARDROBE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(FurnitureBlocks.APPLE_GRANDFATHER_CLOCK.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(FurnitureBlocks.APPLE_BENCH.get(), RenderType.cutout());
        }

        if (ModCompat.HEARTH_AND_TIMBER) {
            BlockRenderLayerMap.INSTANCE.putBlock(HearthAndTimberBlocks.APPLE_WINDOW_PANE.get(), RenderType.cutout());
            BlockRenderLayerMap.INSTANCE.putBlock(HearthAndTimberBlocks.APPLE_WINDOW.get(), RenderType.cutout());
        }

        java.util.List<RegistrySupplier<Block>> fcCrates =
                java.util.Arrays.asList(
                        ModBlocks.TOMATO_CRATE,
                        ModBlocks.POTATO_CRATE,
                        ModBlocks.CARROT_CRATE,
                        ModBlocks.BEETROOT_CRATE,
                        ModBlocks.CORN_CRATE,
                        ModBlocks.STRAWBERRY_CRATE,
                        ModBlocks.ONION_CRATE,
                        ModBlocks.LETTUCE_CRATE
                );

        for (var crate : fcCrates) {
            if (crate != null && crate.isPresent()) {
                BlockRenderLayerMap.INSTANCE.putBlock(crate.get(), RenderType.cutout());
            }
        }
    }

    private void registerBoatModels() {
        for (AppleBoatEntity.Type type : AppleBoatEntity.Type.values()) {
            String modId = AppleWoodRebarked.MOD_ID;
            EntityModelLayerRegistry.registerModelLayer(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(modId, type.getModelLocation()), "main"), BoatModel::createBodyModel);
            EntityModelLayerRegistry.registerModelLayer(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(modId, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
        }
    }

    private static void tryAddWoodType(net.minecraft.world.level.block.state.properties.WoodType woodType) {
        try {
            Class<?> sheetsClass = Class.forName("net.minecraft.client.renderer.Sheets");
            try {
                java.lang.reflect.Method m = sheetsClass.getMethod("addWoodType", net.minecraft.world.level.block.state.properties.WoodType.class);
                m.invoke(null, woodType);
                return;
            } catch (NoSuchMethodException ignored) {
            }

            try {
                Class<?> trl = Class.forName("net.minecraft.client.renderer.TexturedRenderLayers");
                java.lang.reflect.Method m2 = trl.getMethod("addWoodType", net.minecraft.world.level.block.state.properties.WoodType.class);
                m2.invoke(null, woodType);
                return;
            } catch (NoSuchMethodException ignored) {
            }
        } catch (ReflectiveOperationException ignored) {
        }
    }
}