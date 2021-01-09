package io.github.joaoh1.libzoomer.api.transitions;

import io.github.joaoh1.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;

public class InstantTransitionMode implements TransitionMode {
    private Identifier transitionId = new Identifier("libzoomer:no_transition");
    private boolean active;
    private double internalMultiplier;

    public InstantTransitionMode() {
        this.active = false;
        this.internalMultiplier = 1.0;
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
        this.internalMultiplier = 1.0 / divisor;
        return fov / divisor;
    }

    @Override
    public void tick(boolean active, double divisor) {
        this.active = active;
    }

    @Override
    public double getInternalMultiplier() {
        return this.internalMultiplier;
    }
}
