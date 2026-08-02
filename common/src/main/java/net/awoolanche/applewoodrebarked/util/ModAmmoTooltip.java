package net.awoolanche.applewoodrebarked.util;

import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.component.FireworkExplosion;

import java.util.List;
import java.util.Locale;

public class ModAmmoTooltip {

    public static float getDamage(ItemStack ammo) {
        if (ammo.is(Items.FLINT)) return 4f;
        if (ammo.is(Items.FIRE_CHARGE)) return 5f;
        if (ammo.is(Items.CLAY_BALL)) return 2f;
        if (ammo.is(Items.SLIME_BALL)) return 1f;
        if (ammo.is(Items.CHORUS_FRUIT)) return 1f;
        if (ammo.is(Items.FIREWORK_STAR)) return 4f;
        if (ammo.is(Items.GOLD_NUGGET)) return 4f;
        if (ammo.is(Items.IRON_NUGGET)) return 6f;
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

        if (ammo.is(Items.FIREWORK_STAR))
            return Component.literal("Flashbang").withStyle(ChatFormatting.WHITE);


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
        String damageText = (damage == (int) damage) ? String.valueOf((int) damage) : String.valueOf(damage);

        tooltip.add(Component.literal(" ")
                .append(Component.literal(damageText).append(" "))
                .append(Component.translatable("attribute.name.generic.attack_damage"))
                .withStyle(ChatFormatting.DARK_GREEN));

        MutableComponent mod = getModifierComponent(displayAmmo);
        if (mod != null) {
            tooltip.add(Component.literal(" ").append(mod));
        }

        if (displayAmmo.is(Items.FIREWORK_STAR)) {
            appendFireworkEffectLines(displayAmmo, tooltip);
        }

        if (displayAmmo.is(Items.SPLASH_POTION) || displayAmmo.is(Items.LINGERING_POTION)) {
            appendPotionEffectLines(displayAmmo, tooltip);
        }
    }

    private static void appendFireworkEffectLines(ItemStack star, List<Component> tooltip) {
        FireworkExplosion explosion = star.get(DataComponents.FIREWORK_EXPLOSION);
        if (explosion == null) {
            return;
        }

        IntList colors = explosion.colors();
        if (!colors.isEmpty()) {
            tooltip.add(colorSquaresLine(colors));
        }

        String shapeKey = "item.minecraft.firework_star.shape." + explosion.shape().name().toLowerCase(Locale.ROOT);
        tooltip.add(Component.literal(" ").append(Component.translatable(shapeKey)).withStyle(ChatFormatting.GRAY));

        IntList fadeColors = explosion.fadeColors();
        if (!fadeColors.isEmpty()) {
            tooltip.add(Component.literal(" ")
                    .append(Component.translatable("item.minecraft.firework_star.fade_to"))
                    .append(" ")
                    .append(colorSquares(fadeColors))
                    .withStyle(ChatFormatting.GRAY));
        }

        if (explosion.hasTrail()) {
            tooltip.add(Component.literal(" ").append(Component.translatable("item.minecraft.firework_star.trail")).withStyle(ChatFormatting.GRAY));
        }

        if (explosion.hasTwinkle()) {
            tooltip.add(Component.literal(" ").append(Component.translatable("item.minecraft.firework_star.flicker")).withStyle(ChatFormatting.GRAY));
        }
    }

    private static void appendPotionEffectLines(ItemStack potion, List<Component> tooltip) {
        PotionContents contents = potion.getOrDefault(DataComponents.POTION_CONTENTS, PotionContents.EMPTY);

        String header = potion.is(Items.LINGERING_POTION) ? "Lingering" : "Splash";
        tooltip.add(Component.literal(" ").append(Component.literal(header)).withStyle(ChatFormatting.BLUE));

        contents.forEachEffect(effectInstance -> {
            MobEffect effect = effectInstance.getEffect().value();
            MutableComponent line = Component.literal(" ").append(Component.translatable(effect.getDescriptionId()));

            if (!effect.isInstantenous()) {
                int seconds = effectInstance.getDuration() / 20;
                line = line.append(Component.literal(String.format(" (%02d:%02d)", seconds / 60, seconds % 60)));
            }

            tooltip.add(line.withStyle(effect.getCategory().getTooltipFormatting()));
        });
    }


    private static MutableComponent colorSquaresLine(IntList colors) {
        return Component.literal(" ").append(colorSquares(colors));
    }

    private static MutableComponent colorSquares(IntList colors) {
        MutableComponent line = Component.empty();
        for (int i = 0; i < colors.size(); i++) {
            if (i > 0) {
                line = line.append(" ");
            }
            line = line.append(Component.literal("\u25A0")
                    .withStyle(Style.EMPTY.withColor(TextColor.fromRgb(colors.getInt(i)))));
        }
        return line;
    }
}
