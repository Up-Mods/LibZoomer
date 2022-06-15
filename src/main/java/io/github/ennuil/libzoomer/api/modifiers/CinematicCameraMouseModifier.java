package io.github.ennuil.libzoomer.api.modifiers;

import io.github.ennuil.libzoomer.api.MouseModifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.SmoothUtil;
import net.minecraft.util.Identifier;

/**
 * An implemenation of Vanilla's Cinematic Camera as a mouse modifier
 */
public class CinematicCameraMouseModifier implements MouseModifier {
	private static final Identifier MODIFIER_ID = new Identifier("libzoomer:cinematic_camera");
	private boolean active;
	private MinecraftClient client;
	private boolean cinematicCameraEnabled;
	private final SmoothUtil cursorXZoomSmoother = new SmoothUtil();
	private final SmoothUtil cursorYZoomSmoother = new SmoothUtil();

	/**
	 * Initializes an instance of the cinematic camera mouse modifier
	*/
	public CinematicCameraMouseModifier() {
		this.active = false;
		this.ensureClient();
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
		if (this.cinematicCameraEnabled) {
			this.cursorXZoomSmoother.clear();
			return cursorDeltaX;
		}
		double smoother = mouseUpdateTimeDelta * cursorSensitivity;
		return this.cursorXZoomSmoother.smooth(cursorDeltaX, smoother);
	}

	@Override
	public double applyYModifier(double cursorDeltaY, double cursorSensitivity, double mouseUpdateTimeDelta, double targetDivisor, double transitionMultiplier) {
		if (this.cinematicCameraEnabled) {
			this.cursorYZoomSmoother.clear();
			return cursorDeltaY;
		}
		double smoother = mouseUpdateTimeDelta * cursorSensitivity;
		return this.cursorYZoomSmoother.smooth(cursorDeltaY, smoother);
	}

	@Override
	public void tick(boolean active) {
		this.ensureClient();
		this.cinematicCameraEnabled = this.client.options.cinematicCamera;
		if (!active && active != this.active) {
			this.cursorXZoomSmoother.clear();
			this.cursorYZoomSmoother.clear();
		}
		this.active = active;
	}

	private void ensureClient() {
		if (this.client == null) {
			this.client = MinecraftClient.getInstance();
		}
	}
}
