package io.github.ennuil.libzoomer.mixin;

import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;

@Mixin(Mouse.class)
public abstract class MouseMixin {
    @Shadow
    @Final
    private MinecraftClient client;

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
            target = "net.minecraft.client.option/GameOptions.cinematicCamera:Z"
        ),
        method = "updateLookDirection()V",
        locals = LocalCapture.CAPTURE_FAILHARD,
        cancellable = true
    )
    public void getSensitivity(CallbackInfo ci, double e, double f, double g, double h) {
        this.cursorSensitivity = h;
    }

    @Inject(
        at = @At(
            value = "FIELD",
            target = "net/minecraft/client/option/GameOptions.invertYMouse:Z",
            opcode = Opcodes.GETFIELD
        ),
        method = "updateLookDirection()V",
        locals = LocalCapture.CAPTURE_FAILHARD
    )
    public void applyZoomChanges(CallbackInfo ci, double e, double k, double l, int m) {
        this.modifyMouse = false;
        if (ZoomRegistry.shouldIterateZoom() || ZoomRegistry.shouldIterateModifiers()) {
            for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
                if (instance.getMouseModifier() != null) {
                    boolean zoom = instance.getZoom();
                    if (zoom || instance.isModifierActive()) {
                        instance.getMouseModifier().tick(zoom);
                        double zoomDivisor = zoom ? instance.getZoomDivisor() : 1.0F;
                        double transitionDivisor = instance.getTransitionMode().getInternalMultiplier();
                        k = instance.getMouseModifier().applyXModifier(k, cursorSensitivity, e, zoomDivisor, transitionDivisor);
                        l = instance.getMouseModifier().applyYModifier(l, cursorSensitivity, e, zoomDivisor, transitionDivisor);
                        this.modifyMouse = true;
                    }
                }
            }
        }
        this.finalCursorDeltaX = k;
        this.finalCursorDeltaY = l;
    }

    @ModifyVariable(
        at = @At(
            value = "FIELD",
            target = "net/minecraft/client/option/GameOptions.invertYMouse:Z",
            opcode = Opcodes.GETFIELD
        ),
        method = "updateLookDirection()V",
        ordinal = 1
    )
    private double modifyFinalCursorDeltaX(double k) {
        if (!this.modifyMouse) return k;
        return finalCursorDeltaX;
    }

    @ModifyVariable(
        at = @At(
            value = "FIELD",
            target = "net/minecraft/client/option/GameOptions.invertYMouse:Z",
            opcode = Opcodes.GETFIELD
        ),
        method = "updateLookDirection()V",
        ordinal = 2
    )
    private double modifyFinalCursorDeltaY(double l) {
        if (!this.modifyMouse) return l;
        return finalCursorDeltaY;
    }
}
