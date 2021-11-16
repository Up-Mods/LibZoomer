package io.github.ennuil.libzoomer.api;

import org.jetbrains.annotations.Nullable;

import io.github.ennuil.libzoomer.api.transitions.InstantTransitionMode;
import net.minecraft.util.Identifier;

/**
 * The zoom instance is essentially the zoom. It contains all the values and sub-instances required to zoom.
 */
public class ZoomInstance {
    private final Identifier instanceId;
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
     * @param transition The zoom instance's transition mode. {@link io.github.ennuil.libzoomer.api.transitions.InstantTransitionMode} is used if null.
     * @param modifier The zoom instance's mouse modifier. If null, no mouse modifier will be applied.
     * @param overlay The zoom instance's zoom overlay. If null, no zoom overlay will be applied.
     */
    public ZoomInstance(Identifier instanceId, float defaultZoomDivisor, TransitionMode transition, @Nullable MouseModifier modifier, @Nullable ZoomOverlay overlay) {
        if (transition == null) {
            transition = new InstantTransitionMode();
        }

        this.instanceId = instanceId;
        this.zoom = false;
        this.defaultZoomDivisor = defaultZoomDivisor;
        this.zoomDivisor = this.defaultZoomDivisor;
        this.transition = transition;
        this.modifier = modifier;
        this.overlay = overlay;
    }

    /**
     * Obtains the identifier of this zoom instance.
     * @return This zoom instance's identifier.
     */
    public Identifier getInstanceId() {
        return this.instanceId;
    }

    /**
     * Gets the zoom instance's zoom state.
     * This is used to check if this instance's sub-instances should be active or not.
     * @return The current zoom state.
     */
    public boolean getZoom() {
        return this.zoom;
    }

    /**
     * Sets the zoom instance's zoom state.
     * @param newZoom The new zoom state.
     * @return The zoom state with the new value.
     */
    public boolean setZoom(boolean newZoom) {
        return this.zoom = newZoom;
    }
    
    /**
     * Gets the zoom instance's current zoom divisor.
     * NOTE: This isn't the same as the transition mode's internal multiplier.
     * @return The current zoom divisor.
     */
    public double getZoomDivisor() {
        return this.zoomDivisor;
    }

    /**
     * Sets the zoom instance's current zoom divisor.
     * @param newDivisor The new zoom divisor.
     * @return The zoom divisor with the new value.
     */
    public double setZoomDivisor(double newDivisor) {
        return this.zoomDivisor = newDivisor;
    }

    /**
     * Sets the instance's zoom divisor to the default zoom divisor.
     * @return The zoom divisor with the new value.
     */
    public double resetZoomDivisor() {
        return this.zoomDivisor = this.defaultZoomDivisor;
    }

    /**
     * Gets the instance's default zoom divisor.
     * This is used as the initial zoom divisor and as the value used on {@link #resetZoomDivisor()}.
     * @return The default zoom divisor.
     */
    public double getDefaultZoomDivisor() {
        return this.defaultZoomDivisor;
    }

    /**
     * Sets the instance's default zoom divisor.
     * @param newDivisor The new default zoom divisor.
     * @return The default zoom divisor with the new value.
     */
    public double setDefaultZoomDivisor(double newDivisor) {
        return this.defaultZoomDivisor = newDivisor;
    }

    /**
     * Gets the instance's transition mode.
     * @return The transition mode.
     */
    public TransitionMode getTransitionMode() {
        return this.transition;
    }

    /**
     * Sets the instance's transition mode.
     * @param transition The new transition mode.
     * @return The transition mode with the new mode.
     */
    public TransitionMode setTransitionMode(TransitionMode transition) {
        return this.transition = transition;
    }

    /**
     * Gets the active state from the instance's transtion mode.
     * @return The transition mode's active state.
     */
    public boolean isTransitionActive() {
        return this.transition.getActive();
    }

    /**
     * Gets the instance's mouse modifier.
     * @return The mouse modifier.
     */
    @Nullable
    public MouseModifier getMouseModifier() {
        return this.modifier;
    }

    /**
     * Sets the mouse modifier.
     * @param modifier The new mouse modifier.
     * @return The mouse modifier with the new modifier.
     */
    public MouseModifier setMouseModifier(MouseModifier modifier) {
        return this.modifier = modifier;
    }

    /**
     * Gets the mouse modifier's active state.
     * @return The mouse modifier's active state. If the modifier's null, return {@code false}.
     */
    public boolean isModifierActive() {
        if (this.modifier != null) {
            return this.modifier.getActive();
        } else {
            return false;
        }
    }

    /**
     * Gets the instance's zoom overlay.
     * @return The zoom overlay.
     */
    @Nullable
    public ZoomOverlay getZoomOverlay() {
        return this.overlay;
    }

    /**
     * Sets the zoom overlay.
     * @param overlay The new zoom overlay.
     * @return The zoom overlay with the new overlay.
     */
    public ZoomOverlay setZoomOverlay(ZoomOverlay overlay) {
        return this.overlay = overlay;
    }

    /**
     * Gets the zoom overlay's active state.
     * @return The zoom overlay's active state. If the overlay's null, return {@code false}.
     */
    public boolean isOverlayActive() {
        if (this.overlay != null) {
            return this.overlay.getActive();
        } else {
            return false;
        }
    }
}