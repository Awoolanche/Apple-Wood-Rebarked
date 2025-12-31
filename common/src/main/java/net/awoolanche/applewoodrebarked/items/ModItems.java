package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.entities.AppleBoatEntity;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.HangingSignItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.satisfy.vinery.core.entity.DarkCherryBoatEntity;
import net.satisfy.vinery.core.item.DarkCherryBoatItem;

import java.util.function.Supplier;

import static net.awoolanche.applewoodrebarked.blocks.ModBlocks.*;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create("applewoodrebarked", Registries.ITEM);

    public static final RegistrySupplier<Item> TEST_ITEM = registerItem("test_item", () -> new Item(baseProperties("test_item")));;
    public static final RegistrySupplier<Item> APPLE_SIGN_ITEM = ITEMS.register("apple_sign", () -> new SignItem(new Item.Properties().stacksTo(16), ModBlocks.APPLE_SIGN.get(), APPLE_WALL_SIGN.get()));
    public static final RegistrySupplier<Item> APPLE_HANGING_SIGN_ITEM = ITEMS.register("apple_hanging_sign", () -> new HangingSignItem(APPLE_HANGING_SIGN.get(), APPLE_WALL_HANGING_SIGN.get(), new Item.Properties().stacksTo(16)));
    public static final RegistrySupplier<Item> APPLE_BOAT = ITEMS.register("apple_boat", () -> new AppleBoatItem(false, AppleBoatEntity.Type.APPLE, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> APPLE_CHEST_BOAT = ITEMS.register("apple_chest_boat", () -> new AppleBoatItem(true, AppleBoatEntity.Type.APPLE, new Item.Properties().stacksTo(1)));
    public static final RegistrySupplier<Item> SLINGSHOT = registerItem("slingshot", () -> new SlingshotItem(new Item.Properties().durability(128).stacksTo(1)));

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