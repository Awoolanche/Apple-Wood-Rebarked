package net.awoolanche.applewoodrebarked.platform;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.network.chat.Component;
import net.minecraft.world.level.ItemLike;

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

    @ExpectPlatform
    public static void insertAfter(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> value) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static void insertBefore(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> before, Supplier<? extends ItemLike> value) {
        throw new AssertionError();
    }
}
