package io.github.joaoh1.libzoomer.api;

import io.github.joaoh1.libzoomer.api.modifiers.ZoomDivisorMouseModifier;
import io.github.joaoh1.libzoomer.api.overlays.NoZoomOverlay;
import io.github.joaoh1.libzoomer.api.transitions.InstantTransitionMode;
import net.minecraft.util.Identifier;

public class ZoomInstance {
    private Identifier instanceId;
    private boolean zoom;
    private double defaultZoomDivisor;
    private double zoomDivisor;
    private TransitionMode transition;
    private boolean isTransitionActive;
    private MouseModifier modifier;
    private boolean isModifierActive;
    private ZoomOverlay overlay;
    private boolean isOverlayActive;

    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor, TransitionMode transition, MouseModifier modifier, ZoomOverlay overlay) {
        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = transition;
        this.modifier = modifier;
        this.overlay = overlay;
    }

    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor, TransitionMode transition, MouseModifier modifier) {
        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = transition;
        this.modifier = modifier;
        this.overlay = new NoZoomOverlay();
    }

    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor, TransitionMode transition) {
        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = transition;
        this.modifier = new ZoomDivisorMouseModifier();
        this.overlay = new NoZoomOverlay();
    }

    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor) {
        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = new InstantTransitionMode();
        this.modifier = new ZoomDivisorMouseModifier();
        this.overlay = new NoZoomOverlay();
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

    public TransitionMode setTransitionMode(TransitionMode transition) {
        return this.transition = transition;
    }

    public boolean getTransitionActive() {
        return this.isTransitionActive;
    }

    public boolean setTransitionActive(boolean newActive) {
        return this.isTransitionActive = newActive;
    }

    public MouseModifier getMouseModifier() {
        return this.modifier;
    }

    public MouseModifier setMouseModifier(MouseModifier modifier) {
        return this.modifier = modifier;
    }

    public boolean getModifierActive() {
        return this.isModifierActive;
    }

    public boolean setModifierActive(boolean newActive) {
        return this.isModifierActive = newActive;
    }

    public ZoomOverlay getZoomOverlay() {
        return this.overlay;
    }

    public ZoomOverlay setZoomOverlay(ZoomOverlay overlay) {
        return this.overlay = overlay;
    }

    public boolean getOverlayActive() {
        return this.isOverlayActive;
    }

    public boolean setOverlayActive(boolean newActive) {
        return this.isOverlayActive = newActive;
    }
}