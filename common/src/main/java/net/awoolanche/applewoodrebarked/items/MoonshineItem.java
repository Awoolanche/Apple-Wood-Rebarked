package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.effects.ModEffects;
import net.minecraft.client.Minecraft;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.satisfy.foundation.util.LibUtil;
import net.satisfy.vinery.core.item.DrinkBlockItem;
import net.satisfy.vinery.core.registry.DataComponentRegistry;
import net.satisfy.vinery.core.registry.ObjectRegistry;
import net.satisfy.vinery.core.wine.WineYears;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * New with the Vinery 1.5.5 update.
 * I have removed the drinkBlockItem#setEffectSupplier and it now reads wine effects only from its own
 * config (wineEffects), so other applewood cant register effects for their drinks anymore.
 * This subclass applies the MobEffect itself on drinking and shows it in the tooltip.
 * Wine aging still scales duration and level via WineYears, just like Vinerys own wines. :)
 */
public class MoonshineItem extends DrinkBlockItem {
    private static final int BASE_DURATION = 1600;
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
        Level level = tooltipContext.registries() != null ? Minecraft.getInstance().level : null;
        MobEffect effect = ModEffects.FEROCITY.get();
        if (level == null) {
            tooltip.add(effect.getDisplayName().copy().withStyle(effect.getCategory().getTooltipFormatting()));
            return;
        }
        int amplifier = amplifier(stack, level);
        int seconds = duration(stack, level) / 20;
        String text = effect.getDisplayName().getString()
                + (amplifier > 0 ? " " + (amplifier + 1) : "")
                + String.format(" (%d:%02d)", seconds / 60, seconds % 60);
        tooltip.add(Component.literal(text).withStyle(effect.getCategory().getTooltipFormatting()));
    }
}
