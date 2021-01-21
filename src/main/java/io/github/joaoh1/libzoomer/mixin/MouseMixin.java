package io.github.joaoh1.libzoomer.mixin;

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

import io.github.joaoh1.libzoomer.api.ZoomRegistry;
import io.github.joaoh1.libzoomer.api.ZoomInstance;
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
    private boolean modifyMouse;

    @Unique
    private double finalO;

    @Unique
    private double finalP;

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
    public void applyZoomChanges2(CallbackInfo ci, double e, double o, double p) {
        this.modifyMouse = false;
        for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
            instance.getMouseModifier().tick(instance.getZoom(), this.client.options.smoothCameraEnabled);
            if (instance.isModifierActive()) {
                double zoomDivisor = instance.getZoomDivisor();
                double transitionDivisor = instance.getTransitionMode().getInternalMultiplier();
                o = instance.getMouseModifier().applyXModifier(this.cursorDeltaX, o, e, zoomDivisor, transitionDivisor);
                p = instance.getMouseModifier().applyYModifier(this.cursorDeltaY, p, e, zoomDivisor, transitionDivisor);
                this.modifyMouse = true;
            }
        }
        this.finalO = o;
        this.finalP = p;
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
        args.set(0, finalO);
        args.set(1, finalP);
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
        args.set(0, finalO);
        args.set(1, finalP * (this.client.options.invertYMouse ? -1 : 1));
    }
}
