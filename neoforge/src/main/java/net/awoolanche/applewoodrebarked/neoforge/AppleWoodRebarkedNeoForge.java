    package net.awoolanche.applewoodrebarked.neoforge;

    import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
    import net.awoolanche.applewoodrebarked.blockEntities.ModBlockEntities;
    import net.awoolanche.applewoodrebarked.entities.AppleBoatEntity;
    import net.awoolanche.applewoodrebarked.entities.ModEntities;
    import net.awoolanche.applewoodrebarked.render.AppleBoatRenderer;
    import net.awoolanche.applewoodrebarked.util.ModPredicates;
    import net.awoolanche.applewoodrebarked.util.ModWoodType;

    import net.minecraft.client.model.BoatModel;
    import net.minecraft.client.model.ChestBoatModel;
    import net.minecraft.client.model.geom.ModelLayerLocation;
    import net.minecraft.client.renderer.Sheets;
    import net.minecraft.client.renderer.blockentity.BlockEntityRenderers;
    import net.minecraft.client.renderer.blockentity.HangingSignRenderer;
    import net.minecraft.client.renderer.blockentity.SignRenderer;
    import net.minecraft.client.renderer.entity.ThrownItemRenderer;
    import net.minecraft.network.chat.Component;
    import net.minecraft.resources.ResourceLocation;
    import net.minecraft.server.packs.*;
    import net.minecraft.server.packs.repository.Pack;
    import net.minecraft.server.packs.repository.PackSource;
    import net.minecraft.world.level.block.state.properties.WoodType;
    import net.neoforged.api.distmarker.Dist;
    import net.neoforged.api.distmarker.OnlyIn;
    import net.neoforged.bus.api.IEventBus;
    import net.neoforged.bus.api.SubscribeEvent;
    import net.neoforged.fml.ModList;
    import net.neoforged.fml.common.EventBusSubscriber;
    import net.neoforged.fml.common.Mod;
    import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
    import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
    import net.neoforged.neoforge.client.event.EntityRenderersEvent;
    import net.neoforged.neoforge.event.AddPackFindersEvent;
    import org.jetbrains.annotations.NotNull;

    import java.nio.file.Path;
    import java.util.Optional;


    @Mod(AppleWoodRebarked.MOD_ID)
    public final class AppleWoodRebarkedNeoForge {

        public AppleWoodRebarkedNeoForge(IEventBus modEventBus) {
            AppleWoodRebarked.init();
            modEventBus.addListener(this::onClientSetup);
            modEventBus.addListener(this::commonSetup);
        }

        public void onClientSetup(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ModPredicates.init();
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

        @EventBusSubscriber(modid = AppleWoodRebarked.MOD_ID, value = Dist.CLIENT)
        public static class ClientEvents {

            @SubscribeEvent
            public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
                event.registerEntityRenderer(ModEntities.APPLE_BOAT.get(), (context) -> new AppleBoatRenderer(context, false));
                event.registerEntityRenderer(ModEntities.APPLE_CHEST_BOAT.get(), (context) -> new AppleBoatRenderer(context, true));
                event.registerEntityRenderer(ModEntities.SLINGSHOT_PROJECTILE.get(), ThrownItemRenderer::new);
            }

            @SubscribeEvent
            public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
                for (AppleBoatEntity.Type type : AppleBoatEntity.Type.values()) {
                    event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, type.getModelLocation()), "main"), BoatModel::createBodyModel);
                    event.registerLayerDefinition(new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, type.getChestModelLocation()), "main"), ChestBoatModel::createBodyModel);
                }
            }

            @OnlyIn(Dist.CLIENT)
            @SubscribeEvent
            public static void onAddPackFinders(AddPackFindersEvent event) {
                if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                    Path packPath = ModList.get().getModFileById(AppleWoodRebarked.MOD_ID)
                            .getFile()
                            .findResource("resourcepacks/vinery_overrides");

                    event.addRepositorySource(consumer -> {
                        PackLocationInfo packLocationInfo = new PackLocationInfo(
                                ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, "resourcepacks/vinery_overrides").toString(),
                                Component.literal("Vinery Overrides"),
                                PackSource.BUILT_IN,
                                Optional.empty()
                        );

                        Pack.ResourcesSupplier resourcesSupplier = new Pack.ResourcesSupplier() {
                            @Override
                            public @NotNull PathPackResources openPrimary(PackLocationInfo info) {
                                return new PathPackResources(info, packPath);
                            }

                            @Override
                            public @NotNull PackResources openFull(PackLocationInfo info, Pack.Metadata metadata) {
                                return new PathPackResources(info, packPath);
                            }
                        };

                        Pack pack = Pack.readMetaAndCreate(
                                packLocationInfo,
                                resourcesSupplier,
                                PackType.CLIENT_RESOURCES,
                                new PackSelectionConfig(true, Pack.Position.TOP, false)
                        );

                        if (pack != null) {
                            consumer.accept(pack);
                        }
                    });
                }
            }
        }
    }
