package io.github.ennuil.libzoomer.mixin;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.GameRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
	@Inject(method = "tick()V", at = @At("HEAD"))
	private void tickInstances(CallbackInfo info) {
		boolean iterateZoom = false;
		boolean iterateTransitions = false;
		boolean iterateModifiers = false;
		boolean iterateOverlays = false;

		for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
			boolean zooming = instance.isZooming();
			if (zooming || (instance.isTransitionActive() || instance.isModifierActive() || instance.isOverlayActive())) {
				double divisor = zooming ? instance.getZoomDivisor() : 1.0;
				instance.getTransitionMode().tick(zooming, divisor);
				if (instance.getMouseModifier() != null) {
					instance.getMouseModifier().tick(zooming);
				}
				if (instance.getZoomOverlay() != null) {
					instance.getZoomOverlay().tick(zooming, divisor, instance.getTransitionMode().getInternalMultiplier());
				}
			}

			iterateZoom = iterateZoom || zooming;
			iterateTransitions = iterateTransitions || instance.isTransitionActive();
			iterateModifiers = iterateModifiers || instance.isModifierActive();
			iterateOverlays = iterateOverlays || instance.isOverlayActive();
		}

		ZoomRegistry.setIterateZoom(iterateZoom);
		ZoomRegistry.setIterateTransitions(iterateTransitions);
		ZoomRegistry.setIterateModifiers(iterateModifiers);
		ZoomRegistry.setIterateOverlays(iterateOverlays);
	}

	@Inject(method = "getFov", at = @At("RETURN"), cancellable = true)
	private void getZoomedFov(Camera activeRenderInfo, float partialTicks, boolean useFOVSetting, CallbackInfoReturnable<Double> cir) {
		double fov = cir.getReturnValue();
		double zoomedFov = fov;

		if (ZoomRegistry.shouldIterateTransitions()) {
			for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
				if (instance.isTransitionActive()) {
					zoomedFov = instance.getTransitionMode().applyZoom(zoomedFov, partialTicks);
				}
			}
		}

		if (fov != zoomedFov) {
			cir.setReturnValue(zoomedFov);
		}
	}
}
