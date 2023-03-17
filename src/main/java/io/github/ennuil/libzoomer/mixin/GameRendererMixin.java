package io.github.ennuil.libzoomer.mixin;

import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;

@Mixin(GameRenderer.class)
public class GameRendererMixin {

	@Inject(method = "tick()V", at = @At("HEAD"))
	private void tickInstances(CallbackInfo info) {
		boolean iterateZoom = false;
		boolean iterateTransitions = false;
		boolean iterateModifiers = false;
		boolean iterateOverlays = false;

		for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
			boolean zoom = instance.getZoom();
			if (zoom || (instance.isTransitionActive() || instance.isOverlayActive())) {
				double divisor = zoom ? instance.getZoomDivisor() : 1.0;
				if (instance.getZoomOverlay() != null) {
					instance.getZoomOverlay().tick(zoom, divisor, instance.getTransitionMode().getInternalMultiplier());
				}
				instance.getTransitionMode().tick(zoom, divisor);
			}

			iterateZoom = iterateZoom || zoom;
			iterateTransitions = iterateTransitions || instance.isTransitionActive();
			iterateModifiers = iterateModifiers || instance.isModifierActive();
			iterateOverlays = iterateOverlays || instance.isOverlayActive();
		}

		ZoomRegistry.setIterateZoom(iterateZoom);
		ZoomRegistry.setIterateTransitions(iterateTransitions);
		ZoomRegistry.setIterateModifiers(iterateModifiers);
		ZoomRegistry.setIterateOverlays(iterateOverlays);
	}

	@Inject(method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D", at = @At("RETURN"), cancellable = true)
	private void getZoomedFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
		double fov = cir.getReturnValue();
		double zoomedFov = fov;

		if (ZoomRegistry.shouldIterateTransitions()) {
			for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
				if (instance.isTransitionActive()) {
					zoomedFov = instance.getTransitionMode().applyZoom(zoomedFov, tickDelta);
				}
			}
		}

		if (fov != zoomedFov) {
			cir.setReturnValue(zoomedFov);
		}
	}
}
