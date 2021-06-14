package io.github.ennuil.libzoomer.api.transitions;

import io.github.ennuil.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SmoothTransitionMode implements TransitionMode {
    private Identifier transitionId = new Identifier("libzoomer:smooth_transition");
    private boolean active;
    private double fovMultiplier;
    private float smoothMultiplier;
    private float internalMultiplier;
    private float lastInternalMultiplier;

    public SmoothTransitionMode(float smoothMultiplier) {
        this.smoothMultiplier = smoothMultiplier;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
    }

    public SmoothTransitionMode() {
        this(0.5f);
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
    public double applyZoom(double fov, double divisor, float tickDelta) {
        fovMultiplier = MathHelper.lerp(tickDelta, this.lastInternalMultiplier, this.internalMultiplier);
        return fov * fovMultiplier;
    }

    @Override
    public void tick(boolean active, double divisor) {
        double zoomMultiplier = 1.0F / divisor;

        this.lastInternalMultiplier = this.internalMultiplier;
        
        this.internalMultiplier += (zoomMultiplier - internalMultiplier) * smoothMultiplier;

        if ((!active && fovMultiplier == this.internalMultiplier) || active) {
            this.active = active;
        }
    }

    @Override
    public double getInternalMultiplier() {
        return this.internalMultiplier;
    }
}
