package io.github.ennuil.libzoomer.mixin;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.ennuil.libzoomer.impl.SpyglassHelper;
import net.fabricmc.fabric.api.tag.client.v1.ClientTags;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.PlayerItemInHandLayer;
import net.minecraft.world.entity.HumanoidArm;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerItemInHandLayer.class)
public abstract class PlayerItemInHandMixin {
	@Inject(method = "renderArmWithItem", at = @At("HEAD"), cancellable = true)
	private void renderCustomSpyglassesAsSpyglass(LivingEntity entity, ItemStack stack, ItemDisplayContext displayContext, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight, CallbackInfo ci) {
		if (ClientTags.isInWithLocalFallback(SpyglassHelper.SPYGLASSES, stack.getItem()) && entity.getUseItem() == stack && entity.swingTime == 0) {
			this.renderArmWithSpyglass(entity, stack, arm, poseStack, buffer, packedLight);
			ci.cancel();
		}
	}

	@Shadow
	protected abstract void renderArmWithSpyglass(LivingEntity livingEntity, ItemStack itemStack, HumanoidArm arm, PoseStack poseStack, MultiBufferSource buffer, int packedLight);
}
