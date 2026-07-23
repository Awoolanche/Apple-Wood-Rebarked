package net.awoolanche.applewoodrebarked.blocks;

import net.awoolanche.applewoodrebarked.compat.FurnitureBlocks;
import net.awoolanche.applewoodrebarked.mixin.BlockEntityTypeMixin;
import net.awoolanche.applewoodrebarked.util.ModCompat;
import net.awoolanche.applewoodrebarked.util.ModWoodType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.material.PushReaction;
import net.satisfy.vinery.core.block.*;
import net.satisfy.vinery.core.block.CabinetBlock;
import net.satisfy.vinery.core.registry.EntityTypeRegistry;
import net.satisfy.vinery.core.registry.SoundEventRegistry;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

import static net.awoolanche.applewoodrebarked.items.ModItems.ITEMS;

public class ModBlocks {

    // F&C Compatibility declarations
    public static RegistrySupplier<Block> TOMATO_CRATE;
    public static RegistrySupplier<Block> POTATO_CRATE;
    public static RegistrySupplier<Block> CARROT_CRATE;
    public static RegistrySupplier<Block> BEETROOT_CRATE;
    public static RegistrySupplier<Block> CORN_CRATE;
    public static RegistrySupplier<Block> STRAWBERRY_CRATE;
    public static RegistrySupplier<Block> ONION_CRATE;
    public static RegistrySupplier<Block> LETTUCE_CRATE;

    // Furniture Compatibility Declarations
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

    private static final DeferredRegister<Block> BLOCKS = DeferredRegister.create("applewoodrebarked", Registries.BLOCK);


    public static final RegistrySupplier<Block> TEST_BLOCK = registerWithItem("test_block", () -> new Block(baseProperties("test_block")));

