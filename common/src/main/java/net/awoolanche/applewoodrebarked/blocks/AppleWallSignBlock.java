package net.awoolanche.applewoodrebarked.blocks;

import net.awoolanche.applewoodrebarked.blockEntities.AppleSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.WallSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AppleWallSignBlock extends WallSignBlock {
    public AppleWallSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AppleSignBlockEntity(pPos, pState);
    }
}
