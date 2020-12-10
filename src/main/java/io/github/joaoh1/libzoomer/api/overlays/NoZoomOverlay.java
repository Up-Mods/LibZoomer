package io.github.joaoh1.libzoomer.api.overlays;

import io.github.joaoh1.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class NoZoomOverlay implements ZoomOverlay {
    @Override
    public Identifier getIdentifier() {
        return new Identifier("libzoomer:no_overlay");
    }
    
    @Override
    public MinecraftClient setClient(MinecraftClient newClient) {
        return null;
    }

    @Override
    public void renderOverlay() {}

    @Override
    public void tick(boolean active, double divisor, double transitionMultiplier) {}
}
