package net.awoolanche.applewoodrebarked.blocks;

import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.InteractionEvent;
import net.minecraft.ChatFormatting;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;

public class CrateBlock extends Block {
    public static final EnumProperty<CrateContents> CONTENTS = EnumProperty.create("contents", CrateContents.class);

    private static final boolean CONSUME_BAG = true;

    private static final VoxelShape CAVITY = Block.box(1, 1, 1, 15, 15, 15);
    private static final VoxelShape OPENING = Block.box(2, 1, 2, 14, 16, 14);
    private static final VoxelShape INSIDE = Shapes.or(CAVITY, OPENING);
    private static final VoxelShape EMPTY_SHAPE = Shapes.join(Shapes.block(), INSIDE, BooleanOp.ONLY_FIRST);
    private static final VoxelShape FULL_SHAPE = Shapes.block();

    public CrateBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(CONTENTS, CrateContents.EMPTY));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(CONTENTS);
    }

    public static void registerEvents() {
        InteractionEvent.RIGHT_CLICK_BLOCK.register(CrateBlock::onRightClickBlock);
    }

    private static EventResult onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, Direction face) {
        if (hand != InteractionHand.MAIN_HAND || !player.isShiftKeyDown() || player.isSpectator()) {
            return EventResult.pass();
        }
        Level level = player.level();
        BlockState state = level.getBlockState(pos);
        if (!(state.getBlock() instanceof CrateBlock)) return EventResult.pass();

        CrateContents contents = state.getValue(CONTENTS);
        if (contents == CrateContents.EMPTY) return EventResult.pass();

        if (!level.isClientSide) {
            level.setBlock(pos, state.setValue(CONTENTS, CrateContents.EMPTY), Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.WOOD_BREAK, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (CONSUME_BAG && !player.getAbilities().instabuild) {
                ItemStack bag = contents.createBagStack();
                if (bag != null && !player.getInventory().add(bag)) {
                    player.drop(bag, false);
                }
            }
        }
        return EventResult.interruptTrue();
    }

    @Override
    public void appendHoverText(ItemStack stack, Item.TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, context, tooltip, flag);
        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("tooltip.applewoodrebarked.crate").withStyle(ChatFormatting.GRAY));
    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return state.getValue(CONTENTS) == CrateContents.EMPTY ? EMPTY_SHAPE : FULL_SHAPE;
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                              Player player, InteractionHand hand, BlockHitResult hit) {
        CrateContents contents = CrateContents.fromStack(stack);
        if (contents == null || state.getValue(CONTENTS) == contents) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        BlockState filled = state.setValue(CONTENTS, contents);
        if (!level.isUnobstructed(filled, pos, CollisionContext.empty())) {
            return ItemInteractionResult.FAIL;
        }
        if (!level.isClientSide) {
            level.setBlock(pos, filled, Block.UPDATE_ALL);
            level.playSound(null, pos, SoundEvents.WOOD_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
            if (CONSUME_BAG && !player.getAbilities().instabuild) stack.shrink(1);
        }
        return ItemInteractionResult.sidedSuccess(level.isClientSide);
    }

    @Override
    protected List<ItemStack> getDrops(BlockState state, LootParams.Builder params) {
        List<ItemStack> drops = new ArrayList<>(super.getDrops(state, params));
        CrateContents contents = state.getValue(CONTENTS);
        if (CONSUME_BAG && contents != CrateContents.EMPTY) {
            ItemStack bag = contents.createBagStack();
            if (bag != null && !bag.isEmpty()) {
                drops.add(bag);
            }
        }
        return drops;
    }

}