package net.awoolanche.applewoodrebarked.fabric.client;

import net.awoolanche.applewoodrebarked.render.AppleHangingSignRenderer;
import net.awoolanche.applewoodrebarked.render.AppleSignRenderer;
import net.awoolanche.applewoodrebarked.util.ModWoodType;
import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;


public class AppleWoodRebarkedFabricClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        tryAddWoodType(ModWoodType.APPLE);

        BlockEntityRenderers.register(ModBlockEntities.APPLE_SIGN.get(), AppleSignRenderer::new);
        BlockEntityRenderers.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), AppleHangingSignRenderer::new);
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