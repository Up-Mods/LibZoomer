package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.client.util.SmoothUtil;
import net.minecraft.util.Identifier;

public class CinematicCameraMouseModifier implements MouseModifier {
    private Identifier transitionId = new Identifier("libzoomer:cinematic_camera");
    private boolean active;
    private boolean cinematicCameraEnabled;
    private final SmoothUtil cursorXZoomSmoother = new SmoothUtil();
    private final SmoothUtil cursorYZoomSmoother = new SmoothUtil();
    private double oldMultiplierX;
    private double oldMultiplierY;

    public CinematicCameraMouseModifier() {
        this.active = false;
        this.oldMultiplierX = 1.0F;
        this.oldMultiplierY = 1.0F;
    }
    
    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        if (this.cinematicCameraEnabled) {
            this.cursorXZoomSmoother.clear();
            return cursorDeltaX;
        }
        double smoother = mouseUpdateTimeDelta * cursorSensitivity;
        return this.cursorXZoomSmoother.smooth(cursorDeltaX, smoother);
    }

    @Override
    public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        if (this.cinematicCameraEnabled) {
            this.cursorYZoomSmoother.clear();
            return cursorDeltaY;
        }
        double smoother = mouseUpdateTimeDelta * cursorSensitivity;
        return this.cursorYZoomSmoother.smooth(cursorDeltaY, smoother);
    }

    @Override
    public void tick(boolean active, boolean cinematicCameraEnabled) {
        this.cinematicCameraEnabled = cinematicCameraEnabled;
        this.active = active;
        if (this.active == false) {
            this.cursorXZoomSmoother.clear();
            this.cursorYZoomSmoother.clear();
        }
    }
}