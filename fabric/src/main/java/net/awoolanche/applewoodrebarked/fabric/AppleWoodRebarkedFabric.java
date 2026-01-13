package net.awoolanche.applewoodrebarked.fabric;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.fabricmc.api.ModInitializer;


public final class AppleWoodRebarkedFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        try {
            Class.forName("net.awoolanche.applewoodrebarked.blocks.ModBlocks"); }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        AppleWoodRebarked.init();
        AppleWoodRebarked.commonSetup();
    }
}
