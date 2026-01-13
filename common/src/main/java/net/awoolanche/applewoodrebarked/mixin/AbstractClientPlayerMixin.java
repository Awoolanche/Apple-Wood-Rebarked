package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.items.ModItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayer.class)
public abstract class AbstractClientPlayerMixin {

    @Inject(method = "getFieldOfViewModifier", at = @At("RETURN"), cancellable = true)
    private void applewood$slingshotFov(CallbackInfoReturnable<Float> info) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        ItemStack itemStack = player.getUseItem();

        if (player.isUsingItem() && itemStack.is(ModItems.SLINGSHOT.get())) {
            int i = player.getTicksUsingItem();

            float pullProgress = (float) i / 20.0f;

            if (pullProgress > 1.0f) { pullProgress = 1.0f; } else { pullProgress *= pullProgress; }

            float currentFov = info.getReturnValue();
            float zoomedFov = currentFov * (1.0f - pullProgress * 0.15f);

            info.setReturnValue(Mth.lerp(
                    Minecraft.getInstance().options.fovEffectScale().get().floatValue(),
                    currentFov,
                    zoomedFov
            ));
        }
    }
}