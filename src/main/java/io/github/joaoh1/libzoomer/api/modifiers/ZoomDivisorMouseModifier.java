package io.github.joaoh1.libzoomer.api.modifiers;

import io.github.joaoh1.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

public class ZoomDivisorMouseModifier implements MouseModifier {

    public ZoomDivisorMouseModifier() {}

    @Override
    public Identifier getIdentifier() {
        return new Identifier("libzoomer:zoom_divisor");
    }

    @Override
    public double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        return o * transitionMultiplier;
    }

    @Override
    public double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier) {
        return p * transitionMultiplier;
    }

    @Override
    public void tick(boolean active, boolean cinematicCameraEnabled) {}
}
