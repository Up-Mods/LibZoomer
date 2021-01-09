package io.github.joaoh1.libzoomer.api.overlays;

import io.github.joaoh1.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class NoZoomOverlay implements ZoomOverlay {
    private Identifier transitionId = new Identifier("libzoomer:no_overlay");

    @Override
    public Identifier getIdentifier() {
        return this.transitionId;
    }

    @Override
    public boolean getActive() {
        return false;
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
