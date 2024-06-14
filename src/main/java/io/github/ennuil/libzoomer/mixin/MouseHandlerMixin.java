package io.github.ennuil.libzoomer.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalDoubleRef;
import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public abstract class MouseHandlerMixin {
	@Inject(
		method = "turnPlayer",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/Options;invertYMouse()Lnet/minecraft/client/OptionInstance;"
		)
	)
	public void applyZoomChanges(double movementTime, CallbackInfo ci, @Local(ordinal = 1) LocalDoubleRef i, @Local(ordinal = 2) LocalDoubleRef j, @Local(ordinal = 5) double f) {
		if (ZoomRegistry.shouldIterateModifiers()) {
			for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
				if (instance.isModifierActive()) {
					double zoomDivisor = instance.getZoom() ? instance.getZoomDivisor() : 1.0;
					double transitionDivisor = instance.getTransitionMode().getInternalMultiplier();
					i.set(instance.getMouseModifier().applyXModifier(i.get(), f, movementTime, zoomDivisor, transitionDivisor));
					j.set(instance.getMouseModifier().applyYModifier(j.get(), f, movementTime, zoomDivisor, transitionDivisor));
				}
			}
		}
	}
}
