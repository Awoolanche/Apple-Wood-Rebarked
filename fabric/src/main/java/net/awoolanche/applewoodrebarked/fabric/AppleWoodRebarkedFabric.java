package net.awoolanche.applewoodrebarked.fabric;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;

import java.util.Optional;

public final class AppleWoodRebarkedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        AppleWoodRebarked.init();
        AppleWoodRebarked.commonSetup();

        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(AppleWoodRebarked.MOD_ID);
        modContainer.ifPresent(container -> {
            boolean registered = ResourceManagerHelper.registerBuiltinResourcePack(
                    ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, "vinery_overrides"),
                    container,
                    Component.literal("Vinery Overrides"),
                    ResourcePackActivationType.ALWAYS_ENABLED
            );
        });
    }
}