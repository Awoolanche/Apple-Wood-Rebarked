package net.awoolanche.applewoodrebarked.items;

import net.awoolanche.applewoodrebarked.enchantments.ModEnchantments;
import net.awoolanche.applewoodrebarked.entities.SlingshotProjectileEntity;
import net.awoolanche.applewoodrebarked.platform.PlatformHelper;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.TagKey;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.Level;
import java.util.List;
import java.util.function.Predicate;

import net.minecraft.network.chat.Component;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class SlingshotItem extends Item {

    public static final Predicate<ItemStack> IS_AMMO = stack ->
            stack.is(Items.EGG) ||
                    stack.is(Items.SNOWBALL) ||
                    stack.is(Items.CLAY_BALL) ||
                    stack.is(Items.FLINT) ||
                    stack.is(Items.SLIME_BALL) ||
                    stack.is(Items.FIRE_CHARGE) ||
                    stack.is(Items.CHORUS_FRUIT) ||
                    stack.is(Items.FIREWORK_STAR) ||
                    stack.is(Items.GOLD_NUGGET) ||
                    stack.is(Items.IRON_NUGGET);

    public static final Predicate<ItemStack> IS_POTION_AMMO = stack ->
            stack.is(Items.SPLASH_POTION) ||
                    stack.is(Items.LINGERING_POTION);

    public static final TagKey<Block> MULTIBLOCK_BLACKLIST =
            TagKey.create(Registries.BLOCK, ResourceLocation.fromNamespaceAndPath("applewoodrebarked", "heavy_lifting_blacklist"));

    public static final Predicate<ItemStack> IS_BLOCK_AMMO = stack ->
            !stack.isEmpty() && stack.getItem() instanceof BlockItem blockItem && !isMultiblock(blockItem);

    public static boolean isMultiblock(BlockItem blockItem) {
        BlockState defaultState = blockItem.getBlock().defaultBlockState();

        if (defaultState.is(MULTIBLOCK_BLACKLIST)) return true;
        if (defaultState.hasProperty(BlockStateProperties.DOUBLE_BLOCK_HALF)) return true;
        if (defaultState.hasProperty(BlockStateProperties.BED_PART)) return true;

        return false;
    }

    private static final float BASE_CHARGE_TICKS = 15.0F;

    public SlingshotItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getEnchantmentValue() {
        return 1;
    }

    @Override
    public int getUseDuration(ItemStack stack, LivingEntity user) {
        return 72000;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack itemStack = player.getItemInHand(hand);

        ItemStack ammoStack = findAmmo(player, itemStack, level);
        boolean canSourceAmmo = !ammoStack.isEmpty() || player.getAbilities().instabuild;

        if (!canSourceAmmo) {
            return InteractionResultHolder.fail(itemStack);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(itemStack);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        PlatformHelper.appendTooltip(stack, tooltip);
        super.appendHoverText(stack, context, tooltip, flag);
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity user, int remainingUseTicks) {
        if (!(user instanceof Player player)) return;

        ItemStack ammoStack = findAmmo(player, stack, level);
        boolean isCreative = player.getAbilities().instabuild;

        if (ammoStack.isEmpty()/* && !isCreative*/) return;

/*        if (ammoStack.isEmpty() && isCreative) {
            ammoStack = new ItemStack(Items.STONE);
        }*/

        int useTime = this.getUseDuration(stack, user) - remainingUseTicks;
        float power = getPowerForTime(useTime, getChargeTimeFactor(stack, level));

        if (power < 0.1f) return;

        if (!level.isClientSide) {
            SlingshotProjectileEntity proj = new SlingshotProjectileEntity(level, player, ammoStack);
            proj.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0f, 1.4f * power * getRangeMultiplier(stack, level), 1.0f);
            level.addFreshEntity(proj);

            stack.hurtAndBreak(1, player, LivingEntity.getSlotForHand(player.getUsedItemHand()));
        }

        level.playSound(null, player.getX(), player.getY(), player.getZ(),
                SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));

        if (!isCreative && !ammoStack.isEmpty()) {
            ammoStack.shrink(1);
        }
    }

    public static ItemStack findAmmo(Player player, ItemStack slingshotStack, Level level) {
        Predicate<ItemStack> ammoPredicate = hasAlchemistry(slingshotStack, level) ? IS_AMMO.or(IS_POTION_AMMO) : IS_AMMO;

        ItemStack offhand = player.getOffhandItem();
        if (ammoPredicate.test(offhand)) {
            return offhand;
        }

        if (hasHeavyLifting(slingshotStack, level) && IS_BLOCK_AMMO.test(offhand)) {
            return offhand;
        }

        for (int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack invStack = player.getInventory().getItem(i);
            if (ammoPredicate.test(invStack)) {
                return invStack;
            }
        }
        return ItemStack.EMPTY;
    }

    public static boolean hasAlchemistry(ItemStack slingshotStack, Level level) {
        Holder<Enchantment> alchemistry = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(ModEnchantments.ALCHEMISTRY);

        return EnchantmentHelper.getItemEnchantmentLevel(alchemistry, slingshotStack) > 0;
    }

    public static boolean hasHeavyLifting(ItemStack slingshotStack, Level level) {
        Holder<Enchantment> heavyLifting = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(ModEnchantments.HEAVY_LIFTING);

        return EnchantmentHelper.getItemEnchantmentLevel(heavyLifting, slingshotStack) > 0;
    }

    public static float getRangeMultiplier(ItemStack slingshotStack, Level level) {
        int powerLevel = getEnchantLevel(slingshotStack, level, Enchantments.POWER);
        return Math.min(1.0F + 0.1F * powerLevel, 1.5F);
    }

    public static float getChargeTimeFactor(ItemStack slingshotStack, Level level) {
        int quickChargeLevel = getEnchantLevel(slingshotStack, level, Enchantments.QUICK_CHARGE);
        return Math.max(1.0F - 0.1F * quickChargeLevel, 0.1F);
    }

    private static int getEnchantLevel(ItemStack stack, Level level, ResourceKey<Enchantment> key) {
        Holder<Enchantment> enchantment = level.registryAccess()
                .lookupOrThrow(Registries.ENCHANTMENT)
                .getOrThrow(key);

        return EnchantmentHelper.getItemEnchantmentLevel(enchantment, stack);
    }

    private float getPowerForTime(int useTime, float chargeTimeFactor) {
        float f = (float)useTime / (BASE_CHARGE_TICKS * chargeTimeFactor);
        f = (f * f + f * 2.0F) / 3.0F;
        if (f > 1.0F) f = 1.0F;
        return f;
    }

}