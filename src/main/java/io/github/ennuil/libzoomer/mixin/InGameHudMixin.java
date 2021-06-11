package io.github.ennuil.libzoomer.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import io.github.ennuil.libzoomer.impl.OverlayCancellingHelper;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Unique
    private boolean cancelOverlay;

    @Inject(
        at = @At(
            value = "INVOKE_ASSIGN",
            target = "net/minecraft/client/option/Perspective.isFirstPerson()Z"
        ),
        method = "render"
    )
    public void canCancelOverlay(MatrixStack matrices, float tickDelta, CallbackInfo ci) {
        this.cancelOverlay = OverlayCancellingHelper.getCancelOverlayRender();
    }

    @Inject(
        at = @At("HEAD"),
        method = {
            "renderSpyglassOverlay",
            "renderOverlay"
        },
        cancellable = true
    )
    public void cancelOverlay(CallbackInfo ci) {
        if (this.cancelOverlay) ci.cancel();
    }
}
