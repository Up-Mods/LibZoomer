package io.github.joaoh1.libzoomer.api;

import io.github.joaoh1.libzoomer.api.modifiers.NoMouseModifier;
import io.github.joaoh1.libzoomer.api.overlays.NoZoomOverlay;
import io.github.joaoh1.libzoomer.api.transitions.InstantTransitionMode;
import net.minecraft.util.Identifier;

public class ZoomInstance {
    private Identifier instanceId;
    private boolean zoom;
    private double defaultZoomDivisor;
    private double zoomDivisor;
    private TransitionMode transition;
    private MouseModifier modifier;
    private ZoomOverlay overlay;

    /**
     * Initializes a zoom instance. It must be registered by the instance registry before being functional
     * @param instanceId The identifier for this zoom instance.
     * @param defaultZoomDivisor The default zoom divisor. It will be this instance's initial zoom divisor value.
     * @param transition The zoom instance's transition mode.
     * @param modifier The zoom instance's mouse modifier.
     * @param overlay The zoom instance's zoom overlay.
     */
    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor, TransitionMode transition, MouseModifier modifier, ZoomOverlay overlay) {
        if (transition == null) {
            transition = new InstantTransitionMode();
        }

        if (modifier == null) {
            modifier = new NoMouseModifier();
        }

        if (overlay == null) {
            overlay = new NoZoomOverlay();
        }

        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = transition;
        this.modifier = modifier;
        this.overlay = overlay;
    }

    public Identifier getInstanceId() {
        return this.instanceId;
    }

    public boolean getZoom() {
        return this.zoom;
    }

    public boolean setZoom(boolean newZoom) {
        return this.zoom = newZoom;
    }
    
    public double getZoomDivisor() {
        return this.zoomDivisor;
    }

    public TransitionMode getTransitionMode() {
        return this.transition;
    }

    public boolean getTransitionActive() {
        return this.transition.getActive();
    }

    public MouseModifier getMouseModifier() {
        return this.modifier;
    }

    public MouseModifier setMouseModifier(MouseModifier modifier) {
        return this.modifier = modifier;
    }

    public boolean getModifierActive() {
        return this.modifier.getActive();
    }

    public ZoomOverlay getZoomOverlay() {
        return this.overlay;
    }

    public ZoomOverlay setZoomOverlay(ZoomOverlay overlay) {
        return this.overlay = overlay;
    }

    public boolean getOverlayActive() {
        return this.overlay.getActive();
    }
}