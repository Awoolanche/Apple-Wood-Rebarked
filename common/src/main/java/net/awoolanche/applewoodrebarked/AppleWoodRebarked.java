package net.awoolanche.applewoodrebarked;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.items.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AppleWoodRebarked {
    public static final String MOD_ID = "applewoodrebarked";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    public static void init() {
        // Write common init code here.

        LOGGER.info("[Let's Do Add-on] Apple Wood Rebarked initialized!");

        // Initialization
        ModBlocks.init();
        ModItems.init();

    }
}
