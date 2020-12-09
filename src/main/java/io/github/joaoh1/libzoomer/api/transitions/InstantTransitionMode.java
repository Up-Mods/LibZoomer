package io.github.joaoh1.libzoomer.api.transitions;

import io.github.joaoh1.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;

public class InstantTransitionMode implements TransitionMode {
    private Identifier transitionId;
    private double internalDivisor;

    public InstantTransitionMode() {
        this.transitionId = new Identifier("libzoomer:no_transition");
        this.internalDivisor = 1.0;
    }
    
    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public double applyZoom(double fov, double divisor, float tickDelta) {
        this.internalDivisor = 1.0 / divisor;
        return fov / divisor;
    }

    @Override
    public void tick(double divisor) {}

    @Override
    public double getInternalDivisor() {
        return this.internalDivisor;
    }
}
