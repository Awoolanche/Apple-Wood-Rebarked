package net.awoolanche.applewoodrebarked.mixin;

import dev.architectury.hooks.item.tool.AxeItemHooks;
import net.satisfy.vinery.core.Vinery;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.awoolanche.applewoodrebarked.blocks.ModBlocks.*;

@Mixin(value = Vinery.class, remap = false)
public abstract class VineryMixin {
    @Inject(method = "commonSetup", at = @At("RETURN"))
    private static void onCommonSetup(CallbackInfo ci) {
        // This forces our strippables (and any other tweaks) to happen inside Vinery's timeline
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_LOG.get(), STRIPPED_APPLE_LOG.get());
        AxeItemHooks.addStrippable(ObjectRegistry.APPLE_WOOD.get(), STRIPPED_APPLE_WOOD.get());
    }
}