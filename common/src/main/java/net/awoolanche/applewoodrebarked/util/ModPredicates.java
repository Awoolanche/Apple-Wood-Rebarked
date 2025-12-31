package net.awoolanche.applewoodrebarked.util;

import net.awoolanche.applewoodrebarked.items.ModItems;

import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.minecraft.resources.ResourceLocation;

public class ModPredicates {
    public static void init() {
        ItemPropertiesRegistry.register(ModItems.SLINGSHOT.get(),
                ResourceLocation.withDefaultNamespace("pull"),
                (stack, level, entity, seed) -> {
                    if (entity == null || entity.getUseItem() != stack) return 0.0F;
                    return (float) (stack.getUseDuration(entity) - entity.getUseItemRemainingTicks()) / 20.0F;
                });

        ItemPropertiesRegistry.register(ModItems.SLINGSHOT.get(),
                ResourceLocation.withDefaultNamespace("pulling"),
                (stack, level, entity, seed) ->
                        entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F
        );
    }
}
