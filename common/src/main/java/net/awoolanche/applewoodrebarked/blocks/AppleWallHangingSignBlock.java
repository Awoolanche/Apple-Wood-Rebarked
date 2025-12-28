package net.awoolanche.applewoodrebarked.blocks;

import net.awoolanche.applewoodrebarked.blockEntities.AppleHangingSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallHangingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AppleWallHangingSignBlock extends WallHangingSignBlock {
    public AppleWallHangingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AppleHangingSignBlockEntity(pPos, pState);
    }
}
