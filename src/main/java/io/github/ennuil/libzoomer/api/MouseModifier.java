package io.github.ennuil.libzoomer.api;

import net.minecraft.util.Identifier;

/**
 * The mouse modifier is the sub-instance that handles any change of behavior of the mouse.
 */
public interface MouseModifier {
    /**
     * Gets the identifier of the mouse modifier.
     * @return The mouse modifier's identifier.
     */
    Identifier getIdentifier();

    /**
     * Gets the active state of the mouse modifier.
     * @return The mouse modifier's active state.
     */
    boolean getActive();

    /**
     * Modifies the mouse's X delta to the value returned on this method.
     * @param cursorXDelta The original X delta.
     * @param o The current X delta.
     * @param mouseUpdateDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The X delta that will replace o.
     */
    double applyXModifier(double cursorXDelta, double o, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier);

    /**
     * Modifies the mouse's Y delta to the value returned on this method.
     * @param cursorXDelta The original Y delta.
     * @param p The current Y delta.
     * @param mouseUpdateDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The Y delta that will replace p.
     */
    double applyYModifier(double cursorYDelta, double p, double mouseUpdateDelta, double targetDivisor, double transitionMultiplier);

    /**
     * The tick method. Used in order to keep the internal variables accurate.
     * @param active The zoom state.
     * @param cinematicCameraEnabled The state of Vanilla's cinematic camera.
     */
    void tick(boolean active, boolean cinematicCameraEnabled);
}
