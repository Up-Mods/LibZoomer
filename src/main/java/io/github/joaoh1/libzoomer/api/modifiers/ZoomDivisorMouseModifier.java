package io.github.joaoh1.libzoomer.api.modifiers;

import io.github.joaoh1.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

public class ZoomDivisorMouseModifier implements MouseModifier {
    private Identifier transitionId = new Identifier("libzoomer:zoom_divisor");
    private boolean active;

    public ZoomDivisorMouseModifier() {
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
        if (!this.active) return o;
        return o * transitionMultiplier;
    }

    @Override
    public double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        if (!this.active) return p;
        return p * transitionMultiplier;
    }

    @Override
    public void tick(boolean active, boolean cinematicCameraEnabled) {
        this.active = active;
    }
}
