package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.effects.ModEffects;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Player.class)
public abstract class PlayerCritMixin {

    @ModifyVariable(method = "attack", at = @At(value = "STORE"), ordinal = 2)
    private boolean applewoodrebarked$forceCrit(boolean bl3) {
        Player self = (Player) (Object) this;

        if (!dev.architectury.platform.Platform.isFabric()) {
            return bl3;
        }

        MobEffectInstance instance = self.getEffect(ModEffects.ferocityHolder());
        if (instance == null) {
            return bl3;
        }

        int cost = ModEffects.critFoodCost(instance.getAmplifier());
        FoodData food = self.getFoodData();
        if (cost > 0 && food.getFoodLevel() < cost && !self.getAbilities().instabuild) {
            return bl3;
        }

        if (!self.getAbilities().instabuild && cost > 0) {
            food.setFoodLevel(Math.max(0, food.getFoodLevel() - cost));
        }

        return true;
    }
}