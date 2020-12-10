package io.github.joaoh1.libzoomer.api;

import net.minecraft.util.Identifier;

public interface TransitionMode {
    Identifier getIdentifier();

    double applyZoom(double fov, double divisor, float tickDelta);

    void tick(double divisor);

    double getInternalMultiplier();
}
