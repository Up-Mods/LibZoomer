package io.github.ennuil.libzoomer.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.hud.InGameHud;

@Mixin(InGameHud.class)
public class InGameHudMixin {
	@Inject(
		method = "render(Lnet/minecraft/client/gui/GuiGraphics;F)V",
		at = @At(
			value = "INVOKE",
			target = "net/minecraft/client/MinecraftClient.getLastFrameDuration()F"
		)
	)
	private void injectZoomOverlay(GuiGraphics graphics, float tickDelta, CallbackInfo ci, @Share("cancelOverlay") LocalBooleanRef cancelOverlay) {
		cancelOverlay.set(false);
		for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
			var overlay = instance.getZoomOverlay();
			if (overlay != null) {
				overlay.tickBeforeRender();
				if (overlay.getActive()) {
					cancelOverlay.set(overlay.cancelOverlayRendering());
					overlay.renderOverlay(graphics);
				}
			}
		}
	}

	// Cancel the cancellable overlays
	@ModifyExpressionValue(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/Perspective;isFirstPerson()Z"))
	private boolean cancelOverlay(boolean original, @Share("cancelOverlay") LocalBooleanRef cancelOverlay) {
		return original && !cancelOverlay.get();
	}
}
