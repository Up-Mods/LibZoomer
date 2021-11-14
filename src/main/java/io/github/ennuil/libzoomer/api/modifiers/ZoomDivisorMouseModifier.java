package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

public class ZoomDivisorMouseModifier implements MouseModifier {
    private static final Identifier TRANSITION_ID = new Identifier("libzoomer:zoom_divisor");
    private boolean active;

    public ZoomDivisorMouseModifier() {
        this.active = false;
    }

    @Override
    public Identifier getIdentifier() {
        return TRANSITION_ID;
    }

    @Override
    public boolean getActive() {
        return this.active;
    }

    @Override
    public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        if (!this.active) return cursorDeltaX;
        return cursorDeltaX * transitionMultiplier;
    }

    @Override
    public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
        if (!this.active) return cursorDeltaY;
        return cursorDeltaY * transitionMultiplier;
    }

    @Override
    public void tick(boolean active) {
        this.active = active;
    }
}
