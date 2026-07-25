package net.awoolanche.applewoodrebarked.compat;

import com.berksire.furniture.core.block.*;
import com.berksire.furniture.core.registry.EntityTypeRegistry;
import com.berksire.furniture.core.registry.SoundRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;
import net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;

import java.util.HashSet;
import java.util.Set;

public final class FurnitureBlocks {
    private FurnitureBlocks() {}

    public static RegistrySupplier<Block> APPLE_BENCH;
    public static RegistrySupplier<Block> APPLE_CLOCK;
    public static RegistrySupplier<Block> APPLE_GRANDFATHER_CLOCK;
    public static RegistrySupplier<Block> APPLE_MIRROR;
    public static RegistrySupplier<Block> APPLE_DESK_CHAIR;
    public static RegistrySupplier<Block> APPLE_DESK;
    public static RegistrySupplier<Block> APPLE_SHUTTER;
    public static RegistrySupplier<Block> APPLE_DRESSER;
    public static RegistrySupplier<Block> APPLE_WARDROBE;
    public static RegistrySupplier<Block> APPLE_BASE_CABINET;

    public static void register() {
        APPLE_BENCH = ModBlocks.registerWithItem("apple_bench", () -> new BenchBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
        APPLE_CLOCK = ModBlocks.registerWithItem("apple_clock", () -> new ClockBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE), ClockBlock.WoodType.OAK));
        APPLE_GRANDFATHER_CLOCK = ModBlocks.registerWithItem("apple_grandfather_clock", () -> new GrandfatherClockBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
        APPLE_MIRROR = ModBlocks.registerWithItem("apple_mirror", () -> new MirrorBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
        APPLE_DESK_CHAIR = ModBlocks.registerWithItem("apple_desk_chair", () -> new DeskChairBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
        APPLE_DESK = ModBlocks.registerWithItem("apple_desk", () -> new DeskBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
        APPLE_SHUTTER = ModBlocks.registerWithItem("apple_shutter", () -> new ShutterBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS).pushReaction(PushReaction.IGNORE)));
        APPLE_DRESSER = ModBlocks.registerWithItem("apple_dresser", () -> new DresserBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundRegistry.CABINET_OPEN, SoundRegistry.CABINET_CLOSE));
        APPLE_WARDROBE = ModBlocks.registerWithItem("apple_wardrobe", () -> new WardrobeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD)));
        APPLE_BASE_CABINET = ModBlocks.registerWithItem("apple_base_cabinet", () -> new com.berksire.furniture.core.block.CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundRegistry.CABINET_OPEN, SoundRegistry.CABINET_CLOSE));
    }

    public static void fixGrandfatherClockValidBlocks() {
        BlockEntityType<?> clockType = EntityTypeRegistry.GRANDFATHER_CLOCK_BLOCK_ENTITY.get();
        BlockEntityTypeMixin clockAccessor = (BlockEntityTypeMixin) clockType;

        Set<Block> clockBlocks = new HashSet<>(clockAccessor.getValidBlocks());

        clockBlocks.add(APPLE_GRANDFATHER_CLOCK.get());
        clockAccessor.setValidBlocks(clockBlocks);
    }
}