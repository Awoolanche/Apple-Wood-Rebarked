package net.awoolanche.applewoodrebarked.compat;

import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.satisfy.hearth_and_timber.core.block.*;

public final class HearthAndTimberBlocks {
    private HearthAndTimberBlocks() {}

    public static RegistrySupplier<Block> APPLE_SHINGLES;
    public static RegistrySupplier<Block> APPLE_SHINGLE_STAIRS;
    public static RegistrySupplier<Block> APPLE_SHINGLE_SLAB;
    public static RegistrySupplier<Block> APPLE_SUPPORT_BEAM;
    public static RegistrySupplier<Block> APPLE_SUPPORT;
    public static RegistrySupplier<Block> APPLE_PILLAR;
    public static RegistrySupplier<Block> APPLE_RAILING;
    public static RegistrySupplier<Block> APPLE_WINDOW_CASING;
    public static RegistrySupplier<Block> APPLE_WINDOW_PANE;
    public static RegistrySupplier<Block> APPLE_WINDOW;
    public static RegistrySupplier<Block> APPLE_BOARD;

    public static void register() {
        APPLE_SHINGLES = ModBlocks.registerWithItem("apple_shingles", () -> new Block(BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_PLANKS.get()).sound(SoundType.WOOD).strength(2.0F, 3.0F)));
        APPLE_SHINGLE_STAIRS = ModBlocks.registerWithItem("apple_shingle_stairs", () -> new StairBlock(APPLE_SHINGLES.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_STAIRS.get())));
        APPLE_SHINGLE_SLAB = ModBlocks.registerWithItem("apple_shingle_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_SLAB.get())));
        APPLE_SUPPORT_BEAM = ModBlocks.registerWithItem("apple_support_beam", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG).sound(SoundType.WOOD)));
        APPLE_SUPPORT = ModBlocks.registerWithItem("apple_support", () -> new SupportBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_PLANKS.get())));
        APPLE_PILLAR = ModBlocks.registerWithItem("apple_pillar", () -> new PillarBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_PLANKS.get())));
        APPLE_RAILING = ModBlocks.registerWithItem("apple_railing", () -> new RailingBlock(ModBlocks.APPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_PLANKS.get()).noOcclusion()));
        APPLE_WINDOW_CASING = ModBlocks.registerWithItem("apple_window_casing", () -> new WindowCasingBlock(BlockBehaviour.Properties.ofFullCopy(ModBlocks.APPLE_PLANKS.get()).noOcclusion()));
        APPLE_WINDOW_PANE = ModBlocks.registerWithItem("apple_window_pane", () -> new WindowBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS_PANE).sound(SoundType.GLASS)));
        APPLE_WINDOW = ModBlocks.registerWithItem("apple_window", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.GLASS)));
        APPLE_BOARD = ModBlocks.registerWithItem("apple_board", WoodenBoardBlock::new);
    }
}