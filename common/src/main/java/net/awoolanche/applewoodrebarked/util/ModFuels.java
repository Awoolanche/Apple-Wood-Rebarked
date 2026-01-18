package net.awoolanche.applewoodrebarked.util;

import dev.architectury.registry.fuel.FuelRegistry;
import net.awoolanche.applewoodrebarked.items.ModItems;
import net.minecraft.resources.ResourceLocation;

public class ModFuels {
    public static void init() {
        // 200 ticks = 1 item smelted

        registerFuel(150, "apple_slab");
        registerFuel(300, "apple_planks");
        registerFuel(300, "apple_floorboard");
        registerFuel(300, "apple_stairs");
        registerFuel(300, "apple_beam");
        registerFuel(300, "stripped_apple_log");
        registerFuel(300, "stripped_apple_wood");
        registerFuel(300, "apple_fence");
        registerFuel(300, "apple_fence_gate");
        registerFuel(200, "apple_door");
        registerFuel(300, "apple_trapdoor");
        registerFuel(300, "apple_pressure_plate");
        registerFuel(100, "apple_button");
        registerFuel(300, "apple_sign");
        registerFuel(300, "apple_hanging_sign");
        registerFuel(1200, "apple_boat");
        registerFuel(1200, "apple_chest_boat");
        registerFuel(300, "apple_shelf");
        registerFuel(800, "apple_drawer");
        registerFuel(600, "apple_cabinet");
        registerFuel(300, "apple_big_table");
        registerFuel(300, "apple_chair");
        registerFuel(300, "apple_table");
        registerFuel(300, "apple_lattice");
        registerFuel(300, "apple_barrel");
        registerFuel(300, "apple_wine_rack_small");
        registerFuel(300, "apple_wine_rack_mid");
        registerFuel(300, "apple_wine_rack_big");

        registerFuel(300, "slingshot");
    }

    private static void registerFuel(int ticks, String name) {
        FuelRegistry.register(ticks, ModItems.ITEMS.getRegistrar().get(
                ResourceLocation.fromNamespaceAndPath("applewoodrebarked", name)));
    }
}