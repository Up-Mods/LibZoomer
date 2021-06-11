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

    public CinematicCameraMouseModifier() {
        this.active = false;
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
    public double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        if (this.cinematicCameraEnabled) {
            this.cursorXZoomSmoother.clear();
            return o;
        }
        double multiplier = mouseUpdateDelta;
        if (cursorXDelta != 0) {
            multiplier *= (o / cursorXDelta);
        }
        return this.cursorXZoomSmoother.smooth(o, multiplier);
    }

    @Override
    public double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        if (this.cinematicCameraEnabled) {
            this.cursorYZoomSmoother.clear();
            return p;
        }
        double multiplier = mouseUpdateDelta;
        if (cursorYDelta != 0) {
            multiplier *= (p / cursorYDelta);
        }
        return this.cursorYZoomSmoother.smooth(p, multiplier);
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