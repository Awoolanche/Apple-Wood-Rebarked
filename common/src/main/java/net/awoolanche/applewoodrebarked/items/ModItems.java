package net.awoolanche.applewoodrebarked.items;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.util.ModTabs;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;

import java.util.function.Supplier;

public class ModItems {
    private static final DeferredRegister<Item> ITEMS = DeferredRegister.create("applewoodrebarked", Registries.ITEM);

    public static RegistrySupplier<Item> TEST_ITEM;
    public static RegistrySupplier<Item> TEST_BLOCK;

    public static void init() {
        TEST_ITEM = registerItem("test_item", () -> new Item(baseProperties("test_item").arch$tab(ModTabs.APPLE_WOOD_REBARKED_TAB)));
        TEST_BLOCK = registerItem("test_block", () -> new BlockItem(ModBlocks.TEST_BLOCK.get(), baseProperties("test_block").arch$tab(ModTabs.APPLE_WOOD_REBARKED_TAB)));
        ITEMS.register();
    }

    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath("applewoodrebarked", name), item);
    }
    public static Item.Properties baseProperties(String name) {
        return new Item.Properties();
    }
}


