package net.awoolanche.applewoodrebarked.platform.fabric;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.items.SlingshotItem;
import net.awoolanche.applewoodrebarked.util.ModAmmoTooltip;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.satisfy.vinery.platform.PlatformHelper;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

import java.util.List;
import java.util.function.Supplier;

public class PlatformHelperImpl extends PlatformHelper {
    public static <T extends Entity> Supplier<EntityType<T>> registerBoatType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        EntityType<T> registry = Registry.register(BuiltInRegistries.ENTITY_TYPE,
                ResourceLocation.fromNamespaceAndPath(AppleWoodRebarked.MOD_ID, name),
                FabricEntityTypeBuilder.create(category, factory).dimensions(EntityDimensions.scalable(width, height)).trackRangeChunks(clientTrackingRange).build());
        return () -> registry;
    }

    public static void insertAfter(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> value) {
        ItemGroupEvents.modifyEntriesEvent(tabKey).register(entries ->
                entries.addAfter(new ItemStack(after.get()), new ItemStack(value.get())));
    }

    public static void insertBefore(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> before, Supplier<? extends ItemLike> value) {
        ItemGroupEvents.modifyEntriesEvent(tabKey).register(entries ->
                entries.addBefore(new ItemStack(before.get()), new ItemStack(value.get())));
    }

    @Environment(EnvType.CLIENT)
    public static void appendTooltip(ItemStack stack, List<Component> tooltip) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack ammo = SlingshotItem.findAmmo(player);
            ModAmmoTooltip.appendTooltip(ammo, tooltip, player.getAbilities().instabuild);
        }
    }
}
