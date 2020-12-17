package io.github.joaoh1.libzoomer.api;

import net.minecraft.util.Identifier;

public interface MouseModifier {
    Identifier getIdentifier();

    boolean getActive();

    double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier);

    double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier);

    void tick(boolean active, boolean cinematicCameraEnabled);
}
