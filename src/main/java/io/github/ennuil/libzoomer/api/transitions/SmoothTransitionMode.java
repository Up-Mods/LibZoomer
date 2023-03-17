package io.github.ennuil.libzoomer.api.transitions;

import io.github.ennuil.libzoomer.api.TransitionMode;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

/**
 * An implementation of Ok Zoomer's smooth transitions (and Vanilla's spyglass zoom) as a transition mode
 */
public class SmoothTransitionMode implements TransitionMode {
	private static final Identifier TRANSITION_ID = new Identifier("libzoomer:smooth_transition");
	private boolean active;
	private final float smoothMultiplier;
	private double fovMultiplier;
	private float internalMultiplier;
	private float lastInternalMultiplier;

	/**
	 * Initializes an instance of the smooth transition mode with the specified smooth multiplier
	 *
	 * @param smoothMultiplier the smooth multiplier, used internally by the smooth transition
	*/
	public SmoothTransitionMode(float smoothMultiplier) {
		this.active = false;
		this.smoothMultiplier = smoothMultiplier;
		this.internalMultiplier = 1.0F;
		this.lastInternalMultiplier = 1.0F;
	}

	/**
	 * Initializes an instance of the smooth transition mode with the smooth multiplier being {@code 0.5F}
	*/
	public SmoothTransitionMode() {
		this(0.5F);
	}

	@Override
	public Identifier getIdentifier() {
		return TRANSITION_ID;
	}

	@Override
	public boolean getActive() {
		return this.active;
	}

	@Override
	public double applyZoom(double fov, float tickDelta) {
		fovMultiplier = MathHelper.lerp(tickDelta, this.lastInternalMultiplier, this.internalMultiplier);
		return fov * fovMultiplier;
	}

	@Override
	public void tick(boolean active, double divisor) {
		double zoomMultiplier = 1.0F / divisor;

		this.lastInternalMultiplier = this.internalMultiplier;

		this.internalMultiplier += (zoomMultiplier - internalMultiplier) * smoothMultiplier;

		if (active || fovMultiplier == this.internalMultiplier) {
			this.active = active;
		}
	}

	@Override
	public double getInternalMultiplier() {
		return this.internalMultiplier;
	}
}
