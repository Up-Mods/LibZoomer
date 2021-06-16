package io.github.ennuil.libzoomer.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import io.github.ennuil.libzoomer.api.ZoomInstance;
import io.github.ennuil.libzoomer.api.ZoomRegistry;

@Mixin(GameRenderer.class)
public class GameRendererMixin {
    @Final
    @Shadow
    private MinecraftClient client;
    
    @Unique
    private double formerFov;

    @Inject(
        at = @At("HEAD"),
        method = "tick()V"
    )
    private void tickInstances(CallbackInfo info) {
        for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
            boolean zoom = instance.getZoom();
            if (zoom || (instance.isTransitionActive() || instance.isOverlayActive())) {
                double divisor = zoom ? instance.getZoomDivisor() : 1.0F;
                instance.getZoomOverlay().tick(zoom, divisor, instance.getTransitionMode().getInternalMultiplier());
                instance.getTransitionMode().tick(zoom, divisor);
            }
        }
    }

    @Inject(
        at = @At("RETURN"),
        method = "getFov(Lnet/minecraft/client/render/Camera;FZ)D",
        cancellable = true
    )
    private double getZoomedFov(Camera camera, float tickDelta, boolean changingFov, CallbackInfoReturnable<Double> cir) {
        double fov = cir.getReturnValue();
        double zoomedFov = fov;
        
        for (ZoomInstance instance : ZoomRegistry.getZoomInstances()) {
            if (instance.isTransitionActive()) {
                zoomedFov = instance.getTransitionMode().applyZoom(zoomedFov, tickDelta);   
            }
        }

        if (fov != zoomedFov) {
            cir.setReturnValue(zoomedFov);
        }

        if (zoomedFov > formerFov) {
            if (changingFov) this.client.worldRenderer.scheduleTerrainUpdate();
        }

        this.formerFov = zoomedFov;

        return fov;
    }
}
