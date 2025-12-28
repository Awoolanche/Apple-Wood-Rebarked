package net.awoolanche.applewoodrebarked.blockEntities;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class AppleHangingSignBlockEntity extends AppleSignBlockEntity {

    public AppleHangingSignBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.APPLE_HANGING_SIGN.get(), blockPos, blockState);
    }

    @Override
    public @NotNull BlockEntityType<?> getType() {
        return ModBlockEntities.APPLE_HANGING_SIGN.get();
    }
}