package net.awoolanche.applewoodrebarked.fabric.client;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.entities.AppleBoatEntity;
import net.awoolanche.applewoodrebarked.entities.ModEntities;
import net.awoolanche.applewoodrebarked.render.AppleBoatRenderer;
import net.awoolanche.applewoodrebarked.render.AppleHangingSignRenderer;
import net.awoolanche.applewoodrebarked.render.AppleSignRenderer;
import net.awoolanche.applewoodrebarked.util.ModWoodType;
import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.client.model.BoatModel;
import net.minecraft.client.model.ChestBoatModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
import net.minecraft.resources.ResourceLocation;


public class AppleWoodRebarkedFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        tryAddWoodType(ModWoodType.APPLE);
        registerBoatModels();

        EntityRendererRegistry.register(ModEntities.APPLE_BOAT.get(), (context) -> new AppleBoatRenderer(context, false));
        EntityRendererRegistry.register(ModEntities.APPLE_CHEST_BOAT.get(), (context) -> new AppleBoatRenderer(context, true));
        BlockEntityRenderers.register(ModBlockEntities.APPLE_SIGN.get(), AppleSignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), AppleHangingSignRenderer::new);
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