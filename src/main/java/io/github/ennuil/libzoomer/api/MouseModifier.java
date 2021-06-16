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
     * @param cursorSensitivity The cursor sensitivity that is applied to the cursor delta.
     * @param cursorDeltaX The X delta after the calculations.
     * @param mouseUpdateTimeDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The X delta that will replace the cursor's X delta.
     */
    double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier);

    /**
     * Modifies the cursor's Y delta to the value returned on this method.
     * @param cursorSensitivity The cursor sensitivity that is applied to the cursor delta.
     * @param cursorDeltaY The Y delta after the calculations.
     * @param mouseUpdateTimeDelta The delta of the mouse update time.
     * @param targetDivisor The current zoom divisor.
     * @param transitionMultiplier The transition mode's internal multiplier.
     * @return The Y delta that will replace the cursor's Y delta.
     */
    double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier);

    /**
     * The tick method. Used in order to keep the internal variables accurate.
     * @param active The zoom state.
     */
    void tick(boolean active);
}
