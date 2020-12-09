package io.github.joaoh1.libzoomer.api;

import net.minecraft.util.Identifier;

public interface MouseModifier {
    Identifier getIdentifier();

    double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionDivisor);

    double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionDivisor);

    void tick(boolean active, boolean cinematicCameraEnabled);
}
