package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.util.Identifier;

/**
 * A mouse modifier which reduces the cursor sensitivity with the transition mode's internal multiplier
 */
public class ZoomDivisorMouseModifier implements MouseModifier {
	private static final Identifier MODIFIER_ID = new Identifier("libzoomer:zoom_divisor");
	private boolean active;

	/**
	 * Initializes an instance of the zoom divisor mouse modifier.
	*/
	public ZoomDivisorMouseModifier() {
		this.active = false;
	}

	@Override
	public Identifier getIdentifier() {
		return MODIFIER_ID;
	}

	@Override
	public boolean getActive() {
		return this.active;
	}

	@Override
	public double applyXModifier(double cursorDeltaX, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
		return cursorDeltaX * (this.active ? transitionMultiplier : 1.0);
	}

	@Override
	public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
		return cursorDeltaY * (this.active ? transitionMultiplier : 1.0);
	}

	@Override
	public void tick(boolean active) {
		this.active = active;
	}
}
