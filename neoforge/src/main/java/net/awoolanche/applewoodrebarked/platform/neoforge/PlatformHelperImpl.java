package net.awoolanche.applewoodrebarked.platform.neoforge;

import net.awoolanche.applewoodrebarked.items.SlingshotItem;
import net.awoolanche.applewoodrebarked.util.ModAmmoTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static net.awoolanche.applewoodrebarked.entities.ModEntities.ENTITY_TYPES;

public class PlatformHelperImpl {
    public static <T extends Entity> Supplier<EntityType<T>> registerBoatType(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, int clientTrackingRange) {
        return ENTITY_TYPES.register(name, () -> EntityType.Builder.of(factory, category).sized(width, height).build(name));
    }

    @OnlyIn(Dist.CLIENT)
    public static void appendTooltip(ItemStack stack, List<Component> tooltip) {
        LocalPlayer player = Minecraft.getInstance().player;
        if (player != null) {
            ItemStack ammo = SlingshotItem.findAmmo(player);
            ModAmmoTooltip.appendTooltip(ammo, tooltip, player.getAbilities().instabuild);
        }
    }

    public static void insertAfter(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> after, Supplier<? extends ItemLike> value) {
        PENDING.add(new Insertion(tabKey, after, value, true));
    }

    public static void insertBefore(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> before, Supplier<? extends ItemLike> value) {
        PENDING.add(new Insertion(tabKey, before, value, false));
    }

    private record Insertion(ResourceKey<CreativeModeTab> tabKey, Supplier<? extends ItemLike> anchor,
                             Supplier<? extends ItemLike> value, boolean after) {}

    private static final List<Insertion> PENDING = new ArrayList<>();

    @EventBusSubscriber(modid = "applewoodrebarked")
    public static class TabInsertionHandler {
        @SubscribeEvent
        public static void onBuildContents(BuildCreativeModeTabContentsEvent event) {
            for (Insertion insertion : PENDING) {
                if (event.getTabKey() == insertion.tabKey()) {
                    ItemStack anchorStack = new ItemStack(insertion.anchor().get());
                    ItemStack valueStack = new ItemStack(insertion.value().get());
                    if (insertion.after()) {
                        event.insertAfter(anchorStack, valueStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    } else {
                        event.insertBefore(anchorStack, valueStack, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS);
                    }
                }
            }
        }
    }
}