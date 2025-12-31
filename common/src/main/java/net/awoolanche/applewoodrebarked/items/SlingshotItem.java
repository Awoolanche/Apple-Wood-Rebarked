package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.entities.SlingshotProjectileEntity;

import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

import java.util.function.Predicate;

public class SlingshotItem extends Item {

    public static final Predicate<ItemStack> IS_AMMO = stack ->
                    stack.is(Items.EGG) ||
                    stack.is(Items.SNOWBALL) ||
                    stack.is(Items.CLAY_BALL) ||
                    stack.is(Items.SLIME_BALL) ||
                    stack.is(Items.FIRE_CHARGE);

    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        boolean canSourceAmmo = !findAmmo(player).isEmpty() || player.getAbilities().instabuild;

        if (!canSourceAmmo) {
            return InteractionResultHolder.fail(itemStack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        ItemStack ammoStack = findAmmo(player);
        boolean isCreative = player.getAbilities().instabuild;

        if (ammoStack.isEmpty() && !isCreative) return;

        if (ammoStack.isEmpty() && isCreative) {
            ammoStack = new ItemStack(Items.STONE);
        }

        int useTime = this.getUseDuration(stack, user) - remainingUseTicks;
        float power = getPowerForTime(useTime);

        if (power < 0.1f) return;

        if (!level.isClientSide) {
            SlingshotProjectileEntity proj = new SlingshotProjectileEntity(level, player, ammoStack);
            proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.4f * power, 1.0f);
            level.addFreshEntity(proj);

            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));

        if (!isCreative && !ammoStack.isEmpty()) {
            ammoStack.shrink(1);
        }
    }

    private ItemStack findAmmo(Player player) {
        if (IS_AMMO.test(player.getOffhandItem())) {
            return player.getOffhandItem();
        }

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (IS_AMMO.test(invStack)) {
                return invStack;
            }
        }
        return ItemStack.EMPTY;
    }

    private float getPowerForTime(int useTime) {
        float f = (float)useTime / 15.0F;
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }
}