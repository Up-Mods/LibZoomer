package io.github.joaoh1.libzoomer.api;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public interface ZoomOverlay {
    Identifier getIdentifier();

    MinecraftClient setClient(MinecraftClient newClient);

    void renderOverlay();

    void tick(boolean active, double divisor);
}
