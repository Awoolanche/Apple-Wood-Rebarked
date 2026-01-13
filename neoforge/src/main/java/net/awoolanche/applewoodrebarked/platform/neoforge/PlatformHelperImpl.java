package net.awoolanche.applewoodrebarked.platform.neoforge;

import net.awoolanche.applewoodrebarked.items.SlingshotItem;
import net.awoolanche.applewoodrebarked.util.ModAmmoTooltip;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;

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
}
