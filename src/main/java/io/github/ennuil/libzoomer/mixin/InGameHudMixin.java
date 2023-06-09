package io.github.ennuil.libzoomer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomOverlay;
import io.github.ennuil.libzoomer.api.ZoomRegistry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Unique
	private boolean shouldCancelOverlay = false;

	@Inject(
		method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/client/MinecraftClient.getLastFrameDuration()F"
		)
	)
	public void injectZoomOverlay(GuiGraphics graphics, float tickDelta, CallbackInfo ci) {
		this.shouldCancelOverlay = false;
		for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
			ZoomOverlay overlay = instance.getZoomOverlay();
			if (overlay != null) {
				overlay.tickBeforeRender();
				if (overlay.getActive()) {
					this.shouldCancelOverlay = overlay.cancelOverlayRendering();
					overlay.renderOverlay(graphics);
				}
			}
		}
	}

	// Yes, there is a renderOverlay for being frozen...
	@Inject(
		method = {"renderSpyglassOverlay", "renderOverlay"},
		at = @At("HEAD"),
		cancellable = true
	)
	public void cancelOverlay(CallbackInfo ci) {
		if (this.shouldCancelOverlay) ci.cancel();
	}

	// ...which is why we set cancelOverlayRender to false before that!
	@Inject(
		method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/client/network/ClientPlayerEntity.getFrozenTicks()I"
		)
	)
	public void disableOverlayCancelling(GuiGraphics graphics, float tickDelta, CallbackInfo ci) {
		if (this.shouldCancelOverlay) {
			this.shouldCancelOverlay = false;
		}
	}
}
