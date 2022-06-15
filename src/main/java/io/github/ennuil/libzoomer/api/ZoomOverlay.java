package io.github.ennuil.libzoomer.api;

import net.minecraft.util.Identifier;

/**
 * The zoom overlay is a sub-instance that handles the rendering of an overlay.
 */
public interface ZoomOverlay {
	/**
	 * Gets the identifier of the zoom overlay.
	 * @return The zoom overlay's identifier.
	 */
	Identifier getIdentifier();

	/**
	 * Gets the active state of the zoom overlay.
	 * @return The zoom overlay's active state.
	 */
	boolean getActive();

	/**
	 * Determines if the zoom overlay should cancel the rendering of anything rendered after that.
	 * By default, it returns false.
	 * @return The state that will be used in order to cancel the rendering or not.
	 */
	default boolean cancelOverlayRendering() { return false; }

	/**
	 * Renders the overlay itself. It's injected by LibZoomer itself.
	 */
	void renderOverlay();

	/**
	 * The tick method. Used in order to keep the internal variables accurate and the overlay functional.
	 * @param active The zoom state.
	 * @param divisor The zoom divisor.
	 * @param transitionMultiplier The transition mode's internal multiplier.
	*/
	void tick(boolean active, double divisor, double transitionMultiplier);

	/**
	 * The tick method used right before the overlay is rendered.
	 * Not required to be implemented.
	 */
	default void tickBeforeRender() {}
}
