package net.awoolanche.applewoodrebarked.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;

import java.util.List;
import java.util.function.Supplier;

public class PlatformHelper {

    @ExpectPlatform
    public static <T extends Entity> Supplier<EntityType<T>> registerBoatType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void appendTooltip(ItemStack stack, List<Component> tooltip) {
        throw new AssertionError();
    }
}
