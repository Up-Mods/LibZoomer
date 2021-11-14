package io.github.ennuil.libzoomer.api.overlays;

import io.github.ennuil.libzoomer.api.ZoomOverlay;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class NoZoomOverlay implements ZoomOverlay {
    private static final Identifier TRANSITION_ID = new Identifier("libzoomer:no_overlay");

    @Override
    public Identifier getIdentifier() {
        return TRANSITION_ID;
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
