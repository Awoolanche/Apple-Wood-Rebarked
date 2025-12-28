package net.awoolanche.applewoodrebarked.blockEntities;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.blocks.ModBlocks;

import com.mojang.datafixers.types.Type;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
        public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES = DeferredRegister.create("applewoodrebarked", Registries.BLOCK_ENTITY_TYPE);;
        public static final RegistrySupplier<BlockEntityType<AppleSignBlockEntity>> APPLE_SIGN = BLOCK_ENTITY_TYPES.register("apple_sign", () -> BlockEntityType.Builder.of(AppleSignBlockEntity::new, new Block[]{(Block) ModBlocks.APPLE_SIGN.get(), (Block)ModBlocks.APPLE_WALL_SIGN.get()}).build((Type)null));
        public static final RegistrySupplier<BlockEntityType<AppleHangingSignBlockEntity>> APPLE_HANGING_SIGN = BLOCK_ENTITY_TYPES.register("apple_hanging_sign", () -> BlockEntityType.Builder.of(AppleHangingSignBlockEntity::new, new Block[]{(Block)ModBlocks.APPLE_HANGING_SIGN.get(), (Block)ModBlocks.APPLE_WALL_HANGING_SIGN.get()}).build((Type)null));


        private static <T extends BlockEntityType<?>> RegistrySupplier<T> registerBlockEntity(String path, Supplier<T> type) {
            return BLOCK_ENTITY_TYPES.register(AppleWoodRebarked.identifier(path), type);
        }
        public static void init() {
            BLOCK_ENTITY_TYPES.register();
        }
    }