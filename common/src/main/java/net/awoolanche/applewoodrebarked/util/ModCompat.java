package net.awoolanche.applewoodrebarked.util;

import dev.architectury.platform.Platform;

public class ModCompat {
    public static final boolean FARM_AND_CHARM =
            Platform.isModLoaded("farm_and_charm");
    public static final boolean FURNITURE =
            Platform.isModLoaded("furniture");
    public static final boolean HEARTH_AND_TIMBER =
            Platform.isModLoaded("hearth_and_timber");
}