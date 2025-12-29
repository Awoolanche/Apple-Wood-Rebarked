    package net.awoolanche.applewoodrebarked.neoforge;

    import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
    import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
    import net.awoolanche.applewoodrebarked.entities.AppleBoatEntity;
    import net.awoolanche.applewoodrebarked.entities.ModEntities;
    import net.awoolanche.applewoodrebarked.render.AppleBoatRenderer;
    import net.awoolanche.applewoodrebarked.util.ModWoodType;

    import net.minecraft.client.model.BoatModel;
    import net.minecraft.client.model.ChestBoatModel;
    import net.minecraft.client.model.geom.ModelLayerLocation;
    import net.minecraft.client.renderer.Sheets;
    import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
    import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
    import net.minecraft.client.renderer.blockentity.SignRenderer;
    import net.minecraft.resources.ResourceLocation;
    import net.minecraft.world.level.block.state.properties.WoodType;
    import net.neoforged.bus.api.IEventBus;
    import net.neoforged.bus.api.SubscribeEvent;
    import net.neoforged.fml.common.Mod;
    import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
    import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
    import net.neoforged.neoforge.client.event.EntityRenderersEvent;


    @Mod(AppleWoodRebarked.MOD_ID)
    public final class AppleWoodRebarkedNeoForge {

        public AppleWoodRebarkedNeoForge(IEventBus modEventBus) {
            AppleWoodRebarked.init();
            modEventBus.addListener(this::onClientSetup);
            modEventBus.addListener(this::commonSetup);
            modEventBus.addListener(AppleWoodRebarkedNeoForge::registerLayerDefinitions);
            modEventBus.addListener(AppleWoodRebarkedNeoForge::registerRenderers);
        }

        public void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                WoodType.register(ModWoodType.APPLE);
                Sheets.addWoodType(ModWoodType.APPLE);

                BlockEntityRenderers.register(ModBlockEntities.APPLE_SIGN.get(), SignRenderer::new);
                BlockEntityRenderers.register(ModBlockEntities.APPLE_HANGING_SIGN.get(), HangingSignRenderer::new);
            });
        }

        private void commonSetup(final FMLCommonSetupEvent event) {
            event.enqueueWork(() -> {
                AppleWoodRebarked.commonSetup();
            });
        }

        @SubscribeEvent
        public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(ModEntities.APPLE_BOAT.get(), (context) -> new AppleBoatRenderer(context, false));
            event.registerEntityRenderer(ModEntities.APPLE_CHEST_BOAT.get(), (context) -> new AppleBoatRenderer(context, true));
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            for (AppleBoatEntity.Type type : AppleBoatEntity.Type.values()) {
                event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
                event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
            }
        }
    }