    public static final RegistrySupplier<Block> APPLE_PLANKS = registerWithItem("apple_planks", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> APPLE_STAIRS = registerWithItem("apple_stairs", () -> new StairBlock(APPLE_PLANKS.get().defaultBlockState(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_STAIRS)));
    public static final RegistrySupplier<Block> APPLE_SLAB = registerWithItem("apple_slab", () -> new SlabBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SLAB)));
    public static final RegistrySupplier<Block> STRIPPED_APPLE_LOG = registerWithItem("stripped_apple_log", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<Block> STRIPPED_APPLE_WOOD = registerWithItem("stripped_apple_wood", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<Block> APPLE_TRAPDOOR = registerWithItem("apple_trapdoor", () -> new TrapDoorBlock(ModWoodType.APPLE.setType(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_TRAPDOOR)));
    public static final RegistrySupplier<Block> APPLE_DOOR = registerWithItem("apple_door", () -> new DoorBlock(ModWoodType.APPLE.setType(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_DOOR)));
    public static final RegistrySupplier<Block> APPLE_FENCE = registerWithItem("apple_fence", () -> new FenceBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE)));
    public static final RegistrySupplier<Block> APPLE_FENCE_GATE = registerWithItem("apple_fence_gate", () -> new FenceGateBlock(ModWoodType.APPLE, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_FENCE_GATE)));
    public static final RegistrySupplier<Block> APPLE_BUTTON = registerWithItem("apple_button", () -> new ButtonBlock(ModWoodType.APPLE.setType(), 30, BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_BUTTON)));
    public static final RegistrySupplier<Block> APPLE_PRESSURE_PLATE = registerWithItem("apple_pressure_plate", () -> new PressurePlateBlock(ModWoodType.APPLE.setType(), BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PRESSURE_PLATE)));
    public static final RegistrySupplier<Block> APPLE_SIGN = registerWithoutItem("apple_sign", () -> new AppleStandingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_SIGN), ModWoodType.APPLE));
    public static final RegistrySupplier<Block> APPLE_WALL_SIGN = registerWithoutItem("apple_wall_sign", () -> new AppleWallSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_SIGN), ModWoodType.APPLE));
    public static final RegistrySupplier<Block> APPLE_HANGING_SIGN = registerWithoutItem("apple_hanging_sign", () -> new AppleCeilingHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_HANGING_SIGN), ModWoodType.APPLE));
    public static final RegistrySupplier<Block> APPLE_WALL_HANGING_SIGN = registerWithoutItem("apple_wall_hanging_sign", () -> new AppleWallHangingSignBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_WALL_HANGING_SIGN), ModWoodType.APPLE));


    public static final RegistrySupplier<Block> APPLE_CHAIR = registerWithItem("apple_chair", () -> new ChairBlock(BlockBehaviour.Properties.of().strength(1.5F)));
    public static final RegistrySupplier<Block> APPLE_BEAM = registerWithItem("apple_beam", () -> new RotatedPillarBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<Block> APPLE_TABLE = registerWithItem("apple_table", () -> new TableBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> APPLE_BIG_TABLE = registerWithItem("apple_big_table", () -> new BigTableBlock(BlockBehaviour.Properties.of().strength(2.0F, 2.0F).pushReaction(PushReaction.IGNORE)));
    public static final RegistrySupplier<Block> APPLE_LATTICE = registerWithItem("apple_lattice", () -> new LatticeBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(Blocks.OAK_PLANKS.defaultBlockState().getSoundType()).noOcclusion()/*.isViewBlocking((state, level, pos) -> false).isSuffocating((state, level, pos) -> false)*/));
    public static final RegistrySupplier<Block> APPLE_SHELF = registerWithItem("apple_shelf", () -> new ShelfBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> APPLE_BARREL = registerWithItem("apple_barrel", () -> new AppleBarrelBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BARREL)));
    public static final RegistrySupplier<Block> APPLE_CABINET = registerWithItem("apple_cabinet", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.CABINET_OPEN.get(), SoundEventRegistry.CABINET_CLOSE.get()));
    public static final RegistrySupplier<Block> APPLE_DRAWER = registerWithItem("apple_drawer", () -> new CabinetBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD), SoundEventRegistry.DRAWER_OPEN.get(), SoundEventRegistry.DRAWER_CLOSE.get()));
    public static final RegistrySupplier<Block> APPLE_WINE_RACK_BIG = registerWithItem("apple_wine_rack_big", () -> new NineBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> APPLE_WINE_RACK_SMALL = registerWithItem("apple_wine_rack_small", () -> new FourBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));
    public static final RegistrySupplier<Block> APPLE_WINE_RACK_MID = registerWithItem("apple_wine_rack_mid", () -> new BigBottleStorageBlock(BlockBehaviour.Properties.of().strength(2.0F, 3.0F).sound(SoundType.WOOD).noOcclusion()));

    public static final RegistrySupplier<Block> APPLE_FLOORBOARD = registerWithItem("apple_floorboard", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> APPLE_CRATE = registerWithItem("apple_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> CHERRY_CRATE = registerWithItem("cherry_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> RED_GRAPE_CRATE = registerWithItem("red_grape_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));
    public static final RegistrySupplier<Block> WHITE_GRAPE_CRATE = registerWithItem("white_grape_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS)));

    // F&C Compatibility
    static { if (ModCompat.FARM_AND_CHARM) { TOMATO_CRATE = registerWithItem("tomato_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { POTATO_CRATE = registerWithItem("potato_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { CARROT_CRATE = registerWithItem("carrot_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { BEETROOT_CRATE = registerWithItem("beetroot_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { CORN_CRATE = registerWithItem("corn_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { STRAWBERRY_CRATE = registerWithItem("strawberry_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { ONION_CRATE = registerWithItem("onion_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}
    static { if (ModCompat.FARM_AND_CHARM) { LETTUCE_CRATE = registerWithItem("lettuce_crate", () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.OAK_PLANKS))); }}

    // Furniture Compatibility
    static {
        if (ModCompat.FURNITURE) {
            FurnitureBlocks.register();
        }
    }

    // Initialization
    public static void init() {
        BLOCKS.register();
    }

    // Registries
    public static <T extends Block> RegistrySupplier<T> registerWithItem(String name, Supplier<T> blockSupplier) {
        RegistrySupplier<T> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
        return block;
    }

    public static <T extends Block> RegistrySupplier<T> registerWithoutItem(String name, Supplier<T> blockSupplier) {
        return BLOCKS.register(name, blockSupplier);
    }

    public static BlockBehaviour.Properties baseProperties(String name) {
        return BlockBehaviour.Properties.of();
    }

    public static void fixBlockEntityValidBlocks() {
        BlockEntityType<?> cabinetType = EntityTypeRegistry.CABINET_BLOCK_ENTITY.get();
        BlockEntityTypeMixin accessor = (BlockEntityTypeMixin) cabinetType;

        Set<Block> currentBlocks = accessor.getValidBlocks();

        Set<Block> newBlocks = new HashSet<>(currentBlocks);

        newBlocks.add(ModBlocks.APPLE_CABINET.get());
        newBlocks.add(ModBlocks.APPLE_DRAWER.get());

        accessor.setValidBlocks(newBlocks);

        if (ModCompat.FURNITURE) {
            FurnitureBlocks.fixGrandfatherClockValidBlocks();
        }
    }
}