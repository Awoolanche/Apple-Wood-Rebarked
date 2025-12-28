    package net.awoolanche.applewoodrebarked.neoforge;

    import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
    import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
    import net.awoolanche.applewoodrebarked.util.ModWoodType;

    import net.minecraft.client.renderer.Sheets;
    import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
    import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
    import net.minecraft.client.renderer.blockentity.SignRenderer;
    import net.minecraft.world.level.block.state.properties.WoodType;
    import net.neoforged.bus.api.IEventBus;
    import net.neoforged.fml.common.Mod;
    import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;


    @Mod(AppleWoodRebarked.MOD_ID)
    public final class AppleWoodRebarkedNeoForge {

        public AppleWoodRebarkedNeoForge(IEventBus modEventBus) {
            AppleWoodRebarked.init();
            modEventBus.addListener(this::onClientSetup);
        }

        public void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                WoodType.register(ModWoodType.APPLE);
                Sheets.addWoodType(ModWoodType.APPLE);

                BlockEntityRenderers.register(ModBlockEntities.APPLE_SIGN.get(), SignRenderer::new);
                BlockEntityRenderers.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), HangingSignRenderer::new);
            });
        }
    }