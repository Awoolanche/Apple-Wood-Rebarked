package net.awoolanche.applewoodrebarked.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import java.util.List;

public class ModAmmoTooltip {

    public static float getDamage(ItemStack ammo) {
        if (ammo.is(Items.FLINT)) return 4f;
        if (ammo.is(Items.FIRE_CHARGE)) return 5f;
        if (ammo.is(Items.CLAY_BALL)) return 2f;
        if (ammo.is(Items.SLIME_BALL)) return 1f;
        if (ammo.is(Items.CHORUS_FRUIT)) return 1f;
        return 0f;
    }

    public static MutableComponent getModifierComponent(ItemStack ammo) {
        if (ammo.is(Items.SLIME_BALL))
            return Component.literal("Bouncy").withStyle(ChatFormatting.GREEN);

        if (ammo.is(Items.CHORUS_FRUIT))
            return Component.literal("Blink").withStyle(ChatFormatting.DARK_PURPLE);

        if (ammo.is(Items.FIRE_CHARGE))
            return Component.literal("Explosive").withStyle(ChatFormatting.RED);

        if (ammo.is(Items.EGG))
            return Component.literal("Clucking").withStyle(ChatFormatting.WHITE);

        return null;
    }

    public static void appendTooltip(ItemStack ammo, List<Component> tooltip, boolean isCreative) {
        ItemStack displayAmmo = (ammo.isEmpty() && isCreative) ? new ItemStack(Items.STONE) : ammo;

        if (displayAmmo.isEmpty()) {
            return;
        }

        tooltip.add(Component.empty());
        tooltip.add(Component.translatable("item.modifiers.mainhand").withStyle(ChatFormatting.GRAY));

        float damage = getDamage(displayAmmo);
        String damageText = (damage == (int)damage) ? String.valueOf((int)damage) : String.valueOf(damage);

        tooltip.add(Component.literal(" ")
                .append(Component.literal(damageText).append(" "))
                .append(Component.translatable("attribute.name.generic.attack_damage"))
                .withStyle(ChatFormatting.DARK_GREEN));

        MutableComponent mod = getModifierComponent(displayAmmo);
        if (mod != null) {
            tooltip.add(Component.literal(" ").append(mod));
        }
    }
}