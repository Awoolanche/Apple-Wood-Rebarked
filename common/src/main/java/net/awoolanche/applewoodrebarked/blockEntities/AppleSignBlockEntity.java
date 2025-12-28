package net.awoolanche.applewoodrebarked.blockEntities;

import net.minecraft.world.level.block.entity.SignBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AppleSignBlockEntity extends SignBlockEntity {
    public AppleSignBlockEntity(BlockEntityType<? extends AppleSignBlockEntity> type, BlockPos pos, BlockState state) {
        super(type, pos, state);
    }

    public AppleSignBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.APPLE_SIGN.get(), pPos, pBlockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ModBlockEntities.APPLE_SIGN.get();
    }
}