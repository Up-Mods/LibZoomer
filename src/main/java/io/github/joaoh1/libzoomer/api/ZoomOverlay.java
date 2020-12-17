package io.github.joaoh1.libzoomer.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public interface ZoomOverlay {
    Identifier getIdentifier();

    boolean getActive();

    MinecraftClient setClient(MinecraftClient newClient);

    default boolean cancelOverlayRendering() { return false; }

    void renderOverlay();

    void tick(boolean active, double divisor, double transitionMultiplier);

    default void tickBeforeRender() {}
}
