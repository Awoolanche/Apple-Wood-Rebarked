package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.effects.ModEffects;
import net.satisfy.foundation.util.LibUtil;
import net.satisfy.vinery.core.item.DrinkBlockItem;
import net.satisfy.vinery.core.component.WineYearComponent;
import net.satisfy.vinery.core.registry.DataComponentRegistry;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.wine.WineYears;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextColor;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class MoonshineItem extends DrinkBlockItem {
    private static final int BASE_DURATION = 1800;
    private static final int BASE_AMPLIFIER = 0;

    public MoonshineItem(Block block, Properties settings) {
        super(block, settings, true, BottleSize.BIG);
    }

    private int duration(ItemStack stack, Level level) {
        return WineYears.isAgingEnabled() ? WineYears.getEffectDuration(stack, level, BASE_DURATION) : BASE_DURATION;
    }

    private int amplifier(ItemStack stack, Level level) {
        return BASE_AMPLIFIER + (WineYears.isAgingEnabled() ? WineYears.getEffectLevel(stack, level) : 0);
    }

    @Override
    public @NotNull ItemStack finishUsingItem(ItemStack itemStack, Level level, LivingEntity livingEntity) {
        if (!level.isClientSide) {
            if (itemStack.get(DataComponentRegistry.WINE_YEAR.get()) == null) {
                WineYears.setWineYear(itemStack, level);
            }
            livingEntity.addEffect(new MobEffectInstance(BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.FEROCITY.get()), duration(itemStack, level), amplifier(itemStack, level)));
        }
        itemStack.shrink(1);
        return LibUtil.convertStackAfterFinishUsing(livingEntity, itemStack, ObjectRegistry.WINE_BOTTLE.get(), this);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext tooltipContext, List<Component> tooltip, TooltipFlag tooltipFlag) {
        Level level = tooltipContext.registries() != null ? getLevel() : null;
        MobEffect effect = ModEffects.FEROCITY.get();
        if (level == null) {
            tooltip.add(effect.getDisplayName().copy().withStyle(effect.getCategory().getTooltipFormatting()));
            return;
        }

        int amplifier = amplifier(stack, level);
        String amplifierRoman = amplifier > 0 ? " " + toRoman(amplifier + 1) : "";
        String text = effect.getDisplayName().getString() + amplifierRoman + " (" + formatDuration(duration(stack, level)) + ")";
        tooltip.add(Component.literal(text).withStyle(effect.getCategory().getTooltipFormatting()));

        WineYearComponent year = stack.get(DataComponentRegistry.WINE_YEAR.get());
        if (WineYears.isAgingEnabled() && year != null) {
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tooltip.vinery.age", Math.max(0, WineYears.getWineAgeYears(stack, level))).withStyle(ChatFormatting.WHITE));
            tooltip.add(Component.empty());

            if (WineYears.getEffectLevel(stack, level) >= year.maxLevel()) {
                tooltip.add(Component.translatable("hud.vinery.wine.fully_aged").withStyle(ChatFormatting.GOLD));
            } else {
                int cycle = Math.max(1, year.daysPerYear() * Math.max(1, year.yearsPerEffectLevel()));
                int daysToNextUpgrade = cycle - (WineYears.getWineAgeDays(stack, level) % cycle);
                tooltip.add(Component.translatable("tooltip.vinery.next_upgrade", daysToNextUpgrade)
                        .withStyle(style -> style.withColor(TextColor.fromRgb(0x93c47d))));
            }
        }
    }

    @Environment(EnvType.CLIENT)
    private Level getLevel() {
        return Minecraft.getInstance().level;
    }

    private static String formatDuration(int ticks) {
        int totalSeconds = Math.max(0, ticks) / 20;
        return String.format("%d:%02d", totalSeconds / 60, totalSeconds % 60);
    }

    private static String toRoman(int number) {
        return switch (number) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> String.valueOf(number);
        };
    }
}