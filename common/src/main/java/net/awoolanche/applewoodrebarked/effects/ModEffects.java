package net.awoolanche.applewoodrebarked.effects;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;

import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {
    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(AppleWoodRebarked.MOD_ID, Registries.MOB_EFFECT);

    public static final RegistrySupplier<MobEffect> FEROCITY = EFFECTS.register(
            "ferocity_effect",
            () -> new FerocityEffect(MobEffectCategory.BENEFICIAL, 0xB30000)
    );

    private static Holder<MobEffect> ferocityHolder;

    public static Holder<MobEffect> ferocityHolder() {
        if (ferocityHolder == null) {
            ferocityHolder = BuiltInRegistries.MOB_EFFECT.wrapAsHolder(FEROCITY.get());
        }
        return ferocityHolder;
    }

    public static int critFoodCost(int amplifier) {
        return Math.max(0, 4 - amplifier);
    }

    public static void init() {
        EFFECTS.register();
    }
}