package net.awoolanche.applewoodrebarked.fabric;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.fabric.api.resource.ResourcePackActivationType;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.resources.ResourceLocation;

import java.util.Optional;

public final class AppleWoodRebarkedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            Class.forName("net.awoolanche.applewoodrebarked.blocks.ModBlocks");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        AppleWoodRebarked.init();
        AppleWoodRebarked.commonSetup();

        Optional<ModContainer> modContainer = FabricLoader.getInstance().getModContainer(AppleWoodRebarked.MOD_ID);
        modContainer.ifPresent(container -> ResourceManagerHelper.registerBuiltinResourcePack(
                ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, "resourcepacks/vinery_overrides"),
                container,
                ResourcePackActivationType.ALWAYS_ENABLED
        ));
    }
}