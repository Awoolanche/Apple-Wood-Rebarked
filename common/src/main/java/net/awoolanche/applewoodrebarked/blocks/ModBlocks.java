package net.awoolanche.applewoodrebarked.blocks;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class ModBlocks {

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("applewoodrebarked", Registries.BLOCK);

    public static RegistrySupplier<Block> TEST_BLOCK;


    // Initialization
    public static void init() {
        TEST_BLOCK = registerBlock("test_block", () -> new Block(baseProperties("test_block")));


        BLOCKS.register();
    }


    // Registries
    public static RegistrySupplier<Block> registerBlock(String name, Supplier<Block> block) {
        return BLOCKS.register(ResourceLocation.fromNamespaceAndPath("applewoodrebarked", name), block);
    }

    public static BlockBehaviour.Properties baseProperties(String name) {
        return BlockBehaviour.Properties.of();
    }
}
