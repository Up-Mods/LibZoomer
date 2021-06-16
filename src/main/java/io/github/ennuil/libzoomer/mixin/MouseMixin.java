package io.github.ennuil.libzoomer.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
public class MouseMixin {
    @Final
    @Shadow
    private MinecraftClient client;

    @Shadow
    private double cursorDeltaX;
    
    @Shadow
    private double cursorDeltaY;

    @Unique
    private double cursorSensitivity;

    @Unique
    private boolean modifyMouse;

    @Unique
    private double finalCursorDeltaX;

    @Unique
    private double finalCursorDeltaY;

    // Mixin really hates me doing the way saner way of doing this, so, we went with the cursed one
    @Inject(
        at = @At(
            value = "FIELD",
            target = "net.minecraft.client.option/GameOptions.smoothCameraEnabled:Z"
        ),
        method = "updateMouse()V",
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    public void getSensitivity(CallbackInfo ci, double e, double f, double g, double h) {
        this.cursorSensitivity = h;
    }

    @Inject(
        at = @At(
            value = "FIELD",
            target = "net/minecraft/client/Mouse.cursorDeltaX:D",
            ordinal = 4
        ),
        method = "updateMouse()V",
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    public void applyZoomChanges(CallbackInfo ci, double e, double o, double p) {
        this.modifyMouse = false;
        for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
            boolean zoom = instance.getZoom();
            if (zoom || instance.isModifierActive()) {
                instance.getMouseModifier().tick(zoom);
                double zoomDivisor = zoom ? instance.getZoomDivisor() : 1.0F;
                double transitionDivisor = instance.getTransitionMode().getInternalMultiplier();
                o = instance.getMouseModifier().applyXModifier(o, cursorSensitivity, e, zoomDivisor, transitionDivisor);
                p = instance.getMouseModifier().applyYModifier(p, cursorSensitivity, e, zoomDivisor, transitionDivisor);
                this.modifyMouse = true;
            }
        }
        this.finalCursorDeltaX = o;
        this.finalCursorDeltaY = p;
    }

    @ModifyArgs(
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/client/tutorial/TutorialManager.onUpdateMouse(DD)V"
        ),
        method = "updateMouse()V"
    )
    private void modifyTutorialUpdate(Args args) {
        if (this.modifyMouse == false) return;
        args.set(0, finalCursorDeltaX);
        args.set(1, finalCursorDeltaY);
    }

    @ModifyArgs(
        at = @At(
            value = "INVOKE",
            target = "net/minecraft/client/network/ClientPlayerEntity.changeLookDirection(DD)V"
        ),
        method = "updateMouse()V"
    )
    private void modifyLookDirection(Args args) {
        if (this.modifyMouse == false) return;
        args.set(0, finalCursorDeltaX);
        args.set(1, finalCursorDeltaY * (this.client.options.invertYMouse ? -1 : 1));
    }
}
