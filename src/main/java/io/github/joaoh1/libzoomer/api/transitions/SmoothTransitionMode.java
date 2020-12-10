package io.github.joaoh1.libzoomer.api.transitions;

import io.github.joaoh1.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SmoothTransitionMode implements TransitionMode {
    private Identifier transitionId = new Identifier("libzoomer:smooth_transition");
    private float smoothMultiplier;
    private float internalMultiplier;
    private float lastInternalMultiplier;

    public SmoothTransitionMode() {
        this.smoothMultiplier = 0.5f;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
    }

    public SmoothTransitionMode(float smoothMultiplier) {
        this.smoothMultiplier = smoothMultiplier;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
    }
    
    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public double applyZoom(double fov, double divisor, float tickDelta) {
        double fovMultiplier = MathHelper.lerp(tickDelta, this.lastInternalMultiplier, this.internalMultiplier);
        return fov * fovMultiplier;
    }

    @Override
    public void tick(double divisor) {
        double zoomMultiplier = 1.0F / divisor;

        this.lastInternalMultiplier = this.internalMultiplier;
        
        this.internalMultiplier += (zoomMultiplier - internalMultiplier) * smoothMultiplier;
    }

    @Override
    public double getInternalMultiplier() {
        return this.internalMultiplier;
    }
}
