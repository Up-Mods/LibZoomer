package io.github.joaoh1.libzoomer.api;

import net.minecraft.util.Identifier;

public interface TransitionMode {
    Identifier getIdentifier();

    boolean getActive();

    double applyZoom(double fov, double divisor, float tickDelta);

    void tick(boolean active, double divisor);

    double getInternalMultiplier();
}
