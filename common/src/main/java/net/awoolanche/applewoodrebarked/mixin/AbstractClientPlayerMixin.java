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

    @Inject(method = "getFieldOfViewModifier", at = @At(value = "TAIL"), cancellable = true)
    private void applewood$getSlingshotFov(CallbackInfoReturnable<Float> info) {
        AbstractClientPlayer player = (AbstractClientPlayer) (Object) this;
        ItemStack itemStack = player.getUseItem();

        if (player.isUsingItem() && itemStack.is(ModItems.SLINGSHOT.get())) {
            int i = player.getTicksUsingItem();
            float g = (float) i / 5.0f; // Charge time
            g = g > 1.0f ? 1.0f : g * g;

            float f = info.getReturnValue();
            f *= 1.0f - g * 0.15f;

            info.setReturnValue(Mth.lerp(Minecraft.getInstance().options.fovEffectScale().get().floatValue(), 1.0f, f));
        }
    }
}
