package net.awoolanche.applewoodrebarked.neoforge;

import net.awoolanche.applewoodrebarked.AppleWoodRebarked;
import net.awoolanche.applewoodrebarked.effects.ModEffects;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.player.CriticalHitEvent;

@EventBusSubscriber(modid = AppleWoodRebarked.MOD_ID)
public class ModCombatEvents {

    @SubscribeEvent
    public static void onCriticalHit(CriticalHitEvent event) {
        Player player = event.getEntity();
        MobEffectInstance instance = player.getEffect(ModEffects.ferocityHolder());
        if (instance == null) {
            return;
        }

        int cost = ModEffects.critFoodCost(instance.getAmplifier());
        FoodData food = player.getFoodData();
        if (cost > 0 && food.getFoodLevel() < cost && !player.getAbilities().instabuild) {
            return;
        }

        event.setCriticalHit(true);
        event.setDamageMultiplier(Math.max(event.getDamageMultiplier(), 1.5F));

        if (!player.getAbilities().instabuild && cost > 0) {
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - cost));
        }
    }


    private static Holder<MobEffect> ferocityHolder() {
        return BuiltInRegistries.MOB_EFFECT.wrapAsHolder(ModEffects.FEROCITY.get());
    }
}