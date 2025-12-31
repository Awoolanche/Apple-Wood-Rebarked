package net.awoolanche.applewoodrebarked.util;

import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.items.ModItems;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;


public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create("applewoodrebarked", Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> APPLE_WOOD_REBARKED_TAB = TABS.register("apple_wood_rebarked_tab", () -> CreativeModeTab.builder(CreativeModeTab.Row.TOP, 0)
            .icon(() -> new ItemStack(ModItems.TEST_ITEM.get()))
            .title(Component.translatable("itemgroup.applewoodrebarked.apple_wood_rebarked_tab"))
            .displayItems((parameters, out) -> {

                out.accept(ModItems.TEST_ITEM.get());
                out.accept(ModBlocks.TEST_BLOCK.get());
                out.accept(ModBlocks.APPLE_PLANKS.get());
                out.accept(ModBlocks.APPLE_STAIRS.get());
                out.accept(ModBlocks.APPLE_SLAB.get());
                out.accept(ModBlocks.STRIPPED_APPLE_LOG.get());
                out.accept(ModBlocks.STRIPPED_APPLE_WOOD.get());
                out.accept(ModBlocks.APPLE_TRAPDOOR.get());
                out.accept(ModBlocks.APPLE_DOOR.get());
                out.accept(ModBlocks.APPLE_FENCE.get());
                out.accept(ModBlocks.APPLE_FENCE_GATE.get());
                out.accept(ModBlocks.APPLE_BUTTON.get());
                out.accept(ModBlocks.APPLE_PRESSURE_PLATE.get());
                out.accept(ModItems.APPLE_SIGN_ITEM.get());
                out.accept(ModItems.APPLE_HANGING_SIGN_ITEM.get());
                out.accept(ModItems.APPLE_BOAT.get());
                out.accept(ModItems.APPLE_CHEST_BOAT.get());

                out.accept(ModBlocks.APPLE_CHAIR.get());
                out.accept(ModBlocks.APPLE_BEAM.get());
                out.accept(ModBlocks.APPLE_TABLE.get());
                out.accept(ModBlocks.APPLE_BIG_TABLE.get());
                out.accept(ModBlocks.APPLE_LATTICE.get());
                out.accept(ModBlocks.APPLE_SHELF.get());
                out.accept(ModBlocks.APPLE_BARREL.get());
                out.accept(ModBlocks.APPLE_CABINET.get());
                out.accept(ModBlocks.APPLE_DRAWER.get());


                // External mod items
                addExternalItem(out, "vinery", "apple_tree_sapling");
                addExternalItem(out, "vinery", "apple_leaves");
                addExternalItem(out, "vinery", "apple_log");
                addExternalItem(out, "vinery", "apple_wood");

            })
            .build());

    public static void init() {
        TABS.register();
    }

    private static void addExternalItem(CreativeModeTab.Output out, String modId, String itemId) {
        Item item = BuiltInRegistries.ITEM.get(ResourceLocation.fromNamespaceAndPath(modId, itemId));
        if (item != net.minecraft.world.item.Items.AIR) {
            out.accept(item);
        }
    }
}
