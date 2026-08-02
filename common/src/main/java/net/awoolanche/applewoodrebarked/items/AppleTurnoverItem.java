package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.compat.BakeryCompat;
import net.awoolanche.applewoodrebarked.util.ModCompat;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class AppleTurnoverItem extends Item {
    private static final int VANILLA_FOOD_USE_DURATION = 32; // 32 ticks
    private static final int FAST_EAT_USE_DURATION = Math.round(VANILLA_FOOD_USE_DURATION * (2f / 3f)); // 21 ticks

    public AppleTurnoverItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return FAST_EAT_USE_DURATION;
    }

    @Override
    public @NotNull ItemStack finishUsingItem(@NotNull ItemStack stack, @NotNull Level level, @NotNull LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (ModCompat.BAKERY && !level.isClientSide && entity instanceof Player player) {
            BakeryCompat.applySugarRush(player);
        }
        return result;
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        if (ModCompat.BAKERY) {
            BakeryCompat.getSugarRushEffectHolder().ifPresent(effectHolder -> {
                MobEffect effect = effectHolder.value();
                MutableComponent line = Component.translatable(effect.getDescriptionId());

                int seconds = BakeryCompat.SUGAR_RUSH_DURATION / 20;
                line = line.append(Component.literal(String.format(" (%d:%02d)", seconds / 60, seconds % 60)));

                tooltip.add(line.withStyle(effect.getCategory().getTooltipFormatting()));
            });
        }

        super.appendHoverText(stack, context, tooltip, flag);
    }
}