package net.awoolanche.applewoodrebarked.blocks;

import net.awoolanche.applewoodrebarked.blockEntities.AppleSignBlockEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.StandingSignBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;

public class AppleStandingSignBlock extends StandingSignBlock {
    public AppleStandingSignBlock(Properties properties, WoodType type) {
        super(type, properties);
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AppleSignBlockEntity(pPos, pState);
    }
}
