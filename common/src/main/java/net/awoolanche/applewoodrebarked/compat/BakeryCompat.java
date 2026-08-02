package net.awoolanche.applewoodrebarked.compat;

import net.minecraft.core.Holder;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Optional;

public final class BakeryCompat {
    private BakeryCompat() {}

    public static final int SUGAR_RUSH_DURATION = 400; // 20s
    private static final int SUGAR_RUSH_MAX_STACKS = 10;

    private static final String MOB_EFFECT_REGISTRY_CLASS = "net.satisfy.bakery.core.registry.MobEffectRegistry";

    private static boolean initialized = false;
    private static boolean available = false;
    private static Object sugarRushSupplier;
    private static Method holderMethod;

    private static synchronized void init() {
        if (initialized) {
            return;
        }
        initialized = true;

        try {
            Class<?> registryClass = Class.forName(MOB_EFFECT_REGISTRY_CLASS);
            Field sugarRushField = registryClass.getField("SUGAR_RUSH");
            sugarRushSupplier = sugarRushField.get(null);
            holderMethod = registryClass.getMethod("holder", sugarRushField.getType());
            available = true;
        } catch (ReflectiveOperationException | RuntimeException e) {
            available = false;
        }
    }

    @SuppressWarnings("unchecked")
    public static Optional<Holder<MobEffect>> getSugarRushEffectHolder() {
        init();
        if (!available) {
            return Optional.empty();
        }

        try {
            return Optional.of((Holder<MobEffect>) holderMethod.invoke(null, sugarRushSupplier));
        } catch (ReflectiveOperationException | RuntimeException e) {
            return Optional.empty();
        }
    }

    public static void applySugarRush(Player player) {
        if (player.level().isClientSide) {
            return;
        }

        getSugarRushEffectHolder().ifPresent(effectHolder -> {
            MobEffectInstance currentEffect = player.getEffect(effectHolder);
            int newAmplifier = currentEffect == null ? 0 : Math.min(SUGAR_RUSH_MAX_STACKS - 1, currentEffect.getAmplifier() + 1);
            player.addEffect(new MobEffectInstance(effectHolder, SUGAR_RUSH_DURATION, newAmplifier, false, true, true));
        });
    }
}