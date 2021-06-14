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
     * Modifies the cursor's X delta to the value returned on this method.
     * @param rawCursorDeltaX The X delta without any calculations (like sensitivity, spyglass, etc.).
     * @param cursorDeltaX The X delta after the calculators.
     * @param mouseUpdateTimeDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The X delta that will replace the cursor's X delta.
     */
    double applyXModifier(double rawCursorDeltaX, double cursorDeltaX, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier);

    /**
     * Modifies the cursor's Y delta to the value returned on this method.
     * @param rawCursorDeltaY The Y delta without any calculations (like sensitivity, spyglass, etc.).
     * @param cursorDeltaY The Y delta after the calculators.
     * @param mouseUpdateTimeDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The Y delta that will replace the cursor's Y delta.
     */
    double applyYModifier(double rawCursorDeltaY, double cursorDeltaY, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier);

    /**
     * The tick method. Used in order to keep the internal variables accurate.
     * @param active The zoom state.
     * @param cinematicCameraEnabled The state of Vanilla's cinematic camera.
     */
    void tick(boolean active, boolean cinematicCameraEnabled);
}
