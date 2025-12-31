package net.awoolanche.applewoodrebarked.mixin;

import net.awoolanche.applewoodrebarked.items.ModItems;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.ItemInHandRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ItemInHandRenderer.class)
public abstract class ItemInHandRendererMixin {

    @Shadow
    public abstract void renderItem(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, boolean leftHand, PoseStack poseStack, MultiBufferSource buffer, int combinedLight);

    @Inject(
            method = "renderArmWithItem",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/item/ItemStack;getUseAnimation()Lnet/minecraft/world/item/UseAnim;"
            ),
            cancellable = true
    )
    private void applewood$slingshotTransform(
            AbstractClientPlayer player,
            float tickDelta,
            float pitch,
            InteractionHand hand,
            float swingProgress,
            ItemStack stack,
            float equipProgress,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int combinedLight,
            CallbackInfo ci
    ) {
        if (!stack.is(ModItems.SLINGSHOT.get())) return;
        if (!player.isUsingItem() || player.getUsedItemHand() != hand) return;

        boolean isRightHand = hand == InteractionHand.MAIN_HAND
                ? player.getMainArm() == HumanoidArm.RIGHT
                : player.getMainArm() == HumanoidArm.LEFT;

        HumanoidArm arm = isRightHand ? HumanoidArm.RIGHT : HumanoidArm.LEFT;
        int sign = isRightHand ? 1 : -1;

        poseStack.pushPose();

        float side = arm == HumanoidArm.RIGHT ? 1.0F : -1.0F;
        poseStack.translate(side * 0.56F, -0.52F + equipProgress * -0.6F, -0.72F);

        // Translation
        poseStack.translate(
                sign * -0.41F,// X: Centers the model
                0.10F,           // Y: Height
                -0.10F           // Z: Forward placement
        );

        // Rotation
        poseStack.mulPose(Axis.XP.rotationDegrees(-5.0F));
        poseStack.mulPose(Axis.YP.rotationDegrees(sign * -92.5F));
        poseStack.mulPose(Axis.ZP.rotationDegrees(sign * -2.0F));

        // Pull
        float used = (float)stack.getUseDuration(player) - ((float)player.getUseItemRemainingTicks() - tickDelta + 1.0F);
        float pull = used / 20.0F;
        pull = (pull * pull + pull * 2.0F) / 3.0F;
        pull = Math.min(pull, 1.0F);

        // Wobble
        if (pull > 0.1F) {
            float wobble = Mth.sin((used - 0.1F) * 1.3F);
            float amt = wobble * (pull - 0.1F);
            poseStack.translate(0.0F, amt * 0.002F, 0.0F);
        }

        // Forward pull
        poseStack.translate(pull * 0.05F, 0.0F, 0.0F);

        this.renderItem(
                player,
                stack,
                isRightHand ? ItemDisplayContext.FIRST_PERSON_RIGHT_HAND : ItemDisplayContext.FIRST_PERSON_LEFT_HAND,
                !isRightHand,
                poseStack,
                buffer,
                combinedLight
        );

        poseStack.popPose();
        ci.cancel();
    }
}