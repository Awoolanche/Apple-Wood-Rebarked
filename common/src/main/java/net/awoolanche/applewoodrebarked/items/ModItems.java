package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;

import java.util.function.Supplier;

import static net.awoolanche.applewoodrebarked.blocks.ModBlocks.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("applewoodrebarked", Registries.ITEM);

    public static final RegistrySupplier<Item> TEST_ITEM = registerItem("test_item", () -> new Item(baseProperties("test_item")));;
    public static final RegistrySupplier<Item> APPLE_SIGN_ITEM = ITEMS.register("apple_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.APPLE_SIGN.get(), APPLE_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> APPLE_HANGING_SIGN_ITEM = ITEMS.register("apple_hanging_sign", () -> new HangingSignItem(APPLE_HANGING_SIGN.get(), APPLE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));


    public static void init() {
        ITEMS.register();
    }

    public static RegistrySupplier<Item> registerItem(String name, Supplier<Item> item) {
        return ITEMS.register(ResourceLocation.fromNamespaceAndPath("applewoodrebarked", name), item);
    }
    public static Item.Properties baseProperties(String name) {
        return new Item.Properties();
    }
}