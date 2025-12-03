package net.awoolanche.applewoodrebarked.util;

import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.items.ModItems;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    private static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create("applewoodrebarked", Registries.CREATIVE_MODE_TAB);

    public static RegistrySupplier<CreativeModeTab> APPLE_WOOD_REBARKED_TAB;

    public static void init() {
        APPLE_WOOD_REBARKED_TAB = TABS.register("apple_wood_rebarked_tab",
                () -> CreativeTabRegistry.create(Component.translatable("itemgroup.applewoodrebarked.apple_wood_rebarked_tab"),
                        () -> new ItemStack(ModItems.TEST_ITEM.get())));

        TABS.register();
    }

}
