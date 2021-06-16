package io.github.ennuil.libzoomer.api;

import net.minecraft.util.Identifier;

/**
 * The transition mode is a sub-instance that handles zooming itself.
 * It handles how the regular FOV will transition to the zoomed FOV and vice-versa.
 */
public interface TransitionMode {
    /**
     * Gets the identifier of the transition mode.
     * @return The transition mode's identifier.
     */
    Identifier getIdentifier();

    /**
     * Gets the active state of the transition mode.
     * @return The transition mode's active state.
     */
    boolean getActive();

    /**
     * Applies the zoom to the FOV.
     * @param fov The original FOV.
     * @param tickDelta The current tick delta.
     * @return The zoomed FOV.
     */
    double applyZoom(double fov, float tickDelta);

    /**
     * The tick method. Used in order to keep the internal variables accurate.
     * @param active The zoom state.
     * @param divisor The zoom divisor.
     */
    void tick(boolean active, double divisor);

    /**
     * Gets the internal multiplier. Used for purposes other than zooming the FOV.
     * @return The internal multiplier.
     */
    double getInternalMultiplier();
}
