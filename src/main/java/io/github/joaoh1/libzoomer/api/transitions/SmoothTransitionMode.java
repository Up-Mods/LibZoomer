package io.github.joaoh1.libzoomer.api.transitions;

import io.github.joaoh1.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

public class SmoothTransitionMode implements TransitionMode {
    private Identifier transitionId;
    private float smoothMultiplier;
    private float internalMultiplier;
    private float lastInternalMultiplier;
    private double internalDivisor;

    public SmoothTransitionMode() {
        this.transitionId = new Identifier("libzoomer:smooth_transition");
        this.smoothMultiplier = 0.75f;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
        this.internalDivisor = 1.0;
    }

    public SmoothTransitionMode(float smoothMultiplier) {
        this.transitionId = new Identifier("libzoomer:smooth_transition");
        this.smoothMultiplier = smoothMultiplier;
        this.internalMultiplier = 1.0F;
        this.lastInternalMultiplier = 1.0F;
        this.internalDivisor = 1.0;
    }
    
    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public double applyZoom(double fov, double divisor, float tickDelta) {
        this.internalDivisor = MathHelper.lerp(tickDelta, this.lastInternalMultiplier, this.internalMultiplier);
        return fov * this.internalDivisor;
    }

    @Override
    public void tick(double divisor) {
        float zoomMultiplier = (float)(1.0F / divisor);

        this.lastInternalMultiplier = this.internalMultiplier;
        
        this.internalMultiplier += (zoomMultiplier - internalMultiplier) * smoothMultiplier;
    }

    @Override
    public double getInternalDivisor() {
        return this.internalDivisor;
    }
}
